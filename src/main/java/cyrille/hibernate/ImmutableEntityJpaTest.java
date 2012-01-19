package cyrille.hibernate;

import java.io.Serializable;
import java.sql.Connection;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.junit.Before;
import org.junit.Test;

public class ImmutableEntityJpaTest {
    @Entity()
    @org.hibernate.annotations.Entity(mutable = false)
    @Table(name="gender")
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
    
    EntityManagerFactory entityManagerFactory;
    
    @Before
    public void setUp() throws Exception {
        Ejb3Configuration configuration = new HsqldbEjb3Configuration();
        
        configuration.addAnnotatedClass(Gender.class);
        
        
        this.entityManagerFactory = configuration.buildEntityManagerFactory();
        SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor)((HibernateEntityManagerFactory)this.entityManagerFactory).getSessionFactory();
        
        Connection connection = sessionFactoryImplementor.getConnectionProvider().getConnection();
        connection.createStatement().execute("insert into gender(id, name) values (1, 'MALE')");
        connection.createStatement().execute("insert into gender(id, name) values (2, 'FEMALE')");
        connection.commit();
    }
    
    @Test
    public void test() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        entityManager.getTransaction().begin();
        Gender male = entityManager.getReference(Gender.class, 1L);
        Gender female = entityManager.getReference(Gender.class, 2L);
        System.out.println(male);
        System.out.println(female);
        entityManager.getTransaction().commit();
    }
}
