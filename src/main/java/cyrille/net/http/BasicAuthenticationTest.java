/*
 * Created on Aug 6, 2004
 */
package cyrille.net.http;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.apache.axis.encoding.ser.Base64Deserializer;
import org.apache.axis.encoding.ser.Base64DeserializerFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.junit.Test;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class BasicAuthenticationTest {
    
    @Test
    public void testEncode() throws Exception {
        
        testEncode("osm", "1111");
        testEncode("0621402081", "1111");
        testEncode("0621482520", "1111");
        testEncode("0621441414", "1111");
        
    }
    
    public String testEncode(String login, String password) throws UnsupportedEncodingException {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(login, password);
        String encodedLoginPassword = BasicScheme.authenticate(credentials, "ISO-8859-1");
        byte[] base64 = Base64.encodeBase64((login + ":" + password).getBytes("iso-8859-1"));
        
        System.out.println("clientId=" + login + ", password=" + password + ", encodedClientIdPassword=" + encodedLoginPassword + ", base64=" + new String(base64, "iso-8859-1"));

        return encodedLoginPassword;
    }
}
