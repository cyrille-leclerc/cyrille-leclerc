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
package cyrille.hibernate.temporal.sinistre;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import cyrille.hibernate.HsqldbAnnotationConfiguration;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class SinistreTest extends TestCase {

    protected SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AnnotationConfiguration configuration = new HsqldbAnnotationConfiguration();
        configuration.configure();
        configuration.addAnnotatedClass(Sinistre.class).addAnnotatedClass(SinistreVersion.class);
        configuration.addAnnotatedClass(Evenement.class).addAnnotatedClass(EvenementVersion.class);
        this.sessionFactory = configuration.buildSessionFactory();
    }

    public void test() throws Exception {
        Long sinistreId;
        {
            // CREATE SINISTRE
            this.sessionFactory.getCurrentSession().beginTransaction();

            Sinistre sinistre = new Sinistre("num-sinistre-1", "num-declaration-1");
            sinistre.setDescription("Mon premier sinistre - v1");
            sinistre.setCodeAssureur("SMABTP");

            this.sessionFactory.getCurrentSession().save(sinistre);
            this.sessionFactory.getCurrentSession().getTransaction().commit();
            sinistreId = sinistre.getId();

            System.out.println("CREATE SINISTRE : " + sinistre);
        }

        {
            // UPDATE SINISTRE - un seul champ versione
            this.sessionFactory.getCurrentSession().beginTransaction();
            Sinistre sinistre = (Sinistre) this.sessionFactory.getCurrentSession().get(Sinistre.class, sinistreId);

            sinistre.setDescription("Mon premier sinistre - v2");

            this.sessionFactory.getCurrentSession().save(sinistre);
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            System.out.println("UPDATE SINISTRE : " + sinistre);
        }
        {
            // UPDATE SINISTRE - deux champs versiones
            this.sessionFactory.getCurrentSession().beginTransaction();
            Sinistre sinistre = (Sinistre) this.sessionFactory.getCurrentSession().get(Sinistre.class, sinistreId);

            sinistre.setDescription("Mon premier sinistre - v3");
            sinistre.setCodeAssureur("PROTEC");

            this.sessionFactory.getCurrentSession().save(sinistre);
            // this.sessionFactory.getCurrentSession().save(sinistre.getWorkingCopy());
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            System.out.println("UPDATE SINISTRE 2 : " + sinistre);
        }
        {
            // DISPLAY SINISTRE
            this.sessionFactory.getCurrentSession().beginTransaction();
            Sinistre sinistre = (Sinistre) this.sessionFactory.getCurrentSession().get(Sinistre.class, sinistreId);
            System.out.println("DISPLAY SINISTRE : " + sinistre);
        }

    }
}
