/*
 * Created on Sep 24, 2003
 */
package cyrille.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class PropertiesTest {
    public static void main(String[] args) {
        try {
            testDoubles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testDoubles() throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("c:/symphonie_nls_labels.log"));
        properties.store(new FileOutputStream("c:/symphonie_nls_labels.filtered.txt"), "");

    }
}
