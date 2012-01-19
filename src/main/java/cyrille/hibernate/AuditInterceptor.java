/*
 * Created on Feb 14, 2007
 */
package cyrille.hibernate;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;

import cyrille.sample.person.Person;

public class AuditInterceptor extends EmptyInterceptor implements Interceptor {

    private final static Logger logger = Logger.getLogger(AuditInterceptor.class);

    private static final long serialVersionUID = 1L;

    SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        logger.debug("onSave(entity=" + entity + ", id=" + id + ", state=" + Arrays.asList(state) + ", propertyNames="
                + Arrays.asList(propertyNames));
        if (entity instanceof Person) {
            Person person = (Person) entity;
            logChange(person);
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames,
            Type[] types) {
        logger.debug("onFlushDirty(entity=" + entity + ", id=" + id + ", currentState=" + Arrays.asList(currentState) + ", previousState="
                + Arrays.asList(previousState) + ", propertyNames=" + Arrays.asList(propertyNames));
        if (entity instanceof Person) {
            Person person = (Person) entity;
            logChange(person);
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    public void logChange(Person person) {
        AuditRecord auditRecord = new AuditRecord(person.toString());
        this.sessionFactory.getCurrentSession().save(auditRecord);
    }
}
