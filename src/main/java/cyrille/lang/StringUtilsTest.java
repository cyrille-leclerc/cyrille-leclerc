/*
 * Created on Oct 27, 2005
 */
package cyrille.lang;

import org.apache.commons.lang.StringUtils;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(StringUtilsTest.class);
    }

    public void testCapitalizeCamelWord() {
        String actual = MyStringUtils.capitalize("testOne");
        String expected = "TEST_ONE";
        assertEquals(expected, actual);
    }

    public void testCapitalizeSapces() {
        String actual = MyStringUtils.capitalize("test one");
        String expected = "TEST ONE";
        assertEquals(expected, actual);
    }

    
    
    public void testSubstring() throws Exception {
        String actual = StringUtils.substring("my-string", 0, 70);
        String expected = "my-string";
        assertEquals(expected, actual);
    }
}
