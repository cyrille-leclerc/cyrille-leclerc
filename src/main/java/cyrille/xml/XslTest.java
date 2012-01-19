package cyrille.xml;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

public class XslTest extends TestCase {

    public void testTransformer() throws Exception {

        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        Source source = null;
        transformer.transform(source, new StreamResult(System.out));
    }
}
