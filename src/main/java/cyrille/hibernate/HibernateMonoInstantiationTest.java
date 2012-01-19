package cyrille.hibernate;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateMonoInstantiationTest extends TestCase {
    SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AnnotationConfiguration configuration = new AnnotationConfiguration();

        configuration.configure();
        configuration.addAnnotatedClass(Product.class).addAnnotatedClass(Color.class);
        configuration.buildMappings();
        this.sessionFactory = configuration.buildSessionFactory();

    }

    public void testMonoInstantiationViaMultipleLoadAndGet() throws Exception {
        Long redId;
        {
            // INITIALIZE DB
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Color red = new Color("red");
            this.sessionFactory.getCurrentSession().save(red);
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            // get generated value
            redId = red.getId();
        }

        {
            // REPEATED GET
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Color red1 = (Color) this.sessionFactory.getCurrentSession().get(Color.class, redId);
            Color red2 = (Color) this.sessionFactory.getCurrentSession().get(Color.class, redId);

            assertSame(red1, red2);

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }
        {
            // REPEATED LOAD
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Color red1 = (Color) this.sessionFactory.getCurrentSession().load(Color.class, redId);
            Color red2 = (Color) this.sessionFactory.getCurrentSession().load(Color.class, redId);

            assertSame(red1, red2);

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }
        {
            // LOAD THEN GET
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Color red1 = (Color) this.sessionFactory.getCurrentSession().load(Color.class, redId);
            Color red2 = (Color) this.sessionFactory.getCurrentSession().get(Color.class, redId);

            assertSame(red1, red2);

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }
        {
            // GET THEN LOAD
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Color red1 = (Color) this.sessionFactory.getCurrentSession().load(Color.class, redId);
            Color red2 = (Color) this.sessionFactory.getCurrentSession().get(Color.class, redId);

            assertSame(red1, red2);

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }

    }

    public void testMonoInstantiationViaReference() throws Exception {

        Long carId;
        Long redId;
        {
            // INITIALIZE DB
            this.sessionFactory.getCurrentSession().getTransaction().begin();
            Color blue = new Color("blue");
            Color red = new Color("red");
            Product car = new Product("car", red);
            this.sessionFactory.getCurrentSession().save(blue);
            this.sessionFactory.getCurrentSession().save(red);
            this.sessionFactory.getCurrentSession().save(car);
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            // get generated values
            redId = red.getId();
            carId = car.getId();
        }

        {
            // GET RED FROM CAR THEN DIRECTLY RED FROM SESSION
            this.sessionFactory.getCurrentSession().getTransaction().begin();

            Product car = (Product) this.sessionFactory.getCurrentSession().get(Product.class, carId);
            Color redViaCarReference = car.getColor();
            assertEquals("red", redViaCarReference.getName());

            Color red = (Color) this.sessionFactory.getCurrentSession().get(Color.class, redId);
            assertEquals("red", red.getName());

            assertSame(red, redViaCarReference);

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }
        {
            // GET DIRECTLY RED FROM SESSION THEN RED FROM CAR
            this.sessionFactory.getCurrentSession().getTransaction().begin();

            Color red = (Color) this.sessionFactory.getCurrentSession().get(Color.class, redId);
            assertEquals("red", red.getName());

            Product car = (Product) this.sessionFactory.getCurrentSession().get(Product.class, carId);
            Color redViaCarReference = car.getColor();
            assertEquals("red", redViaCarReference.getName());

            assertSame(red, redViaCarReference);

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }
        {
            // LOAD DIRECTLY RED FROM SESSION THEN RED FROM CAR
            this.sessionFactory.getCurrentSession().getTransaction().begin();

            Color red = (Color) this.sessionFactory.getCurrentSession().load(Color.class, redId);
            assertEquals("red", red.getName());

            Product car = (Product) this.sessionFactory.getCurrentSession().get(Product.class, carId);
            Color redViaCarReference = car.getColor();
            assertEquals("red", redViaCarReference.getName());

            assertSame(red, redViaCarReference);

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }

    }

    @Entity
    @Table(name = "product")
    public static class Product implements Serializable {
        private static final long serialVersionUID = 1L;

        @ManyToOne
        @JoinColumn(name = "color", nullable = true)
        Color color;

        @Id
        @GeneratedValue
        Long id;

        @Basic
        @Column(name = "name", nullable = false, length = 255)
        String name;

        public Product() {
            super();
        }

        public Product(String name, Color color) {
            super();
            this.name = name;
            this.color = color;
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
            final Product other = (Product) obj;

            return new EqualsBuilder().append(this.id, other.id).isEquals();
        }

        public Color getColor() {
            return this.color;
        }

        public Long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("id", this.id).append("name", this.name).toString();
        }
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
}
