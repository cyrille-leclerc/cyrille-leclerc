/*
 * Created on Oct 29, 2004
 */
package cyrille.xml.xsl;

import java.io.File;
import java.io.FileWriter;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class XslStressTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(XslStressTest.class);
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MonitorFactory.setEnabled(true);
    }

    public void test() throws Exception {
        final String HOST = "http://10.173.35.81:9080/"; // "http://127.0.0.1:3000/"//
        final String PATH = "xdimeORoml/oml/index.oml"; // "/static/oml/index.oml";//

        final int NUMBER_OF_THREADS = 15;
        final int NUMBER_OF_INVOCATIONS_PER_THREAD = 100;

        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams httpConnectionManagerParams = connectionManager.getParams();
        httpConnectionManagerParams.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, NUMBER_OF_THREADS);

        final HttpClient client = new HttpClient(connectionManager);

        HostConfiguration hostConfiguration = new HostConfiguration();
        hostConfiguration.setProxy("10.173.35.81", 8087);
        client.setHostConfiguration(hostConfiguration);

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Runnable runnable = new Runnable() {

                public void run() {
                    for (int i = 0; i < NUMBER_OF_INVOCATIONS_PER_THREAD; i++) {
                        System.out.print("-");
                        if (i % 10 == 0) {
                            System.out.println();
                        }
                        GetMethod get = new GetMethod(HOST);
                        get.setPath(PATH);
                        get.setQueryString("MCO=OFR&LANG=fr");

                        HttpMethodParams httpMethodParams = get.getParams();
                        httpMethodParams.setVersion(HttpVersion.HTTP_1_1);
                        httpMethodParams.setVirtualHost("toto.com");
                        httpMethodParams.setParameter(HttpMethodParams.USER_AGENT, "Nokia6100_FBO");

                        get.addRequestHeader("Connection", "keep-alive");
                        get.addRequestHeader("Keep-alive", "300");
                        Monitor monitor = MonitorFactory.start("get");
                        try {
                            int resultCode = client.executeMethod(get);
                            if (resultCode != 200) {
                                throw new Exception("code != 200 : " + resultCode);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            monitor.stop();
                            get.releaseConnection();
                        }
                    }
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(10 * 60, TimeUnit.SECONDS);
        File file = new File("jamonForWtp.html");
        FileWriter writer = new FileWriter(file);
        writer.write(MonitorFactory.getReport());
        writer.close();
        System.out.println();
        System.out.println("Metrics written in " + file.getAbsolutePath() + " bye");
    }
}