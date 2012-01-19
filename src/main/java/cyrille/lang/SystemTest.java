package cyrille.lang;

import java.util.Properties;

import junit.framework.TestCase;

public class SystemTest extends TestCase {

    public void testSystemProperties() throws Exception {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            String value = properties.getProperty((String) key);
            System.out.println(key + "=" + value);
        }
    }
}
