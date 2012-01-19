/*
 * Created on Apr 15, 2006
 */
package cyrille.springframework.jmx;

import java.net.URL;
import java.rmi.registry.Registry;

import junit.framework.TestCase;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class JmxCustomNamingStrategyTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(JmxCustomNamingStrategyTest.class);
    }

    public void test() throws Exception {

        URL url = getClass().getResource("beans-customNamingStrategy.xml");
        Resource res = new UrlResource(url);
        XmlBeanFactory factory = new XmlBeanFactory(res);

        Java5AnnotedJmxTestServiceImpl java5AnnotedJmxTestServiceImpl = (Java5AnnotedJmxTestServiceImpl) factory
                .getBean("java5AnnotedJmxTestServiceImpl");
        System.out.println(java5AnnotedJmxTestServiceImpl);

        Registry registry = (Registry) factory.getBean("registry");
        System.out.println(registry);

        Object serverConnector = factory.getBean("serverConnector");
        System.out.println(serverConnector);

        Object exporter = factory.getBean("exporter");
        System.out.println(exporter);

        String[] beanNames = factory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = factory.getBean(beanName);
            System.out.println(beanName + " = " + bean);
        }

        Thread.sleep(Long.MAX_VALUE);

    }
}
