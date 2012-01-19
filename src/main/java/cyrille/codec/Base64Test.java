/*
 * Created on Aug 9, 2004
 */
package cyrille.codec;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class Base64Test {

    @Test
    public void testEncode() {
        String[] decodedStrings = new String[] { "psw-www.sfr.fr:psw-www.sfr.fr" };
        for (String decodedString : decodedStrings) {
            byte[] encodedValue = Base64.encodeBase64(decodedString.getBytes());
            String based64String = new String(encodedValue);
            System.out.println("decodedString=" + decodedString + ", based64String=" + based64String);
        }
    }

    @Test
    public void testDecodeHttpBasicAuthentication1() throws UnsupportedEncodingException {
        decodeHttpBasicAuthentication("c2ZyLmZyOndlYmxvZ2ljMDAx");
        decodeHttpBasicAuthentication("aXNhYmVsbGUuY2FycmllQHNmci5jb206c1c3bmE2YXA=");
        decodeHttpBasicAuthentication("IGlzYWJlbGxlLmNhcnJpZUBzZnIuY29tOnNXN25hNmFw");
    }

    private void decodeHttpBasicAuthentication(String based64String) throws UnsupportedEncodingException {
        String decodedString = new String(Base64.decodeBase64(based64String.getBytes("iso-8859-1")), "iso-8859-1");
        System.out.println("based64String=" + based64String + ", decodedString=" + decodedString);
    }
}