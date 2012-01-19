/*
 * Created on Jun 20, 2005
 */
package cyrille.util;

import java.util.StringTokenizer;

import junit.framework.TestCase;

public class StringTokenizerTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(StringTokenizerTest.class);
    }

    public void test() {
        StringTokenizer stringTokenizer = new StringTokenizer("a&b&b&", "&");
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            System.out.println("token " + token);
        }

    }
}
