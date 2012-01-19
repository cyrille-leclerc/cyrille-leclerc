/*
 * Created on Jul 18, 2005
 */
package cyrille.lang;

public class InfiniteRecursionTest {

    public static void main(String[] args) {
        InfiniteRecursionTest infiniteRecursionTest = new InfiniteRecursionTest();
        try {
            infiniteRecursionTest.test();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void test() throws Exception {

        ClassLoader.getSystemClassLoader().loadClass("cyrille.lang.InfiniteRecursionTest");

        test();
    }
}
