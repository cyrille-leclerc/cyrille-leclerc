package cyrille.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class MergeTest extends TestCase {

    @Entity
    public static class Customer {

        @Temporal(value = TemporalType.DATE)
        protected Date birthDate;

        @Id
        @GeneratedValue
        protected Long id;

        @Basic
        protected String lastName;

        @Version
        protected int version;

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Customer other = (Customer) obj;

            return new EqualsBuilder().append(this.id, other.id).isEquals();
        }

        public Date getBirthDate() {
            return birthDate;
        }

        public Long getId() {
            return id;
        }

        public String getLastName() {
            return lastName;
        }

        public int getVersion() {
            return version;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(this.id).toHashCode();
        }

        public void setBirthDate(Date birthDate) {
            this.birthDate = birthDate;
        }

        public void setId(Long id) {
            System.out.println("use setId on " + this);
            this.id = id;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("id", this.id).append("version", this.version).append("lastName", this.lastName)
                    .append("birthDate", this.birthDate).toString();
        }
    }

    protected SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        AnnotationConfiguration configuration = new HsqldbAnnotationConfiguration();
        configuration.addAnnotatedClass(Customer.class);
        configuration.configure();

        this.sessionFactory = configuration.buildSessionFactory();
    }

    public void testMerge() throws Exception {

        Long customerId;
        {
            // CREATE
            sessionFactory.getCurrentSession().beginTransaction();

            Customer customer = new Customer();
            customer.setLastName("Le Clerc");
            sessionFactory.getCurrentSession().save(customer);

            sessionFactory.getCurrentSession().getTransaction().commit();

            System.out.println("created customer: " + customer);
            customerId = customer.getId();

            sessionFactory.getCurrentSession().close();
        }

        {
            // RELOAD AND UPDATE
            sessionFactory.getCurrentSession().beginTransaction();
            Customer customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class, customerId);
            customer.setBirthDate(new GregorianCalendar(1976, 0, 5).getTime());

            sessionFactory.getCurrentSession().update(customer);

            sessionFactory.getCurrentSession().getTransaction().commit();
            System.out.println("updated customer: " + customer);

            sessionFactory.getCurrentSession().close();
        }

        {
            // SIMULATE WEB UPDATE
            String id, version, lastName, birthDate;
            {
                // GENERATE PAGE
                sessionFactory.getCurrentSession().beginTransaction();
                Customer customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class, customerId);

                id = customer.getId().toString();
                version = String.valueOf(customer.getVersion());
                lastName = customer.getLastName();
                birthDate = DateFormatUtils.format(customer.getBirthDate(), "yyyy/MM/dd");
                sessionFactory.getCurrentSession().getTransaction().commit();

                sessionFactory.getCurrentSession().close();
            }
            concurrentModification(customerId);
            {
                // UPDATE DATA IN THE WEB FORM
                System.out.println("web modificatino of version " + version);
                birthDate = "1975/01/01";
            }
            {
                // UPDATE OBJECT WITH FORM DATA
                sessionFactory.getCurrentSession().beginTransaction();

                // business check
                Customer customerforCheck = (Customer) sessionFactory.getCurrentSession().get(Customer.class, customerId);
                customerforCheck.getBirthDate();

                // hibernate update
                Customer customer = new Customer();
                customer.setId(new Long(id));
                customer.setVersion(Integer.parseInt(version));
                customer.setLastName(lastName);
                customer.setBirthDate(new SimpleDateFormat("yyyy/MM/dd").parse(birthDate));

                /*
                 * merge = - load from session - override each field's value - recurse ?
                 */
                Customer mergedCustomer = (Customer) sessionFactory.getCurrentSession().merge(customer);
                assertSame(customerforCheck, mergedCustomer);

                sessionFactory.getCurrentSession().getTransaction().commit();

                sessionFactory.getCurrentSession().close();

                System.out.println("updated reconnected customer: " + customer);
            }

        }
    }

    public void concurrentModification(Long customerId) {
        sessionFactory.getCurrentSession().beginTransaction();
        Customer customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class, customerId);
        System.out.println("concurrent modification of version " + customer.getVersion());
        
        customer.setBirthDate(new GregorianCalendar(1989, 03, 06).getTime());

        sessionFactory.getCurrentSession().save(customer);
        sessionFactory.getCurrentSession().getTransaction().commit();

        System.out.println("concurrently modified customer " + customer);
        sessionFactory.getCurrentSession().close();

    }
}
