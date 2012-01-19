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
package cyrille.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class LdapTest {
    @Test
    public void test() throws Exception {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        DirContext dirContext = new InitialDirContext(env);
        
        Attributes attributes = dirContext.getAttributes("uid=aeinstein,ou=Users,dc=example,dc=com");
        for (NamingEnumeration<Attribute> attributesEnumeration = (NamingEnumeration<Attribute>)attributes.getAll(); attributesEnumeration
            .hasMore();) {
            Attribute attribute = attributesEnumeration.next();
            System.out.print(attribute.getID() + "=");
            
            for (NamingEnumeration<?> attributeValues = attribute.getAll(); attributeValues.hasMore();) {
                Object value = attributeValues.next();
                if (value instanceof byte[] && "userpassword".equals(attribute.getID())) {
                    byte[] bytes = (byte[])value;
                    System.out.print(new String(bytes) + ", ");                    
                } else {
                    System.out.print(value + ", ");
                }
            }
            System.out.println();
        }
    }
}
