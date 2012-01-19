package cyrille.codec;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.XStream;

import junit.framework.TestCase;

/**
 * <p>
 * Sample : java object -> xml String (via xstream) -> base 64 string (via base64 encode ) -> xml
 * string (via base 64 decode) -> java object (via xstream)
 * </p>
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
public class XStreamCommonsCodecBase64Test extends TestCase {

    /**
     * Sample java object
     */
    public static class Person {

        protected String firstName;

        protected String lastName;

        public Person() {
            super();
        }

        public Person(String lastName, String firstName) {
            this();
            this.lastName = lastName;
            this.firstName = firstName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int hashCode() {
            return new HashCodeBuilder().append(this.lastName).append(this.firstName).toHashCode();
        }

        public boolean equals(Object obj) {
            if (false == (obj instanceof Person)) {
                return false;
            }
            final Person other = (Person) obj;
            return new EqualsBuilder().append(this.lastName, other.lastName).append(this.firstName, other.firstName).isEquals();
        }

        public String toString() {
            return new ToStringBuilder(this).append("lastName", this.lastName).append("firstName", this.firstName).toString();
        }
    }

    /**
     * Decode given base64 bytes (handled as a String) into an XML String.
     */
    private String base64Decode(String xmlAsBase64String, String xmlEncoding) throws UnsupportedEncodingException {

        byte[] xmlAsBase64 = Base64.decodeBase64(xmlAsBase64String.getBytes("ascii"));

        return new String(xmlAsBase64, xmlEncoding);
    }

    /**
     * Encode given XML String into base64 bytes (handled as a string).
     */
    private String base64Encode(String xmlAsString, String xmlEncoding) throws UnsupportedEncodingException {
        String xmlencodingDeclaration = "<?xml version=\"1.0\" encoding=\"" + xmlEncoding + "\"?>";
        byte[] xmlAsBytes = (xmlencodingDeclaration + xmlAsString).getBytes(xmlEncoding);

        byte[] xmlAsBase64 = Base64.encodeBase64(xmlAsBytes);

        // base64 is pure ascii
        String xmlAsBase64String = new String(xmlAsBase64, "ascii");

        return xmlAsBase64String;
    }

    /**
     * Test encode-decode.
     */
    private void testBase64EncodeDecode(String xmlEncoding) throws UnsupportedEncodingException {
        System.out.println("> testBase64EncodeDecode(" + xmlEncoding + ")");

        // INITIALIZE XSTREAM
        XStream xstream = new XStream();
        xstream.alias("person", Person.class);

        Person person = new Person("my-last-name", "my-first-name-with-accents-יטאש");

        // XSTREAM MARSHAL (OBJECT TO XML)
        String xmlAsString = xstream.toXML(person);

        System.out.println("\t xmlAsString\t" + xmlAsString);

        // BASE 64 ENCODE
        String xmlAsBase64String = base64Encode(xmlAsString, xmlEncoding);
        System.out.println("\t xmlAsBase64\t" + xmlAsBase64String);

        // BASE 64 DECODE
        String decodedBase64XmlAsString = base64Decode(xmlAsBase64String, xmlEncoding);

        // XSTREAM UNMARSHAL (OBJECT FROM XML)
        Person decodedPerson = (Person) xstream.fromXML(decodedBase64XmlAsString);

        assertEquals(person, decodedPerson);
    }

    public void testBase64EncodeDecodeIso88591() throws Exception {
        String xmlEncoding = "ISO-8859-1";
        testBase64EncodeDecode(xmlEncoding);
    }

    public void testBase64EncodeDecodeUtf8() throws Exception {
        String xmlEncoding = "UTF-8";
        testBase64EncodeDecode(xmlEncoding);
    }
}
