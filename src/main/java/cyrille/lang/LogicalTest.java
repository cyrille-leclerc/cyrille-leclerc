package cyrille.lang;

import junit.framework.TestCase;

public class LogicalTest extends TestCase {

    public void testOr() throws Exception {
        boolean actual = false;
        actual |= sayFalse("1");
        actual |= sayTrue("2");
        actual |= sayFalse("3");
        assertEquals(true, actual);
    }

    public boolean sayTrue(String msg) {
        System.out.println("sayTrue " + msg);
        return true;
    }

    public boolean sayFalse(String msg) {
        System.out.println("sayFalse " + msg);
        return false;
    }
}
