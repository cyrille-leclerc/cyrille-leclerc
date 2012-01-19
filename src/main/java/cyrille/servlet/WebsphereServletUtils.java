/*
 * Created on Oct 22, 2005
 */
package cyrille.servlet;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebsphereServletUtils {

    public static class WebsphereContextInfo {

        private String cellName;

        private String nodeName;

        private String serverName;

        private String enterpriseApplicationName;

        private String webModuleName;

        /**
         * @param cellName
         * @param nodeName
         * @param serverName
         * @param enterpriseApplicationName
         * @param webModuleName
         */
        public WebsphereContextInfo(String cellName, String nodeName, String serverName, String enterpriseApplicationName,
                String webModuleName) {
            super();
            this.cellName = cellName;
            this.nodeName = nodeName;
            this.serverName = serverName;
            this.enterpriseApplicationName = enterpriseApplicationName;
            this.webModuleName = webModuleName;
        }

        public String getCellName() {
            return this.cellName;
        }

        public String getEnterpriseApplicationName() {
            return this.enterpriseApplicationName;
        }

        public String getNodeName() {
            return this.nodeName;
        }

        public String getWebModuleName() {
            return this.webModuleName;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("cellName", this.cellName).append("nodeName", this.nodeName).append("serverName", this.serverName)
                    .append("enterpriseApplicationName", this.enterpriseApplicationName).append("webModuleName", this.webModuleName).toString();
        }
    }

    private final static Log log = LogFactory.getLog(WebsphereServletUtils.class);

    private WebsphereServletUtils() {
        super();
    }

    /**
     * <p>
     * Returns the Websphere specific context information (cellName, nodeName, appServerName,
     * enterpriseApplicationName, webModuleName)
     * </p>
     * <p>
     * This methods relies on environment variables and attributes like
     * <code>servletContext.getAttribute("com.ibm.websphere.servlet.application.host")</code>,
     * <code>servletContext.getAttribute("javax.servlet.context.tempdir")</code> or
     * <code>servletContext.getResource("/")</code>
     * </p>
     * 
     * @param servletContext
     * @see ServletContext#getAttribute(java.lang.String)
     * @throws MalformedURLException
     */
    public static WebsphereContextInfo getWebsphereContextInfo(ServletContext servletContext) throws MalformedURLException {
        String fileSeparator = System.getProperty("file.separator");

        String servletContextName = servletContext.getServletContextName();
        /*
         * Expected value for
         * servletContext.getAttribute("com.ibm.websphere.servlet.application.host") : "dmgr"
         */
        String serverName = (String) servletContext.getAttribute("com.ibm.websphere.servlet.application.host");

        /*
         * Expected pattern for servletContext.getResource("/") :
         * "file:/opt/Websphere/AppServer/installedApps/MyCell/favicon.ear/favicon.war/"
         */
        URL resourceUrl = servletContext.getResource(fileSeparator);
        String[] resourceUrlAsArray = StringUtils.split(resourceUrl.getPath(), "/");
        String cellName;
        String enterpriseApplicationName;
        if (resourceUrlAsArray.length >= 3) {
            cellName = resourceUrlAsArray[resourceUrlAsArray.length - 3];
            enterpriseApplicationName = resourceUrlAsArray[resourceUrlAsArray.length - 2];
        } else {
            log.warn("Unexpected value for " + "servletContext.getResource('/'): " + resourceUrl.getPath());
            cellName = "#unknown#";
            enterpriseApplicationName = "#unknown#";
        }

        /*
         * Expected pattern for servletContext.getAttribute("javax.servlet.context.tempdir"):
         * "/opt/Websphere/AppServer/temp/MyNode/MyServer/favicon/favicon.war"
         */
        String tempDir = servletContext.getAttribute("javax.servlet.context.tempdir").toString();
        String[] tempDirAsArray = StringUtils.split(tempDir, fileSeparator);
        String nodeName;
        if (tempDirAsArray.length >= 4 && serverName.equals(tempDirAsArray[tempDirAsArray.length - 3])) {
            nodeName = tempDirAsArray[tempDirAsArray.length - 4];
            enterpriseApplicationName = tempDirAsArray[tempDirAsArray.length - 2];
        } else {
            log.warn("Unexpected value for " + "servletContext.getAttribute('javax.servlet.context.tempdir'): " + tempDir);
            log.warn("Unable to discover nodeName");
            nodeName = "#unknown#";
        }

        WebsphereContextInfo result = new WebsphereContextInfo(cellName, nodeName, serverName, enterpriseApplicationName,
                servletContextName);

        return result;
    }

}
