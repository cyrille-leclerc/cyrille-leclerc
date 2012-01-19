/*
 * Copyright 2002-2006 the original author or authors.
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
package cyrille.springframework.jmx;

import javax.management.remote.JMXConnectorServer;

import junit.framework.TestCase;

import org.springframework.jmx.support.JmxUtils;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class JmxUtilsTest extends TestCase {

    public void testIsMBean() throws Exception {
        {
            boolean actual = JmxUtils.isMBean(org.springframework.jmx.support.ConnectorServerFactoryBean.class);
            System.out.println("isMBean(ConnectorServerFactoryBean.class): " + actual);
        }

        // MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        // JMXServiceURL serviceURL = new
        // JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/server");
        // JMXConnectorServer connectorServer =
        // JMXConnectorServerFactory.newJMXConnectorServer(serviceURL, null,
        // mbeanServer);
        {
            boolean actual = JmxUtils.isMBean(JMXConnectorServer.class);
            System.out.println("isMBean(JMXConnectorServer.class): " + actual);
        }

    }
}
