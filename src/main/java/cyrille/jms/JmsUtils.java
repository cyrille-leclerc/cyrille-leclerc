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
package cyrille.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;

/**
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
public class JmsUtils {

    private JmsUtils() {
        super();
    }

    public static void dumpConnectionMetadata(Connection connection) throws JMSException {
        ConnectionMetaData connectionMetaData = connection.getMetaData();
        System.out.println("CONNECTION METADATA");
        System.out.println("\tJMSProviderName=" + connectionMetaData.getJMSProviderName());
        System.out.println("\tJMSMajorVersion=" + connectionMetaData.getJMSMajorVersion());
        System.out.println("\tJMSMinorVersion=" + connectionMetaData.getJMSMinorVersion());
        System.out.println("\tJMSVersion=" + connectionMetaData.getJMSVersion());
        System.out.println("\tProviderMajorVersion=" + connectionMetaData.getProviderMajorVersion());
        System.out.println("\tProviderMinorVersion=" + connectionMetaData.getProviderMinorVersion());
        System.out.println("\tProviderVersion=" + connectionMetaData.getProviderVersion());

        System.out.print("\tJMSXPropertyNames");
        Enumeration<String> jmsxPropertyNames = connectionMetaData.getJMSXPropertyNames();
        while (jmsxPropertyNames.hasMoreElements()) {
            String propertyName = jmsxPropertyNames.nextElement();
            System.out.print(propertyName + ", ");
        }
        System.out.println();
    }
}
