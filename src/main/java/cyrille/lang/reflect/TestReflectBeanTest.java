/*
 * Created on Mar 30, 2004
 */
package cyrille.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TestReflectBeanTest extends TestCase {

    /**
     * Constructor for TestReflectBeanTest.
     * 
     * @param name
     */
    public TestReflectBeanTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestReflectBeanTest.class);
    }

    public void test() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String expected = "test";
        TestReflectBean bean = new TestReflectBean(expected);
        {
            Field[] fields = TestReflectBean.class.getFields();
            for (Field field : fields) {
                System.out.println("field [" + field.getName() + ", type=" + field.getType() + "]");
            }
        }
        {
            Method[] methods = TestReflectBean.class.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(method.toString());
            }
        }
        {
            Field[] declaredFields = TestReflectBean.class.getDeclaredFields();
            for (Field field : declaredFields) {
                System.out.println("declaredField [" + field.getName() + ", modifiers=" + field.getModifiers() + ", accessible="
                        + field.isAccessible() + ", type=" + field.getType() + "]");
                field.setAccessible(true);
            }
        }
        Field field = TestReflectBean.class.getDeclaredField("fieldHiddenByAGetter");
        field.setAccessible(true);
        Object actual = field.get(bean);
        assertEquals(expected, actual);
    }
}
