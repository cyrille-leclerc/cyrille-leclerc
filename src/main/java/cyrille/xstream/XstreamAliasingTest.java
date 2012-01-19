package cyrille.xstream;

import junit.framework.TestCase;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class XstreamAliasingTest extends TestCase {

    @XStreamAlias("ze-person")
    public static class Person {

        @XStreamAlias("ze-first-name")
        public String firstName;

        public Person() {
            super();
        }

        public Person(String firstName) {
            super();
            this.firstName = firstName;
        }

        public String getFirstName() {
            return this.firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }

    public void testAliasWithAnnotations() throws Exception {
        System.out.println("\r\ntestAliasWithAnnotations");

        XStream xstream = new XStream();

        Annotations.configureAliases(xstream, Person.class);

        Person person = new Person("Cyrille");
        xstream.toXML(person, System.out);
    }

    public void testAliasWithCode() throws Exception {
        System.out.println("\r\ntestAliasWithCode");

        XStream xstream = new XStream();

        xstream.aliasType("a-person", Person.class);
        xstream.aliasField("a-first-name", Person.class, "firstName");

        Person person = new Person("Cyrille");
        xstream.toXML(person, System.out);
    }

}
