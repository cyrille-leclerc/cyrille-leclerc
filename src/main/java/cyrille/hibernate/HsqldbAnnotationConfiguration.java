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

import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.SettingsFactory;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class HsqldbAnnotationConfiguration extends AnnotationConfiguration {

    private static final long serialVersionUID = 1L;

    protected boolean showSql;

    /**
     * @see AnnotationConfiguration#AnnotationConfiguration()
     */
    public HsqldbAnnotationConfiguration() {
        super();
        this.showSql = true;
    }

    /**
     * @see AnnotationConfiguration#AnnotationConfiguration()
     */
    public HsqldbAnnotationConfiguration(boolean showSql) {
        super();
        this.showSql = showSql;
    }

    /**
     * @param sf
     * @see AnnotationConfiguration#AnnotationConfiguration(SettingsFactory)
     */
    public HsqldbAnnotationConfiguration(SettingsFactory sf) {
        super(sf);
        this.showSql = true;
    }

    /**
     * Configure in-memory HSQLDB as database.
     * 
     * @see org.hibernate.cfg.Configuration#configure()
     */
    @Override
    public AnnotationConfiguration configure() throws HibernateException {
        this.setProperty(Environment.DRIVER, "org.hsqldb.jdbcDriver");
        this.setProperty(Environment.URL, "jdbc:hsqldb:mem:mydb");
        this.setProperty(Environment.USER, "sa");
        this.setProperty(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
        this.setProperty(Environment.CACHE_PROVIDER, "org.hibernate.cache.NoCacheProvider");
        this.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        this.setProperty(Environment.SHOW_SQL, Boolean.toString(this.showSql));
        this.setProperty(Environment.USE_SQL_COMMENTS, Boolean.toString(this.showSql));
        this.setProperty(Environment.HBM2DDL_AUTO, "create");
        return this;
    }

}
