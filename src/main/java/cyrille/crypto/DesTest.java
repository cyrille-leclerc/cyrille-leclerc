package cyrille.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import junit.framework.TestCase;

public class DesTest extends TestCase {

    private void testEncode() throws Exception {
        // Create a Cipher object for symmetric encryption (e.g., DES)
        Cipher cipher = Cipher.getInstance("DES");

        // Create a KeyGenerator object
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");

        // Use KeyGenerator to create a Secret (session) key
        SecretKey secretKey = keyGenerator.generateKey();

        // Initialize Cipher object for encryption with session key
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt message
        // cipher.
        // Get intended recipient's public key (
        // e.g., from the
        // recipient's public key certificate)
        // Create Cipher for asymmetric encryption (
        // e.g., RSA), and
        // initialize it for encryption with
        // recipient's public key
        // Create SealedObject to seal session key using
        // asymmetric Cipher
        // Serialize SealedObject
        // Send encrypted message and serialized
        // SealedObject to intended recipient
    }
}
