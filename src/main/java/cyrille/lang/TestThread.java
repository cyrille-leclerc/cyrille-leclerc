/*
 * Created on Aug 10, 2005
 */
package cyrille.lang;

import junit.framework.TestCase;

public class TestThread extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestThread.class);
    }

    private static ThreadLocal mythreadLocalVariable = new ThreadLocal();

    public void myMethod() {
        mythreadLocalVariable.set("my-value");
        try {
            // do job that relies on my thread local variable
        } finally {
            mythreadLocalVariable.set(null);
        }
    }

    public void testLock() {
        final Object lock = new Object();
        Runnable runnable = new Runnable() {

            public void run() {
                System.out.println("child thread sleep for 1 s ...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Child thread Notify all");
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        };
        Thread child = new Thread(runnable);
        System.out.println("start child thread");
        child.start();
        System.out.println("wait ...");
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("parent thread awakened");
    }
}
