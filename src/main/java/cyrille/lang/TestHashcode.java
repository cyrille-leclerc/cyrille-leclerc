/*
 * Created on Apr 27, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package cyrille.lang;

import java.util.Locale;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code
 * Generation>Code and Comments
 */
public class TestHashcode {
    public static void main(String[] args) {
        try {
            TestHashcode testHashcode = new TestHashcode();
            testHashcode.testArrayHashCode();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testArrayHashCode() throws Exception {
        Object[] array = new Object[] { "toto", Locale.FRENCH, new MyObject() };
        int hashCode = array.hashCode();
        System.out.println("hashcode = " + hashCode);
    }

    public class MyObject {
        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            System.out.println("goes here");
            return super.hashCode();
        }
    }

}
