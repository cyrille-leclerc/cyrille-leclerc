/*
 * Created on Jul 6, 2005
 */
package cyrille.util;

import java.util.Properties;

import junit.framework.TestCase;

public class PropertiesTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PropertiesTest.class);
    }

    public void test() throws Exception {
        Properties properties = new Properties();
        for (int i = 0; i < 10; i++) {
            properties.put("key-" + i, "value-" + i);
        }
        properties.storeToXML(System.out, "my comments");
    }
}
