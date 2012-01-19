/*
 * Created on Oct 18, 2004
 */
package cyrille.xml.xsl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class AnotherXslTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AnotherXslTest.class);
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testXsl() throws Exception {
        System.err.println();
        System.err.println("***********************************");
        System.err.println("testXsl");
        System.err.println("***********************************");

        String actual = transform("test.xsl");
        String expected = "this is myTemplate";

        assertEquals(expected, actual);
    }

    public void testXslWithTemplateOverWrite() throws Exception {
        System.err.println();
        System.err.println("***********************************");
        System.err.println("TestXslWithTemplateOverWrite");
        System.err.println("***********************************");

        String actual = transform("testWithTemplateOverWrite.xsl");
        String expected = "this is myTemplate"; // "this is an over written myTemplate";

        assertEquals(expected, actual);
    }

    public void testXslWithMissingTemplateDeclaration() throws Exception {
        System.err.println();
        System.err.println("***********************************");
        System.err.println("testXslWithMissingTemplateDeclaration");
        System.err.println("***********************************");

        String actual = transform("testWithMissingTemplateDeclaration.xsl");
        String expected = "this is an over written myTemplate";

        assertEquals(expected, actual);
    }

    public void testWithMissingTemplateDeclarationCalledByANestedXsl() throws Exception {
        System.err.println();
        System.err.println("***********************************");
        System.err.println("testWithMissingTemplateDeclarationCalledByANestedXsl");
        System.err.println("***********************************");

        String actual = transform("testWithMissingTemplateDeclaration.xsl");
        String expected = "this is an over written myTemplate";

        assertEquals(expected, actual);
    }

    /**
     * @param xslName
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    private String transform(String xslName) throws TransformerFactoryConfigurationError, TransformerConfigurationException,
            TransformerException {
        InputStream xslStream = getClass().getResourceAsStream(xslName);
        assertNotNull(xslStream);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Templates templates = transformerFactory.newTemplates(new StreamSource(xslStream));

        InputStream xmlStream = getClass().getResourceAsStream("test.xml");
        assertNotNull(xmlStream);
        Transformer transformer = templates.newTransformer();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        transformer.transform(new StreamSource(xmlStream), new StreamResult(out));
        String actual = out.toString();
        return actual;
    }
}