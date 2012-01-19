package cyrille.springframework.beans;

import static junit.framework.Assert.*;

import java.net.URL;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.UrlResource;

import cyrille.sample.product.Color;

public class PlaceholderConfigurerTest {

    @Test
    public void test() throws Exception {

        URL xmlConfigurationUrl = getClass().getResource("placeholderconfigurer-beans.xml");
        XmlBeanFactory factory = new XmlBeanFactory(new UrlResource(xmlConfigurationUrl));

        // create placeholderconfigurer to bring in some property values from a
        // Properties file
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        URL propertiesConfigurationUrl = getClass().getResource("placeholderconfigurer-beans.properties");
        propertyPlaceholderConfigurer.setLocation(new UrlResource(propertiesConfigurationUrl));
        // now actually do the replacement
        propertyPlaceholderConfigurer.postProcessBeanFactory(factory);

        Color red = (Color) factory.getBean("color-red");
        System.out.println(red);
        assertEquals("This is red", red.getDescription());

    }
}
