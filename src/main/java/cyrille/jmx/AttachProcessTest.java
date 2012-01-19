/*
 * Copyright 2002-2008 the original author or authors.
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
package cyrille.jmx;

import java.io.File;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.junit.Test;

import com.sun.tools.attach.VirtualMachine;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class AttachProcessTest {
    @Test
    public void test() throws Exception {
        final String CONNECTOR_ADDRESS = "com.sun.management.jmxremote.localConnectorAddress";
        
        // attach to the target application
        VirtualMachine vm = VirtualMachine.attach("1028");
        
        // get the connector address
        String connectorAddress = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);
        
        // no connector address, so we start the JMX agent
        if (connectorAddress == null) {
            String agent = vm.getSystemProperties().getProperty("java.home") + File.separator + "lib" + File.separator
                           + "management-agent.jar";
            vm.loadAgent(agent);
            
            // agent is started, get the connector address
            connectorAddress = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);
        }
        
        // establish connection to connector server
        System.out.println("Connect to " + connectorAddress);
        JMXServiceURL url = new JMXServiceURL(connectorAddress);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        MBeanServerConnection mbeanServerConnection = connector.getMBeanServerConnection();
        Set<ObjectInstance> objectInstances = mbeanServerConnection.queryMBeans(new ObjectName("*:*"), null);
        
        for (ObjectInstance objectInstance : objectInstances) {
            ObjectName objectName = objectInstance.getObjectName();
            System.out.println(objectName);
        }
    }
}
