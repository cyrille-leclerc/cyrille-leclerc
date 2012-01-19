package cyrille.lang;

import junit.framework.TestCase;

public class LangTest extends TestCase {

    public void testInstanceOf() throws Exception {
        Object object = new Object();

        boolean actual = String.class.isInstance(object);

        assertFalse(actual);
    }

    /**
     * test null value
     * 
     * @throws Exception
     */
    public void testInstanceOfWithNull() throws Exception {

        boolean actual = String.class.isInstance(null);

        assertFalse(actual);
    }
}
