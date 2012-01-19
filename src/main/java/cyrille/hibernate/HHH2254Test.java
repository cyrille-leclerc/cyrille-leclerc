/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cyrille.hibernate;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.Loader;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

/**
 * <p>
 * Test <a href="http://opensource.atlassian.com/projects/hibernate/browse/HHH-2254">HHH-2254 : A query validation bug produces
 * QueryException: "Expected positional parameter count: 1, actual parameters: [Parent@bec357b] [from Child this where this.id.parent = ?]"</a>
 * with a {@link Loader} to override the hql to load the persistent object.
 * </p>
 * <p>
 * Workaround : replace the positional parameter ("?") by a named parameter (":blahblah").
 * </p>
 * 
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class HHH2254Test extends TestCase {

    @Entity(name = "PersistentObject")
    @Loader(namedQuery = "load.PersistentObject")
    @NamedQueries({@NamedQuery(name = "load.PersistentObject", query = "SELECT p FROM PersistentObject p WHERE p.compositeId = :pk")})
    public static class PersistentObject {

        @EmbeddedId
        CompositeId compositeId;

        @Basic
        String name;

        public CompositeId getCompositeId() {
            return compositeId;
        }

        public void setCompositeId(CompositeId compositeId) {
            this.compositeId = compositeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Embeddable
    public static class CompositeId implements Serializable {

        private static final long serialVersionUID = 1L;

        Long id1;

        Long id2;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
            result = prime * result + ((id2 == null) ? 0 : id2.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final CompositeId other = (CompositeId) obj;
            if (id1 == null) {
                if (other.id1 != null)
                    return false;
            } else if (!id1.equals(other.id1))
                return false;
            if (id2 == null) {
                if (other.id2 != null)
                    return false;
            } else if (!id2.equals(other.id2))
                return false;
            return true;
        }

        public Long getId1() {
            return id1;
        }

        public void setId1(Long id1) {
            this.id1 = id1;
        }

        public Long getId2() {
            return id2;
        }

        public void setId2(Long id2) {
            this.id2 = id2;
        }
    }

    public void test() throws Exception {
        AnnotationConfiguration annotationConfiguration = new HsqldbAnnotationConfiguration();
        annotationConfiguration.addAnnotatedClass(PersistentObject.class);
        annotationConfiguration.configure();

        SessionFactory sessionFactory = annotationConfiguration.buildSessionFactory();

        {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            CompositeId compositeId = new CompositeId();
            compositeId.setId1(new Long(1));
            compositeId.setId2(new Long(2));

            PersistentObject persistentObject = new PersistentObject();
            persistentObject.setCompositeId(compositeId);
            persistentObject.setName("John Doe");

            session.save(persistentObject);

            session.getTransaction().commit();
        }

        {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            CompositeId compositeId = new CompositeId();
            compositeId.setId1(new Long(1));
            compositeId.setId2(new Long(2));

            PersistentObject persistentObject = (PersistentObject) session.get(PersistentObject.class, compositeId);

            assertEquals("John Doe", persistentObject.getName());
            session.getTransaction().commit();
        }

    }
}
