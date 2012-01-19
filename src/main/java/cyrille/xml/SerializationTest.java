/*
 * Created on Apr 8, 2007
 */
package cyrille.xml;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class SerializationTest extends TestCase {

    public void testSerialization() throws Exception {
        
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = (Element) document.appendChild(document.createElement("root"));
        Element child = (Element) root.appendChild(document.createElement("child"));
        Text text = (Text) child.appendChild(document.createTextNode("my text יטא €"));

        System.out.println(text);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        String encoding = "iso-8859-1"; //"UTF-8";//"iso-8859-15"; //"UTF-8"; //"iso-8859-15";
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        FileOutputStream out = new FileOutputStream("/tmp/test-encoding-" + encoding + ".xml");
        StreamResult streamResult = new StreamResult(System.out);

        transformer.transform(new DOMSource(document), streamResult);
    }
}
