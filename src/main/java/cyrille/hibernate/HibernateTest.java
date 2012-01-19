/*
 * Created on Feb 14, 2007
 */
package cyrille.hibernate;

import java.sql.Timestamp;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import cyrille.sample.person.Person;

public class HibernateTest extends TestCase {

    SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AnnotationConfiguration configuration = new AnnotationConfiguration();

        configuration.addAnnotatedClass(Person.class).addAnnotatedClass(AuditRecord.class);

        configuration.configure();
        AuditInterceptor auditInterceptor = new AuditInterceptor();
        configuration.setInterceptor(auditInterceptor);

        this.sessionFactory = configuration.buildSessionFactory();
        auditInterceptor.setSessionFactory(this.sessionFactory);

    }

    public void test() throws Exception {
        Long id;
        {
            System.out.println("INSERT NEW");
            this.sessionFactory.getCurrentSession().beginTransaction();

            Person person = new Person("Le Clerc", "Cyrille", null);
            this.sessionFactory.getCurrentSession().save(person);

            this.sessionFactory.getCurrentSession().getTransaction().commit();

            id = person.getId();
        }
        {
            System.out.println("SELECT");
            this.sessionFactory.getCurrentSession().beginTransaction();
            Person person = (Person) this.sessionFactory.getCurrentSession().get(Person.class, id);

            person.setComment("comment " + new Timestamp(System.currentTimeMillis()));

            this.sessionFactory.getCurrentSession().update(person);
            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }
    }
}
