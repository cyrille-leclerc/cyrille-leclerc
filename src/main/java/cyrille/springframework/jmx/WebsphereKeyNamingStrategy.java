/*
 * Created on May 6, 2006
 */
package cyrille.springframework.jmx;

import java.net.InetAddress;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.jmx.export.naming.KeyNamingStrategy;
import org.springframework.web.context.ServletContextAware;

public class WebsphereKeyNamingStrategy extends KeyNamingStrategy implements ServletContextAware {

    ServletContext servletContext;

    public WebsphereKeyNamingStrategy() {
        super();
    }

    @Override
    public ObjectName getObjectName(Object managedBean, String beanKey) throws MalformedObjectNameException {

        String cellName = "#cell#";
        String nodeName = "#node#";
        String serverName = "#server#";
        String enterpriseApplicationName = "#enterpriseApplication#";
        String applicationName = "#application#";
        String hostName = "#host#";
        try {
            // expected websphere6 base NameInNamespace :
            // "myCell/nodes/myNode/servers/myServer/"
            String nameInNamespace = new InitialContext().getNameInNamespace();
            String[] splittedNameInNamespace = StringUtils.split(nameInNamespace, "/");
            cellName = splittedNameInNamespace[0];
            nodeName = splittedNameInNamespace[2];
            serverName = splittedNameInNamespace[4];

        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            applicationName = this.servletContext.getServletContextName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String enrichedBeanKey = beanKey + ",cell=" + ObjectName.quote(cellName) + "," + "node=" + ObjectName.quote(nodeName) + ","
                + "server=" + ObjectName.quote(serverName) + "," + "enterpriseApplication=" + ObjectName.quote(enterpriseApplicationName)
                + "," + "webApplication=" + ObjectName.quote(applicationName) + ",host=" + ObjectName.quote(hostName);
        return super.getObjectName(managedBean, enrichedBeanKey);
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
