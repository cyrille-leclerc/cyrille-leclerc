/*
 * Created on Mar 7, 2005
 */
package cyrille.lang;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class StringTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(StringTest.class);
    }

    public void testSubString() {
        String source = "mspvq507_a";
        String actual = source.substring(0, source.length() - 2);
        String expected = "mspvq507";
        assertEquals(expected, actual);
    }

    public void testSubstringEndIndex() {
        {
            String source = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam blandit, tortor ";
            String actual = source.substring(0, Math.min(source.length(), 5));
            String expected = "Lorem";
            assertEquals(expected, actual);
        }
        {
            String source = "Lorem";
            String actual = source.substring(0, Math.min(source.length(), 10));
            String expected = "Lorem";
            assertEquals(expected, actual);
        }
    }

    public void testReplaceAll() {
        String source = "com.osa.mdsp.testing.admin.AliasingAdminTest";
        String actual = source.replaceAll("\\.", "/");
        String expected = "com/osa/mdsp/testing/admin/AliasingAdminTest";
        assertEquals(expected, actual);
    }

    public void testIsEmpty() throws Exception {

    }

    private boolean isAlreadyInUrl(String url, String parameter, int pos) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(parameter) || pos > url.length()) {
            return false;
        }
        boolean result = (StringUtils.indexOf(url, "?" + parameter + "=") >= pos)
                || (StringUtils.indexOf(url, "&" + parameter + "=") >= pos);

        return result;
    }

    private boolean isAlreadyInUrl2(String url, String parameter, int pos) {
        boolean result = (StringUtils.indexOf(url, "?" + parameter + "=") >= pos)
                || (StringUtils.indexOf(url, "&" + parameter + "=") >= pos);
        return result;
    }
}