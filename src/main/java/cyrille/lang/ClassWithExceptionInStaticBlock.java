/*
 * Created on Feb 28, 2005
 */
package cyrille.lang;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class ClassWithExceptionInStaticBlock {

    static {
        if (true) {
            throw new RuntimeException("broken");
        }
    }

    public static void myStaticMethod() {

    }
}