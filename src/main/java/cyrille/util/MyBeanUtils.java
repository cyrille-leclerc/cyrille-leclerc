package cyrille.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.Validate;

public class MyBeanUtils {
    @SuppressWarnings("unchecked")
    public static boolean isSimpleType(Object object) {
        Validate.notNull(object);

        if (object.getClass().isPrimitive()) {
            return true;
        }
        Class[] simpleTypes = new Class[] { String.class, Number.class, Date.class, Boolean.class, Currency.class, Byte.class,
                StringBuffer.class, URL.class, Calendar.class, CharSequence.class, Character.class };
        for (Class simpleType : simpleTypes) {
            if (simpleType.isAssignableFrom(object.getClass())) {
                return true;
            }
        }
        return false;
    }

    public static void assertRecursiveEquals(Object expected, Object actual) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        // System.out.println("Evaluate expected:" + expected + ", actual:" + actual );
        if (expected == null && actual == null) {
            return;
        }
        if ((expected == null && actual != null) || (expected != null && actual == null)) {
            throw new AssertionFailedError("expected:<" + expected + "> but was:<" + actual + ">");
        }

        if (isSimpleType(expected)) {
            Assert.assertEquals(expected, actual);
            return;
        }

        if (expected.getClass().isArray()) {
            Assert.assertTrue("Expected array for" + actual, actual.getClass().isArray());
            Object[] expectedArray = (Object[]) expected;
            Object[] actualArray = (Object[]) actual;
            Assert.assertEquals("size", expectedArray.length, actualArray.length);
            for (int i = 0; i < expectedArray.length; i++) {
                Object expectedElement = expectedArray[i];
                Object actualElement = actualArray[i];
                assertRecursiveEquals(expectedElement, actualElement);
            }
        } else if (expected instanceof StringBuffer) {
            Assert.assertTrue("Expected java.util.StringBuffer for" + actual, actual instanceof StringBuffer);
            assertRecursiveEquals(expected.toString(), actual.toString());
        } else if (expected instanceof Collection) {
            Assert.assertTrue("Expected java.util.Collection for" + actual, actual instanceof Collection);
            Collection expectedCollection = (Collection) expected;
            Collection actualCollection = (Collection) actual;
            assertRecursiveEquals(expectedCollection.toArray(), actualCollection.toArray());
        } else if (expected instanceof Map) {
            throw new UnsupportedOperationException("Map comparison is not yet supported");
        } else {
            PropertyDescriptor expectedDescriptors[] = PropertyUtils.getPropertyDescriptors(expected);
            for (PropertyDescriptor element : expectedDescriptors) {
                String name = element.getName();
                if ("class".equals(name)) {
                    // do not compare
                } else if (PropertyUtils.isReadable(expected, name)) {
                    Object expectedProperty = PropertyUtils.getSimpleProperty(expected, name);
                    Object actualProperty = PropertyUtils.getSimpleProperty(actual, name);
                    try {
                        assertRecursiveEquals(expectedProperty, actualProperty);
                    } catch (AssertionFailedError afe) {
                        throw new AssertionFailedError(expected.getClass().getSimpleName() + "#" + name + " : " + afe.getLocalizedMessage());
                    }
                }
            }
        }
    }

}
