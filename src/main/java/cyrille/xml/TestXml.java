/*
 * Created on Sep 17, 2003
 */
package cyrille.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TestXml extends TestCase {

    /**
     * Constructor for TestXml.
     * 
     * @param name
     */
    public TestXml(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestXml.class);
    }

    public void testTransformFileFile() throws TransformerException {
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");

        InputStream xmlStream = getClass().getResourceAsStream("legal_2420.xml");
        InputStream xslStream = getClass().getResourceAsStream("specificAddressForCpart.xsl");
        Source xmlSource = new StreamSource(xmlStream);
        Source xslSource = new StreamSource(xslStream);
        Result result = new StreamResult(System.out);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xslSource);
        transformer.setURIResolver(new MyURIResolver());
        transformer.transform(xmlSource, result);
    }

    public void testTransformDOMFile() throws TransformerException, ParserConfigurationException, SAXException, IOException {
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        InputStream xmlStream = getClass().getResourceAsStream("legal_2420.xml");
        Document document = documentBuilder.parse(xmlStream);
        Source xmlSource = new DOMSource(document); // new StreamSource(xmlStream);

        InputStream xslStream = getClass().getResourceAsStream("specificAddressForCpart.xsl");
        Document xslDocument = documentBuilder.parse(xslStream);
        Source xslSource = new DOMSource(xslDocument); // new StreamSource(xslStream);
        Result result = new StreamResult(System.out);

        Transformer transformer = transformerFactory.newTransformer(xslSource);
        transformer.setURIResolver(new MyURIResolver());
        transformer.transform(xmlSource, result);
    }
}
