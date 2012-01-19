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

import javax.persistence.EntityManagerFactory;

import org.hibernate.cache.NoCacheProvider;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.ejb.Ejb3Configuration;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class HsqldbEjb3Configuration extends Ejb3Configuration {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public EntityManagerFactory buildEntityManagerFactory() {
        this.setProperty(Environment.DRIVER, "org.hsqldb.jdbcDriver");
        this.setProperty(Environment.URL, "jdbc:hsqldb:mem:mydb");
        this.setProperty(Environment.USER, "sa");
        this.setProperty(Environment.DIALECT, HSQLDialect.class.getName());
        this.setProperty(Environment.CACHE_PROVIDER, NoCacheProvider.class.getName());
        this.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        this.setProperty(Environment.SHOW_SQL, Boolean.TRUE.toString());
        this.setProperty(Environment.USE_SQL_COMMENTS, Boolean.TRUE.toString());
        this.setProperty(Environment.HBM2DDL_AUTO, "create");

        return super.buildEntityManagerFactory();
    }
    
}
