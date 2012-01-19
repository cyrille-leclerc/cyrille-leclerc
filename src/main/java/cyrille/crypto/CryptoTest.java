/*
 * Created on Feb 19, 2004
 */
package cyrille.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class CryptoTest extends TestCase {

    /**
     * Constructor for CryptoTest.
     * 
     * @param name
     */
    public CryptoTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(CryptoTest.class);
    }

    public void testBlowfish() throws NoSuchAlgorithmException {
        KeyGenerator.getInstance("Blowfish");
    }
}
