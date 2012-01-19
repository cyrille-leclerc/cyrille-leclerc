package cyrille.hibernate;

import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

import cyrille.sample.CompositeKey;
import cyrille.sample.PersistentObjectWithCompositeKey;
import cyrille.sample.Person;

public class HibernateConfigurationTest extends TestCase {

    @SuppressWarnings("unchecked")
    public void test() throws Exception {
        AnnotationConfiguration configuration = new AnnotationConfiguration();

        configuration.configure();
        configuration.addAnnotatedClass(Person.class).addAnnotatedClass(PersistentObjectWithCompositeKey.class);
        configuration.buildMappings();

        System.out.println("CONFIGURATION PROPERTIES");
        Properties properties = configuration.getProperties();
        for (Entry<Object, Object> property : properties.entrySet()) {            
            System.out.println(property.getKey() + "=" + property.getValue());            
        }
        
        System.out.println("MAPPING");
        Iterator<PersistentClass> mappings = configuration.getClassMappings();
        while (mappings.hasNext()) {
            PersistentClass persistentClass = mappings.next();
            Property identifierProperty = persistentClass.getIdentifierProperty();
            System.out
                    .println("persistentClass=" + persistentClass.getClassName() + ", identifierProperty=" + identifierProperty.getName());
        }

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        sessionFactory.getCurrentSession().getTransaction().begin();

        {
            Person newPerson = new Person();
            newPerson.setLastName("Le Clerc");
            newPerson.setFirstName("Cyrille");
            sessionFactory.getCurrentSession().saveOrUpdate(newPerson);
        }
        {
            PersistentObjectWithCompositeKey powck = new PersistentObjectWithCompositeKey();
            CompositeKey compositeKey = new CompositeKey();
            powck.setCompositeKey(compositeKey);

            compositeKey.setId1(Long.valueOf(1));
            compositeKey.setId2(Long.valueOf(2));
            sessionFactory.getCurrentSession().saveOrUpdate(powck);
        }
        sessionFactory.getCurrentSession().getTransaction().commit();

    }
}
