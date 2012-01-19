/*
 * Created on Oct 10, 2005
 */
package cyrille.management;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import junit.framework.TestCase;

public class MBeanServerTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(MBeanServerTest.class);
    }

    public void testManagementFactory() throws Exception {
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        dumpMBeanServer(mbeanServer);
    }

    public void dumpMBeanServer(MBeanServer mbeanServer) throws Exception {
        Set objectInstances = mbeanServer.queryMBeans(new ObjectName("*:*"), null);
        for (Iterator it = objectInstances.iterator(); it.hasNext();) {
            ObjectInstance objectInstance = (ObjectInstance) it.next();
            ObjectName objectName = objectInstance.getObjectName();
            System.out.println(objectName);
        }
    }

}
