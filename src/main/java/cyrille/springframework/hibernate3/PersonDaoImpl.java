/*
 * Created on May 7, 2006
 */
package cyrille.springframework.hibernate3;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import cyrille.sample.person.Person;

public class PersonDaoImpl implements PersonDao {
    
    protected SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public Person loadPersonById(final Long id) {
        return (Person)sessionFactory.getCurrentSession().get(Person.class, id);
    }
    
    @SuppressWarnings("unchecked")
    public List<Person> loadPersonsByName(final String name) {
        return sessionFactory.getCurrentSession().createCriteria(Person.class).add(Restrictions.like("lastName", name)).list();
    }
    
    public void saveOrUpdate(Person person) {
        sessionFactory.getCurrentSession().saveOrUpdate(person);
    }
}
