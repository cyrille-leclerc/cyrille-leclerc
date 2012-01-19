/*
 * Created on May 7, 2006
 */
package cyrille.springframework.hibernate3;

import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import cyrille.sample.person.Address;
import cyrille.sample.person.Person;

public class PersonDaoImplTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PersonDaoImplTest.class);
    }

    public void testSaveOrUpdate() throws Exception {
        URL url = getClass().getResource("beans.xml");
        Resource res = new UrlResource(url);
        XmlBeanFactory factory = new XmlBeanFactory(res);

        SessionFactory sessionFactory = (SessionFactory) factory.getBean("sessionFactory");
        System.out.println(sessionFactory);

        PersonDao personDao = (PersonDao) factory.getBean("personDao");

        Person newPerson = new Person("Le Clerc", "Cyrille", new Address("86, av. des Ternes", "Paris", "75017"));
        personDao.saveOrUpdate(newPerson);

        System.out.println("newPerson=" + newPerson);

        Person person = personDao.loadPersonById(newPerson.getId());
        System.out.println("person=" + person);
    }

    public void testFindByName() throws Exception {
        System.setProperty("hibernate.cglib.use_reflection_optimizer", "false");
        URL url = getClass().getResource("beans.xml");
        Resource res = new UrlResource(url);
        XmlBeanFactory factory = new XmlBeanFactory(res);

        SessionFactory sessionFactory = (SessionFactory) factory.getBean("sessionFactory");
        System.out.println(sessionFactory);

        PersonDao personDao = (PersonDao) factory.getBean("personDao");

        Person newPerson = new Person("Le Clerc", "Cyrille", new Address("86, av. des Ternes", "Paris", "75017"));
        personDao.saveOrUpdate(newPerson);

        System.out.println("newPerson=" + newPerson);

        List<Person> persons = personDao.loadPersonsByName("Le Clerc");
        for (Object element : persons) {
            Person person = (Person) element;
            System.out.println(person);
        }
    }
}
