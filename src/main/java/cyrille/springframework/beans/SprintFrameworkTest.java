/*
 * Created on Apr 15, 2006
 */
package cyrille.springframework.beans;

import java.net.URL;

import junit.framework.TestCase;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import cyrille.sample.product.Color;
import cyrille.sample.product.Product;
import cyrille.sample.product.Size;

public class SprintFrameworkTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SprintFrameworkTest.class);
    }

    public void test() throws Exception {

        URL url = getClass().getResource("beans.xml");
        Resource res = new UrlResource(url);
        XmlBeanFactory factory = new XmlBeanFactory(res);

        Color red = (Color) factory.getBean("color-red");
        System.out.println(red);

        Size size = (Size) factory.getBean("size-9Male");
        System.out.println(size);

        Product product = (Product) factory.getBean("myProduct");
        System.out.println(product);

        String[] beanNames = factory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
