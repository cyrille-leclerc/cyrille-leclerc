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

import cyrille.hibernate.Db2AnnotationConfiguration;
import cyrille.hibernate.HsqldbAnnotationConfiguration;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class EvenementTest extends TestCase {

    protected SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AnnotationConfiguration configuration = new Db2AnnotationConfiguration();
        configuration.configure();
        configuration.addAnnotatedClass(Sinistre.class).addAnnotatedClass(SinistreVersion.class);
        configuration.addAnnotatedClass(Evenement.class).addAnnotatedClass(EvenementVersion.class);
        configuration.addAnnotatedClass(Dommage.class).addAnnotatedClass(DommageCorporel.class);
        configuration.addAnnotatedClass(DommageVersion.class).addAnnotatedClass(DommageCorporelVersion.class);
        this.sessionFactory = configuration.buildSessionFactory();
    }

    public void test() throws Exception {
        Long evenementId;
        {
            // CREATE SINISTRE & EVENEMENT
            this.sessionFactory.getCurrentSession().beginTransaction();

            Sinistre sinistre = new Sinistre("num-sinistre-1", "num-declaration-1");
            sinistre.setDescription("Mon premier sinistre - v1");
            sinistre.setCodeAssureur("SMABTP");
            
            Evenement evenement = new Evenement("num-fonctionnel-1");
            evenement.setDescription("Mon premier evenement - v1");
            evenement.setLabelEvenement("Lavel evenement - v1");
            
            // link
            evenement.setSinistre(sinistre);
            sinistre.getEvenements().add(evenement);
            
            this.sessionFactory.getCurrentSession().save(sinistre);
            this.sessionFactory.getCurrentSession().getTransaction().commit();
            evenementId = evenement.getId();

            System.out.println("CREATE EVENEMENT : " + evenement);
        }

        {
            // UPDATE EVENEMENT - un seul champ versione
            this.sessionFactory.getCurrentSession().beginTransaction();
            Evenement evenement = (Evenement) this.sessionFactory.getCurrentSession().get(Evenement.class, evenementId);

            evenement.setDescription("Mon premier evenement - v2");

            this.sessionFactory.getCurrentSession().save(evenement);
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            System.out.println("UPDATE EVENEMENT : " + evenement);
        }
        {
            // UPDATE EVENEMENT - deux champs versiones
            this.sessionFactory.getCurrentSession().beginTransaction();
            Evenement evenement = (Evenement) this.sessionFactory.getCurrentSession().get(Evenement.class, evenementId);

            evenement.setDescription("Mon premier evenement - v3");
            evenement.setLabelEvenement("Lavel evenement - v3");

            this.sessionFactory.getCurrentSession().save(evenement);
            // this.sessionFactory.getCurrentSession().save(evenement.getWorkingCopy());
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            System.out.println("UPDATE EVENEMENT 2 : " + evenement);
        }
        {
            // DISPLAY EVENEMENT
            this.sessionFactory.getCurrentSession().beginTransaction();
            Evenement evenement = (Evenement) this.sessionFactory.getCurrentSession().get(Evenement.class, evenementId);
            System.out.println("DISPLAY EVENEMENT : " + evenement);
        }

    }
}
