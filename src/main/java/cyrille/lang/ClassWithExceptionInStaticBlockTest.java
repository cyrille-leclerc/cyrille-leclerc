/*
 * Created on Feb 28, 2005
 */
package cyrille.lang;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class ClassWithExceptionInStaticBlockTest extends TestCase {

    public void testInstantiation() {
        System.out.println("> testInstantiation");
        try {
            ClassWithExceptionInStaticBlock o = new ClassWithExceptionInStaticBlock();
            System.out.println(o);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void testCallAStaticMethod() {
        System.out.println("> testCallAStaticMethod");
        try {
            ClassWithExceptionInStaticBlock o = new ClassWithExceptionInStaticBlock();
            System.out.println(o);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}