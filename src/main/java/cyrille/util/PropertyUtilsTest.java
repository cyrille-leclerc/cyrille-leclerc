package cyrille.util;

import java.beans.PropertyDescriptor;

import junit.framework.TestCase;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class PropertyUtilsTest extends TestCase {

    public void testSimpleProperty() {
        String aString = "a string";
        System.out.println(ReflectionToStringBuilder.toString(aString));
        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(aString);

        for (PropertyDescriptor propertyDescriptor : descriptors) {
            System.out.println(propertyDescriptor.getDisplayName());
        }

    }
}
