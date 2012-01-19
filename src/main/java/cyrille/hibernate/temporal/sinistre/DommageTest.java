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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import cyrille.hibernate.Db2AnnotationConfiguration;
import cyrille.hibernate.HsqldbAnnotationConfiguration;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class DommageTest extends TestCase {

    protected SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);

        AnnotationConfiguration configuration = new Db2AnnotationConfiguration();
        configuration.configure();
        configuration.addAnnotatedClass(Sinistre.class).addAnnotatedClass(SinistreVersion.class);
        configuration.addAnnotatedClass(Evenement.class).addAnnotatedClass(EvenementVersion.class);
        configuration.addAnnotatedClass(Dommage.class).addAnnotatedClass(DommageVersion.class);
        configuration.addAnnotatedClass(DommageCorporel.class).addAnnotatedClass(DommageCorporelVersion.class);
        this.sessionFactory = configuration.buildSessionFactory();
    }

    public void test() throws Exception {
        Long dommageCorporelId;
        {
            // CREATE SINISTRE & DOMMAGE
            this.sessionFactory.getCurrentSession().beginTransaction();

            Sinistre sinistre = new Sinistre("num-sinistre-1", "num-declaration-1");
            sinistre.setDescription("Mon premier sinistre - v1");
            sinistre.setCodeAssureur("SMABTP");

            DommageCorporel dommageCorporel = new DommageCorporel("code-type-dommage-1");
            dommageCorporel.setDescription("Mon premier dommageCorporel - v1");
            dommageCorporel.setLabelDommage("Label dommage - v1");
            dommageCorporel.setCodeNatureMaladie("Code Nature maladie - v1");

            // link
            dommageCorporel.setSinistre(sinistre);
            sinistre.getDommages().add(dommageCorporel);

            this.sessionFactory.getCurrentSession().save(sinistre);
            this.sessionFactory.getCurrentSession().getTransaction().commit();
            dommageCorporelId = dommageCorporel.getId();

            System.out.println("CREATE DOMMAGE : " + dommageCorporel);
        }

        {
            // UPDATE DOMMAGE - un seul champ versionne
            this.sessionFactory.getCurrentSession().beginTransaction();
            DommageCorporel dommageCorporel = (DommageCorporel) this.sessionFactory.getCurrentSession().get(
                    DommageCorporel.class, dommageCorporelId);

            dommageCorporel.setDescription("Mon premier dommageCorporel - v2");

            dommageCorporel.setCodeNatureMaladie("Code Nature maladie - v2");

            this.sessionFactory.getCurrentSession().save(dommageCorporel);
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            System.out.println("UPDATE DOMMAGE : " + dommageCorporel);

        }
        {
            // CHECK DOMMAGE
            this.sessionFactory.getCurrentSession().beginTransaction();
            DommageCorporel dommageCorporel = (DommageCorporel) this.sessionFactory.getCurrentSession().get(
                    DommageCorporel.class, dommageCorporelId);
            System.out.println("CHECK DOMMAGE : " + dommageCorporel);

            assertEquals("Mon premier dommageCorporel - v2", dommageCorporel.getDescription());
            assertEquals("Label dommage - v1", dommageCorporel.getLabelDommage());
            assertEquals("Code Nature maladie - v2", dommageCorporel.getCodeNatureMaladie());

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }

        {
            // UPDATE DOMMAGE - deux champs versionnes
            this.sessionFactory.getCurrentSession().beginTransaction();
            DommageCorporel dommageCorporel = (DommageCorporel) this.sessionFactory.getCurrentSession().get(
                    DommageCorporel.class, dommageCorporelId);

            dommageCorporel.setDescription("Mon premier dommageCorporel - v3");
            dommageCorporel.setLabelDommage("Label dommage - v3");
            dommageCorporel.setCodeNatureMaladie("Code Nature maladie - v3");

            this.sessionFactory.getCurrentSession().save(dommageCorporel);
            this.sessionFactory.getCurrentSession().getTransaction().commit();

            System.out.println("UPDATE DOMMAGE 2 : " + dommageCorporel);
        }
        {
            // CHECK DOMMAGE
            this.sessionFactory.getCurrentSession().beginTransaction();
            DommageCorporel dommageCorporel = (DommageCorporel) this.sessionFactory.getCurrentSession().get(
                    DommageCorporel.class, dommageCorporelId);
            System.out.println("CHECK DOMMAGE : " + dommageCorporel);

            assertEquals("Mon premier dommageCorporel - v3", dommageCorporel.getDescription());
            assertEquals("Label dommage - v3", dommageCorporel.getLabelDommage());
            assertEquals("Code Nature maladie - v3", dommageCorporel.getCodeNatureMaladie());

            this.sessionFactory.getCurrentSession().getTransaction().commit();
        }

    }
}
