/*
 * Created on May 6, 2006
 */
package cyrille.springframework.jmx;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.springframework.jmx.export.naming.KeyNamingStrategy;

public class EnrichedKeyNamingStrategy extends KeyNamingStrategy {

    public EnrichedKeyNamingStrategy() {
        super();
    }

    @Override
    public ObjectName getObjectName(Object managedBean, String beanKey) throws MalformedObjectNameException {
        String enrichedBeanKey = beanKey + ",cell=myCell," + "node=myNode," + "server=myServer,"
                + "enterpriseApplication=myEnterpriseApplication," + "webApplication=myWebApplication";
        return super.getObjectName(managedBean, enrichedBeanKey);
    }
}
