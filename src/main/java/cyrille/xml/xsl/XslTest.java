/*
 * Created on Oct 18, 2004
 */
package cyrille.xml.xsl;

import java.io.InputStream;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class XslTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(XslTest.class);
    }

    public void testXsl() throws Exception {
        System.err.println();
        System.err.println("***********************************");
        System.err.println("testXsl");
        System.err.println("***********************************");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        InputStream xslStream = getClass().getResourceAsStream("test.xsl");
        assertNotNull(xslStream);
        Templates templates = transformerFactory.newTemplates(new StreamSource(xslStream));

        InputStream xmlStream = getClass().getResourceAsStream("test.xml");
        assertNotNull(xmlStream);
        Transformer transformer = templates.newTransformer();
        transformer.transform(new StreamSource(xmlStream), new StreamResult(System.err));
    }

    public void testXslWithTemplateOverWrite() throws Exception {
        System.err.println();
        System.err.println("***********************************");
        System.err.println("TestXslWithTemplateOverWrite");
        System.err.println("***********************************");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        InputStream xslStream = getClass().getResourceAsStream("testWithTemplateOverWrite.xsl");
        assertNotNull(xslStream);
        Templates templates = transformerFactory.newTemplates(new StreamSource(xslStream));

        InputStream xmlStream = getClass().getResourceAsStream("test.xml");
        assertNotNull(xmlStream);
        Transformer transformer = templates.newTransformer();
        transformer.transform(new StreamSource(xmlStream), new StreamResult(System.err));
    }

    public void testXslWithMissingTemplateDeclaration() throws Exception {
        System.err.println();
        System.err.println("***********************************");
        System.err.println("testXslWithMissingTemplateDeclaration");
        System.err.println("***********************************");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        InputStream xslStream = getClass().getResourceAsStream("testWithMissingTemplateDeclaration.xsl");
        assertNotNull(xslStream);
        Templates templates = transformerFactory.newTemplates(new StreamSource(xslStream));

        InputStream xmlStream = getClass().getResourceAsStream("test.xml");
        assertNotNull(xmlStream);
        Transformer transformer = templates.newTransformer();
        transformer.transform(new StreamSource(xmlStream), new StreamResult(System.err));
    }
}