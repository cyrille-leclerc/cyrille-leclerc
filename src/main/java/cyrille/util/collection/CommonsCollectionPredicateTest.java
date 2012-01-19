package cyrille.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Sample of {@link List} and {@link Collection} filtering with {@link Predicate}
 * 
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class CommonsCollectionPredicateTest extends TestCase {

    public static class Person {

        long id;

        String name;

        public Person() {
            super();
        }

        public Person(long id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public void testListPersonsWithNameStartingBy() throws Exception {

        Collection<Person> persons = new ArrayList<Person>();
        persons.add(new Person(1, "A person"));
        persons.add(new Person(2, "B person"));
        persons.add(new Person(2, "A another person"));

        final String startsWith = "A";
        
        Predicate personStartsWithAPredicate = new Predicate() {

            public boolean evaluate(Object object) {
                Person person = (Person) object;
                return person.getName().startsWith(startsWith);
            }

        };

        Collection<?> personsStartintWithA = CollectionUtils.select(persons, personStartsWithAPredicate);
        assertEquals(2, personsStartintWithA.size());
    }
}
