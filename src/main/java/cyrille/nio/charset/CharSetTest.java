package cyrille.nio.charset;

import java.nio.charset.Charset;
import java.util.Set;
import java.util.SortedMap;

import junit.framework.TestCase;

public class CharSetTest extends TestCase {

    public void testAvailableCharset() {
        SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
        for (Charset charset : availableCharsets.values()) {
            Set<String> aliases = charset.aliases();
            String msg = charset.toString() + " :";
            for (String alias : aliases) {
                msg += alias + ", ";
            }
            System.out.println(msg);
        }
    }

    public void testFindCharsetIBM819() throws Exception {
        String charsetName = "IBM819";
        Charset charset = Charset.forName(charsetName);
        System.out.println("Charset for " + charsetName + ": " + charset);
    }

    public void testFindCharset() throws Exception {
        String charsetName = "Unicode";
        Charset charset = Charset.forName(charsetName);
        System.out.println("Charset for " + charsetName + ": " + charset);
    }
    
    /**
     * both 'c' and 'p' chars are upper case
     * @throws Exception
     */
    public void testFindCharsetCP858() throws Exception {
        String charsetName = "CP858";
        Charset charset = Charset.forName(charsetName);
        System.out.println("Charset for " + charsetName + ": " + charset);
    }
}
