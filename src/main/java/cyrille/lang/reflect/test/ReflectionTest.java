/*
 * Created on Jul 1, 2005
 */
package cyrille.lang.reflect.test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;

public class ReflectionTest extends TestCase {

    public Server getServerSample(String key) {
        Server server = new Server("server-id-" + key, "server-name-" + key);

        WebContainer webContainer = new WebContainer("webContainer-id-" + key);
        server.setWebContainer(webContainer);

        for (int i = 0; i < 2; i++) {
            Transport transport = new Transport("transport-id-" + i + "-" + key, "host-" + i + "-" + key);
            webContainer.getTransports().add(transport);
        }

        ElementWithoutId elementWithoutId = new ElementWithoutId("element-id-" + key);
        webContainer.setElementWithoutId(elementWithoutId);

        return server;
    }

    public void test() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Server actual = getServerSample("actual");
        Server expected = getServerSample("expected");
        introspect(actual, expected, null, "");
    }

    public void introspect(Object actual, Object expected, String parentId, String offset) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        if (PropertyUtils.getPropertyDescriptor(actual, "id") == null) {
            System.out.println(offset + "/?\\ object does NOT have an id");
        } else {
            parentId = (String) PropertyUtils.getSimpleProperty(actual, "id");
        }

        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(actual);

        for (PropertyDescriptor propertyDescriptor : origDescriptors) {
            String name = propertyDescriptor.getName();
            Class type = propertyDescriptor.getPropertyType();
            Object actualValue = PropertyUtils.getSimpleProperty(actual, name);
            Object expectedValue = PropertyUtils.getSimpleProperty(expected, name);

            if ("class".equals(name)) {
                // ignore
            } else if ("id".equals(name)) {
                System.out.println(offset + "skip id field");
            } else if (type.isArray()) {
                /*
                 * System.out.println(offset + "Array Field " + name); Object[] actualArray =
                 * (Object[]) actualValue; Object[] expectedArray = (Object[]) expectedValue; for
                 * (int j = 0; j < actualArray.length; j++) {
                 * 
                 * Object actualObject = actualArray[j]; Object expectedObject = expectedArray[j];
                 * 
                 * System.out.println(offset + "\tElement " + actualObject.getClass());
                 * introspect(actualObject, expectedObject, offset + "\t\t"); }
                 */
            } else if (String.class.equals(type) || Number.class.isAssignableFrom(type) || int.class.equals(type)
                    || long.class.equals(type) || float.class.equals(type) || double.class.equals(type)
                    || Date.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type)) {

                if (ObjectUtils.equals(actualValue, expectedValue)) {
                    System.out.println(offset + "Equal Simple Field " + name + "=" + actualValue);
                } else {
                    System.out.println(offset + "/!\\ Simple Field " + name + " actual=" + actualValue + ", expected=" + expectedValue
                            + ", parentId=" + parentId);
                }

            } else if (Map.class.isAssignableFrom(type)) {
                throw new UnsupportedOperationException();
            } else if (Collection.class.isAssignableFrom(type)) {
                /*
                 * System.out.println(offset + "Collection Field " + name); Collection
                 * actualCollection = (Collection) actualValue; Collection expectedCollection =
                 * (Collection) expectedValue; for (Iterator it = actualCollection.iterator();
                 * it.hasNext();) { Object object = it.next(); System.out.println(offset +
                 * "\tElement " + object.getClass()); introspect(object, offset + "\t\t"); }
                 */
            } else {
                System.out.println(offset + "Complex Field " + name);
                introspect(actualValue, expectedValue, parentId, "\t" + offset);
            }
        }
    }
}
