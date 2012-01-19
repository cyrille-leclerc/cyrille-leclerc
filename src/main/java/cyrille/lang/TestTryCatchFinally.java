/*
 * Created on Oct 26, 2004
 */
package cyrille.lang;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class TestTryCatchFinally extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestTryCatchFinally.class);
    }

    public void testTryCatchFinally() {
        int expected = 2;
        int[] actualAsArray = testSubMethod(expected);
        System.out.println("after call");
        int actual = actualAsArray[0];
        assertEquals(expected, actual);
    }

    public int[] testSubMethod(int expected) {
        int[] result = new int[1];
        try {
            result[0] = expected + 1;
            System.out.println("about to return");
            return result;
        } finally {
            System.out.println("in finally");
            result[0] = expected;
        }
    }
}