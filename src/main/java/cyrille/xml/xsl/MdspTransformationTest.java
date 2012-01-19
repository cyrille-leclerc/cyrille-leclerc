/*
 * Created on Oct 30, 2004
 */
package cyrille.xml.xsl;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class MdspTransformationTest extends TestCase {

    public final int NUMBER_OF_THREADS = 1;

    public final int NUMBER_OF_INVOCATIONS_PER_THREAD = 1000;

    protected TransformerFactory transformerFactory;

    public static void main(String[] args) throws Exception {
        // junit.textui.TestRunner.run(MdspTransformationTest.class);
        MdspTransformationTest mdspTransformationTest = new MdspTransformationTest();
        mdspTransformationTest.setUp();
        mdspTransformationTest.test();
        mdspTransformationTest.tearDown();
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setURIResolver(new ClassPathUriResolver());
        transformerFactory.setErrorListener(new NullErrorListener());
        MonitorFactory.setEnabled(true);
        // System.setProperty("javax.xml.transform.TransformerFactory",
        // "org.apache.xalan.xsltc.trax.TransformerFactoryImpl");
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        File file = new File("jamonForXsl.html");
        FileWriter writer = new FileWriter(file);
        writer.write(MonitorFactory.getReport());
        writer.close();
        System.out.println();
        System.out.println("Metrics written in " + file.getAbsolutePath() + " bye");

    }

    public void test() throws Exception {
        String xmlPath = "cyrille/xsl/html32/index.oml";
        String xslPath = "cyrille/xsl/wml/xhtmlop2wml.xsl";
        // testWithSharedTransformer(xmlPath, xslPath);
        testWithNewTransformer(xmlPath, xslPath);
        // testWithPooledTransformer(xmlPath, xslPath);
        // testWithSharedTransformer(xmlPath, xslPath);
        // testWithNewTransformer(xmlPath, xslPath);
        // testWithPooledTransformer(xmlPath, xslPath);
    }

    public void testWithNewTransformer(final String xmlPath, String xslPath) throws Exception {
        System.out.println("> testWithNewTransformer");
        InputStream xslStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xslPath);
        assertNotNull("xslStream", xslStream);
        Source xslSource = new StreamSource(xslStream);
        final Templates templates = transformerFactory.newTemplates(xslSource);
        System.out.println(templates.newTransformer());
        ExecutorService executorService = Executors.newFixedThreadPool(this.NUMBER_OF_THREADS);

        for (int i = 0; i < this.NUMBER_OF_THREADS; i++) {
            Runnable runnable = new Runnable() {

                public void run() {
                    for (int i = 0; i < MdspTransformationTest.this.NUMBER_OF_INVOCATIONS_PER_THREAD; i++) {
                        // progression bar with simple stupid line breaks
                        System.out.print("-");
                        if ((i + 1) % 5 == 0) {
                            System.out.println();
                        }
                        Monitor monitor = MonitorFactory.start("transformTemplatesNewTransformer");
                        try {
                            InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath);
                            assertNotNull("xmlStream", xmlStream);
                            Source xmlSource = new StreamSource(xmlStream);

                            OutputStream out = new NullOutputStream();
                            Result result = new StreamResult(out);

                            Transformer transformer = templates.newTransformer();
                            transformer.setErrorListener(new NullErrorListener());
                            transformer.transform(xmlSource, result);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            monitor.stop();
                        }
                    }
                }
            };
            executorService.execute(runnable);
            // ramp up
            Thread.sleep(100);
        }
        executorService.shutdown();
        executorService.awaitTermination(10 * 60, TimeUnit.SECONDS);
    }

    public void testWithPooledTransformer(final String xmlPath, final String xslPath) throws Exception {
        System.out.println("> testWithPooledTransformer");

        ExecutorService executorService = Executors.newFixedThreadPool(this.NUMBER_OF_THREADS);
        KeyedPoolableObjectFactory keyedPoolableObjectFactory = new BaseKeyedPoolableObjectFactory() {

            private Map pathToTemplatesMap = new HashMap();

            @Override
            public Object makeObject(Object key) throws Exception {
                Templates templates = getTemplates(key.toString());
                Transformer transformer = templates.newTransformer();
                transformer.setErrorListener(new NullErrorListener());
                return transformer;
            }

            private Templates getTemplates(String path) throws Exception {
                Templates templates = (Templates) this.pathToTemplatesMap.get(path);
                if (templates == null) {
                    synchronized (this) {
                        templates = (Templates) this.pathToTemplatesMap.get(path);
                        if (templates == null) {
                            InputStream xslStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                            if (xslStream == null) {
                                throw new Exception("No resource found in the classpath for '" + path + "'");
                            }
                            Source xslSource = new StreamSource(xslStream);
                            templates = transformerFactory.newTemplates(xslSource);
                            this.pathToTemplatesMap.put(path, templates);
                        }
                    }
                }
                return templates;
            }
        };
        final KeyedObjectPool keyedObjectPool = new GenericKeyedObjectPool(keyedPoolableObjectFactory, this.NUMBER_OF_THREADS + 5);
        // load the template before running the test to compare with other
        // scenarios
        keyedObjectPool.borrowObject(xslPath);

        for (int i = 0; i < this.NUMBER_OF_THREADS; i++) {
            Runnable runnable = new Runnable() {

                public void run() {
                    for (int i = 0; i < MdspTransformationTest.this.NUMBER_OF_INVOCATIONS_PER_THREAD; i++) {
                        // progression bar with simple stupid line breaks
                        System.out.print("-");
                        if ((i + 1) % 5 == 0) {
                            System.out.println();
                        }
                        Monitor monitor = MonitorFactory.start("transformPooledTransformer");
                        try {
                            InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath);
                            assertNotNull("xmlStream", xmlStream);
                            Source xmlSource = new StreamSource(xmlStream);

                            OutputStream out = new NullOutputStream();
                            Result result = new StreamResult(out);
                            Transformer transformer = (Transformer) keyedObjectPool.borrowObject(xslPath);
                            transformer.transform(xmlSource, result);
                            keyedObjectPool.returnObject(xslPath, transformer);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            monitor.stop();
                        }
                    }
                }
            };
            executorService.execute(runnable);
            // ramp up
            Thread.sleep(100);
        }
        executorService.shutdown();
        executorService.awaitTermination(10 * 60, TimeUnit.SECONDS);
    }

    public void testWithSharedTransformer(final String xmlPath, String xslPath) throws Exception {
        System.out.println("> testWithSharedTransformer");
        InputStream xslStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xslPath);
        assertNotNull("xslStream", xslStream);
        Source xslSource = new StreamSource(xslStream);
        final Transformer transformer = transformerFactory.newTransformer(xslSource);
        transformer.setErrorListener(new NullErrorListener());

        ExecutorService executorService = Executors.newFixedThreadPool(this.NUMBER_OF_THREADS);

        for (int i = 0; i < this.NUMBER_OF_THREADS; i++) {
            Runnable runnable = new Runnable() {

                public void run() {
                    for (int i = 0; i < MdspTransformationTest.this.NUMBER_OF_INVOCATIONS_PER_THREAD; i++) {
                        // progression bar with simple stupid line breaks
                        System.out.print("-");
                        if ((i + 1) % 5 == 0) {
                            System.out.println();
                        }
                        Monitor monitor = MonitorFactory.start("transformSharedTransformer");
                        try {
                            InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath);
                            assertNotNull("xmlStream", xmlStream);
                            Source xmlSource = new StreamSource(xmlStream);

                            OutputStream out = new NullOutputStream();
                            Result result = new StreamResult(out);

                            transformer.transform(xmlSource, result);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            monitor.stop();
                        }
                    }
                }
            };
            executorService.execute(runnable);
            // ramp up
            Thread.sleep(100);
        }
        executorService.shutdown();
        executorService.awaitTermination(10 * 60, TimeUnit.SECONDS);
    }
}