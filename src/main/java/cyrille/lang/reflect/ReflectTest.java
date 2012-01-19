/*
 * Created on Apr 7, 2005
 */
package cyrille.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class ReflectTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ReflectTest.class);
    }

    public void testIntrospectClass() throws Exception {
        String className = "";
        Class clazz = Class.forName(className);
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            constructor.toString();
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.toString();
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            method.toString();
        }
    }
}