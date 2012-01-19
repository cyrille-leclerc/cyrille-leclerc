/*
 * Created on May 17, 2007
 */
package fr.xebia.sample.springframework.jmx;

import java.net.URL;

import junit.framework.TestCase;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ServletContextAwareMetadataNamingStrategyTest extends TestCase {

    public void test() throws Exception {

        URL url = getClass().getResource("beans-ServletContextAwareMetadataNamingStrategy.xml");
        Resource res = new UrlResource(url);
        XmlBeanFactory factory = new XmlBeanFactory(res);

        MyService myService = (MyService) factory.getBean("myService");
        System.out.println(myService);

        String[] beanNames = factory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = factory.getBean(beanName);
            System.out.println(beanName + " = " + bean);
        }

        // Thread.sleep(Long.MAX_VALUE);

    }
}
