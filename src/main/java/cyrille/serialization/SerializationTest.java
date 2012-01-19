/*
 * Created on Sep 23, 2004
 */
package cyrille.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class SerializationTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SerializationTest.class);
    }

    public void testSerializeObject() throws Exception {

        if(false) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/tmp/testSerializeObject.dat"));
            TestSerializationClass testSerializationClassInstance = new TestSerializationClass();
            testSerializationClassInstance.setName("my-name");
            testSerializationClassInstance.setValue(1);
            out.writeObject(testSerializationClassInstance);
            out.close();
        }
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("/tmp/testSerializeObject.dat"));
            TestSerializationClass testSerializationClassInstance = (TestSerializationClass) in.readObject();
            System.out.println("name: " + testSerializationClassInstance.getName());
            System.out.println("value: " + testSerializationClassInstance.getValue());
        }
    }

    public void testPrintSerialVersionUID() {
        ObjectStreamClass objectStreamClass = ObjectStreamClass.lookup(TestSerializationClass.class);
        long serialVersionUID = objectStreamClass.getSerialVersionUID();
        System.out.println(serialVersionUID);
    }
}