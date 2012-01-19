package cyrille.hibernate;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateCglibProxyTest extends TestCase {
    SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AnnotationConfiguration configuration = new AnnotationConfiguration();

        configuration.configure();
        configuration.addAnnotatedClass(Color.class);
        this.sessionFactory = configuration.buildSessionFactory();

    }

    @Entity
    @Table(name = "color")
    public static class Color implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue
        Long id;

        @Basic
        @Column(name = "name", nullable = false, length = 255)
        String name;

        public Color() {
            super();
        }

        public Color(String name) {
            super();
            this.name = name;
        }

        public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
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
            final Color other = (Color) obj;

            return new EqualsBuilder().append(this.id, other.id).isEquals();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("id", this.id).append("name", this.name).toString();
        }
    }

    public void testEquals() throws Exception {
        Long redId;
        {
            Color red = new Color("red");
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            this.sessionFactory.getCurrentSession().save(red);

            // sessionFactory.getCurrentSession().flush();
            redId = red.getId();

            Color redLoadedAfterFlush = (cyrille.hibernate.HibernateCglibProxyTest.Color) this.sessionFactory.getCurrentSession().load(
                    Color.class, redId);
            assertSame(red, redLoadedAfterFlush);

            this.sessionFactory.getCurrentSession().getTransaction().commit();

        }

        {
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Color red1 = (Color) this.sessionFactory.getCurrentSession().load(Color.class, redId);
            Color red2 = (Color) this.sessionFactory.getCurrentSession().load(Color.class, redId);
            assertSame(red1, red2);
            this.sessionFactory.getCurrentSession().getTransaction().commit();

        }
    }
}
