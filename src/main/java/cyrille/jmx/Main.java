/*
 * Main.java - main class for Hello World example. Create the HelloWorld MBean,
 * register it, then wait forever (or until the program is interrupted).
 */

package cyrille.jmx;

import java.rmi.registry.LocateRegistry;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class Main {

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Get the Platform MBean Server
        MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();

        {
            // Construct the ObjectName for the MBean we will register
            ObjectName name = new ObjectName("cyrille.jmx:type=Hello");

            // Create the Hello World MBean
            Hello mbean = new Hello();

            // Register the Hello World MBean
            mbeanServer.registerMBean(mbean, name);
        }

        System.out.println("Start the RMI registry");
        LocateRegistry.createRegistry(9999);
        {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost:6666/jndi/rmi://localhost:9999/server");
            int port = url.getPort();
            System.out.println("rmi port " + port);
            JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbeanServer);
            System.out.println("Start the RMI connector server");
            cs.start();
            System.out.println("The RMI connector server successfully started");
        }

        // Wait forever
        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
