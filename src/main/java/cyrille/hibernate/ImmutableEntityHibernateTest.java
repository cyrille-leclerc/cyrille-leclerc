package cyrille.hibernate;

import java.io.Serializable;
import java.sql.Connection;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.engine.SessionFactoryImplementor;
import org.junit.Before;
import org.junit.Test;

public class ImmutableEntityHibernateTest {
    @Entity()
    @org.hibernate.annotations.Entity(mutable = false)
    public static class Gender implements Serializable {
        
        /**
         * JPA requires a public or protected no-arg constructor
         */
        protected Gender() {
            this(null, null);
        }
        
        public Gender(Long id, String name) {
            super();
            this.id = id;
            this.name = name;
        }
        
        private static final long serialVersionUID = 1L;
        
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private final Long id;
        
        @Basic
        private final String name;
        
        public String getName() {
            return name;
        }
        
        @Override
        public String toString() {
            return new ToStringBuilder(this).append("id", this.id).append("name", this.name).toString();
        }
    }
    
    SessionFactory sessionFactory;
    
    @Before
    public void setUp() throws Exception {
        AnnotationConfiguration configuration = new HsqldbAnnotationConfiguration();
        
        configuration.addAnnotatedClass(Gender.class);
        
        configuration.configure();
        
        this.sessionFactory = configuration.buildSessionFactory();
        
        Connection connection = ((SessionFactoryImplementor)sessionFactory).getConnectionProvider().getConnection();
        connection.createStatement().execute("insert into gender(id, name) values (1, 'MALE')");
        connection.createStatement().execute("insert into gender(id, name) values (2, 'FEMALE')");
        connection.commit();
    }
    
    @Test
    public void test() throws Exception {
        sessionFactory.getCurrentSession().beginTransaction();
        Gender male = (Gender)sessionFactory.getCurrentSession().get(Gender.class, 1L);
        Gender female = (Gender)sessionFactory.getCurrentSession().get(Gender.class, 2L);
        System.out.println(male);
        System.out.println(female);
        sessionFactory.getCurrentSession().getTransaction().commit();
    }
}
