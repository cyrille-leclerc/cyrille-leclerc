package cyrille.xstream;

import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Document;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomReader;
import com.thoughtworks.xstream.io.xml.DomWriter;

public class DomWriterTest extends TestCase {
    public static class Person {

        String firstName;

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

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(this.firstName).toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Person other = (Person) obj;

            return new EqualsBuilder().append(this.firstName, other.firstName).isEquals();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("firstName", this.firstName).toString();
        }
    }

    public void testMarshal() throws Exception {
        List<Person> sourcePersons = Arrays.asList(new Person[] { new Person("peter"), new Person("John") });

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        HierarchicalStreamWriter writer = new DomWriter(document);

        XStream xstream = new XStream();
        xstream.marshal(sourcePersons, writer);

        HierarchicalStreamReader reader = new DomReader(document);
        List<Person> actualPersons = (List) xstream.unmarshal(reader);

        assertEquals(2, actualPersons.size());
        assertEquals(sourcePersons.get(0), actualPersons.get(0));
        assertEquals(sourcePersons.get(1), actualPersons.get(1));

    }
}
