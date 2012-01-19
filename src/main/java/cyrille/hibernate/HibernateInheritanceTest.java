package cyrille.hibernate;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.cfg.AnnotationConfiguration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class HibernateInheritanceTest extends TestCase {
    @Entity
    @DiscriminatorValue(value = "boy-boy")
    public static class Boy extends Person {
        private static final long serialVersionUID = 1L;

        public Boy() {
            super();
        }

        public Boy(String name) {
            super(name, "boy", "boy");
        }

    }

    @Entity
    @DiscriminatorValue(value = "girl-girl")
    public static class Girl extends Person {
        public Girl() {
            super();
        }

        public Girl(String name) {
            super(name, "girl", "girl");
        }

        private static final long serialVersionUID = 1L;
    }

    @Entity()
    @Table(name = "person")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorFormula(value = "discriminator1 || '-' || discriminator2")
    public abstract static class Person implements Serializable {
        private static final long serialVersionUID = 1L;

        @Basic
        @Column(name = "discriminator1", nullable = false, length = 255)
        String discriminator1;

        @Basic
        @Column(name = "discriminator2", nullable = false, length = 255)
        String discriminator2;

        @Id
        @GeneratedValue
        Long id;

        @Basic
        @Column(name = "name", nullable = false, length = 255)
        @XStreamAlias("name")
        String name;

        public Person() {
            super();
        }

        public Person(String name, String discriminator1, String discriminator2) {
            super();
            this.name = name;
            this.discriminator1 = discriminator1;
            this.discriminator2 = discriminator2;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Person other = (Person) obj;

            return new EqualsBuilder().append(this.id, other.id).isEquals();
        }

        public String getDiscriminator1() {
            return this.discriminator1;
        }

        public String getDiscriminator2() {
            return this.discriminator2;
        }

        public Long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(this.id).toHashCode();
        }

        public void setDiscriminator1(String discriminator1) {
            this.discriminator1 = discriminator1;
        }

        public void setDiscriminator2(String discriminator2) {
            this.discriminator2 = discriminator2;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String firstName) {
            this.name = firstName;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("id", this.id).append("firstName", this.name).toString();
        }
    }

    SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AnnotationConfiguration configuration = new AnnotationConfiguration();

        configuration.configure();
        configuration.addAnnotatedClass(Person.class).addAnnotatedClass(Boy.class).addAnnotatedClass(Girl.class);
        configuration.buildMappings();
        this.sessionFactory = configuration.buildSessionFactory();

    }

    public void test() throws Exception {
        Long tomId;
        Long britneyId;
        {
            // INITIALIZE
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Boy tom = new Boy("tom");
            Girl britney = new Girl("britney");
            this.sessionFactory.getCurrentSession().save(tom);
            this.sessionFactory.getCurrentSession().save(britney);
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            tomId = tom.getId();
            britneyId = britney.getId();
        }

        {
            // LOAD
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Boy tom = (Boy) this.sessionFactory.getCurrentSession().get(Person.class, tomId);
            Girl britney = (Girl) this.sessionFactory.getCurrentSession().get(Person.class, britneyId);

            assertEquals("tom", tom.getName(), "tom");
            assertEquals("britney", britney.getName(), "britney");
            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }

    }

}
