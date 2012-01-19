/*
 * Created on Apr 15, 2006
 */
package cyrille.springframework.jmx;

import java.net.URL;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class JmxIdentityNamingStrategyTest {
    
    @Test
    public void test() throws Exception {
        
        URL url = getClass().getResource("beans-identityNamingStrategy.xml");
        Resource res = new UrlResource(url);
        XmlBeanFactory factory = new XmlBeanFactory(res);
        
        Java5AnnotedJmxTestServiceImpl java5AnnotedJmxTestServiceImpl = (Java5AnnotedJmxTestServiceImpl)factory
            .getBean("java5AnnotedJmxTestServiceImpl");
        System.out.println(java5AnnotedJmxTestServiceImpl);
        
        Object exporter = factory.getBean("exporter");
        System.out.println(exporter);
        
        String[] beanNames = factory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        
        Thread.sleep(Long.MAX_VALUE);
        
    }
}
