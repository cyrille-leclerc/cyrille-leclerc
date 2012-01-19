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
package cyrille.hibernate.temporal;

import java.sql.Timestamp;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import cyrille.hibernate.HsqldbAnnotationConfiguration;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TemporalTest extends TestCase {

    protected SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AnnotationConfiguration configuration = new HsqldbAnnotationConfiguration();
        configuration.configure();
        configuration.addAnnotatedClass(Contrat.class).addAnnotatedClass(Objet.class).addAnnotatedClass(
                ContratObjet.class);
        this.sessionFactory = configuration.buildSessionFactory();
    }

    public void test() throws Exception {
        {
            // CREATE CONTRAT
            this.sessionFactory.getCurrentSession().beginTransaction();
            Contrat contrat = new Contrat(1, "assurance chantier Viaduc de Millau - v1");
            this.sessionFactory.getCurrentSession().save(contrat);
            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }

        {
            // ADD OBJET
            this.sessionFactory.getCurrentSession().beginTransaction();
            Contrat contratv2 = new Contrat(1, "assurance chantier Viaduc de Millau - v2");
            Objet objet = new Objet(1, "grue xyz - v1");
            ContratObjet contratObjet = new ContratObjet(contratv2, objet, new Timestamp(System.currentTimeMillis()),
                    ContratObjet.CauseEffictivite.MODIFICATION_OBJET_FILS);

            this.sessionFactory.getCurrentSession().save(contratv2);
            this.sessionFactory.getCurrentSession().save(objet);
            this.sessionFactory.getCurrentSession().save(contratObjet);
            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }
        {
            // UPDATE CONTRAT
            this.sessionFactory.getCurrentSession().beginTransaction();
            Contrat contratv3 = new Contrat(1, "assurance chantier Viaduc de Millau - v3");
            // ..

        }

    }
}
