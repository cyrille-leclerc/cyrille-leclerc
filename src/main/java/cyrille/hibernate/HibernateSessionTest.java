package cyrille.hibernate;

import java.math.BigInteger;
import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import cyrille.sample.Person;

public class HibernateSessionTest extends TestCase {

    /**
     * Check the {@link Session#isDirty()} status after rollback.
     */
    public void testSessionDirtyFlagAfterRollback() throws Exception {

        AnnotationConfiguration configuration = new HsqldbAnnotationConfiguration();
        configuration.addAnnotatedClass(Person.class);
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Person person = buildPerson();

        session.saveOrUpdate(person);
        
        session.flush();

        assertTrue(session.isDirty());

        session.getTransaction().rollback();

        assertFalse(session.isDirty());
    }

    private Person buildPerson() {
        Person person = new Person("Le Clerc", "Cyrille", new Date(), 31, new Integer(31), BigInteger.TEN);
        return person;
    }
}
