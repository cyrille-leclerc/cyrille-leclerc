/*
 * Created on Feb 3, 2005
 */
package cyrille.lang;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class TestSystemCurrentTimeMillis extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestSystemCurrentTimeMillis.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.class.getDeclaredMethod("nanoTime", new Class[0]);

        String javaSpecificationVersion = System.getProperty("java.specification.version");
        System.out.println(javaSpecificationVersion);
    }

    public void test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            long expected = 5;
            long before = System.currentTimeMillis();
            Thread.sleep(expected);
            long after = System.currentTimeMillis();
            long actual = after - before;
            System.out.println("Expected duration=" + expected + " ms ; actual duration=" + actual);
        }
    }

    public void testNanoTime() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            long expectedInMillis = 8;

            long beforeInMillis = System.currentTimeMillis();
            long beforeInNanos = System.nanoTime();

            Thread.sleep(expectedInMillis);

            long afterInMillis = System.currentTimeMillis();
            long afterInNanos = System.nanoTime();

            long actualInMillis = afterInMillis - beforeInMillis;
            long actualInNanos = afterInNanos - beforeInNanos;

            System.out.println("Expected duration=" + expectedInMillis + " ms ;\tactual duration from SystemCurrentTimeMillis="
                    + actualInMillis + "ms ;\tactual duration from SystemNanoTime=" + (actualInNanos / 1000000) + " ;\toffset="
                    + (((actualInMillis - (actualInNanos / 1000000)) * 100) / (actualInNanos / 1000000)) + "%");
        }
    }

}