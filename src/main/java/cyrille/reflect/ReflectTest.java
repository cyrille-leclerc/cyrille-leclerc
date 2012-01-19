package cyrille.reflect;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public class ReflectTest extends TestCase {

    public void testInvokeStaticMethod() throws Exception {
        Method valueOfMethod = Long.class.getMethod("valueOf", new Class[] { String.class });

        Long actual = (Long) valueOfMethod.invoke(null, new Object[] { "2" });
        Long expected = Long.valueOf(2);
        assertEquals(expected, actual);
    }

    public void test() throws Exception {
        String[] myStringArray = new String[] { "one", "two" };
        Class clazz = myStringArray.getClass();
        Class componentType = clazz.getComponentType();
        System.out.println("clazz : " + clazz);
        System.out.println("componentType : " + componentType);
    }
}
