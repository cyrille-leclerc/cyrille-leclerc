package cyrille.codec;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import junit.framework.TestCase;

public class CommonsCodecBase64Test extends TestCase {

    public void testBase64EncodeDecodeUtf8() throws Exception {
        String xmlEncoding = "UTF-8";
        testBase64EncodeDecode(xmlEncoding);
    }

    public void testBase64EncodeDecodeIso88591() throws Exception {
        String xmlEncoding = "ISO-8859-1";
        testBase64EncodeDecode(xmlEncoding);
    }

    private void testBase64EncodeDecode(String xmlEncoding) throws UnsupportedEncodingException {
        System.out.println("> testBase64EncodeDecode(" + xmlEncoding + ")");
        
        String xmlAsString = "<root><my-element>יטאש</my-element></root>";

        System.out.println("\t xmlAsString\t" + xmlAsString);

        String xmlAsBase64String = base64Encode(xmlAsString, xmlEncoding);
        System.out.println("\t xmlAsBase64\t" + xmlAsBase64String);

        byte[] decodedBased64Xml = base64Decode(xmlAsBase64String);
        String decodedBase64XmlAsString = new String(decodedBased64Xml, xmlEncoding);

        assertTrue("Accents lost", decodedBase64XmlAsString.indexOf("יטאש") > 0);
    }

    private String base64Encode(String xmlAsString, String xmlEncoding) throws UnsupportedEncodingException {
        String xmlencodingDeclaration = "<?xml version=\"1.0\" encoding=\"" + xmlEncoding + "\"?>";
        byte[] xmlAsBytes = (xmlencodingDeclaration + xmlAsString).getBytes(xmlEncoding);

        byte[] xmlAsBase64 = Base64.encodeBase64(xmlAsBytes);

        String xmlAsBase64String = new String(xmlAsBase64, "ascii");

        return xmlAsBase64String;
    }

    private byte[] base64Decode(String xmlAsBase64String) throws UnsupportedEncodingException {

        byte[] xmlAsBase64 = Base64.decodeBase64(xmlAsBase64String.getBytes("ascii"));

        return xmlAsBase64;
    }
}
