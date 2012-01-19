package cyrille.xml.dom;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomTest extends TestCase {

    public void test() throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element rootElement = document.createElement("root-element");
        document.appendChild(rootElement);

        for (int i = 0; i < 10; i++) {
            Element child = document.createElement("child-" + i);
            rootElement.appendChild(child);

            child.setTextContent("text-" + i);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(rootElement), new StreamResult(System.out));
        transformer.reset();

        for (int i = 0; i < 10; i++) {
            NodeList child1AsNodeList = rootElement.getElementsByTagName("child-" + i);
            assertEquals(1, child1AsNodeList.getLength());
            System.out.println("\r\n\r\nChild-" + i + ":");
            transformer.transform(new DOMSource(child1AsNodeList.item(0)), new StreamResult(System.out));
            transformer.reset();
        }

    }
}
