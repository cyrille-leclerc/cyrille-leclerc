/*
 * Created on May 7, 2006
 */
package cyrille.springframework.hibernate3;

import java.util.List;

import cyrille.sample.person.Person;

public interface PersonDao {

    public List<Person> loadPersonsByName(String name);

    public Person loadPersonById(Long id);

    public void saveOrUpdate(Person person);
}