package cyrille.util;

import java.util.Locale;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 * Test of Locale class
 */
public class TestLocale {
    public static void main(String[] args) {
        try {
            TestLocale test = new TestLocale();
            test.testContructor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for TestLocale.
     */
    public TestLocale() {
        super();
    }

    public void testContructor() throws Exception {
        Locale locale = new Locale("fr", "fr");
        System.out.println("French : " + locale);
        locale = new Locale("zz", "");
        System.out.println("zz : " + locale);
    }
}
