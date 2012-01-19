/*
 * Created on Jul 20, 2005
 */
package cyrille.servlet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Chain of {@link ServletContextListener} defined in a configuration file
 * </p>
 * <p>
 * Configuration : relies on web.xml &lt; listener &gt; init param
 * {@link #CONFIGURATION_FILE_PARAMETER_NAME} (<code>configuration-file</code>)
 * </p>
 * <p>
 * Logging policy : we mostly log at debug level. Each {@link ServletContextListener} of the chain
 * is expected to log at info level if necessary
 * </p>
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class ServletContextListenerChain implements ServletContextListener {

    private static final String CONFIGURATION_FILE_PARAMETER_NAME = "ServletContextListenerChainConfigurationFile";

    private static final Log log = LogFactory.getLog(ServletContextListenerChain.class);

    /**
     * Chain of {@link ServletContextListener} to invoked
     */
    private ServletContextListener[] servletContextListeners;

    /**
     * <p>
     * Invoke {@link ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)} on
     * each element of the chain
     * </p>
     * <p>
     * If an exception occurs invoking a {@link ServletContextListener}, the message is logged and
     * next element of the chain is processed
     * </p>
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        for (int i = 0; i < this.servletContextListeners.length; i++) {
            ServletContextListener servletContextListener = this.servletContextListeners[i];
            try {
                servletContextListener.contextDestroyed(servletContextEvent);
                log.debug("ServletContextListener 'servletContextListener." + i + "'='" + servletContextListener + " unloaded");
            } catch (Throwable t) {
                log.error("Exception invoking contextDestroyed on 'servletContextListener." + i + "'='" + servletContextListener + "': "
                        + t.toString(), t);
            }
        }
    }

    /**
     * <p>
     * Load each {@link ServletContextListener} of the chain and invoke
     * {@link ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)} on each
     * one
     * </p>
     * <p>
     * If an exception occurs loading or invoking a {@link ServletContextListener}, the message is
     * logged and next element of the chain is processed
     * </p>
     * 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String configurationFile = servletContextEvent.getServletContext().getInitParameter(CONFIGURATION_FILE_PARAMETER_NAME);
        if (configurationFile == null) {
            log.error("Missing <context-param> '" + CONFIGURATION_FILE_PARAMETER_NAME + "' in web.xml");
            log.error("Abort ServletContextListenerChain initialization");
            return;
        }

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(configurationFile);
        if (in == null) {
            log.error("ServletContextListenerChain configuration file described in '" + configurationFile + "' not found");
            log.error("Abort ServletContextListenerChain initialization");
            return;
        }
        log.debug("Load ServletContextListeners from configuration file '" + configurationFile + "'");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (Exception e) {
            log.error("Exception loading ServletContextListenerChain configuration file '" + configurationFile + "' : " + e.toString(), e);
            log.error("Abort ServletContextListenerChain initialization");
            return;
        }

        List servletContextListenersList = new ArrayList();
        int i = 0;
        String servletContextListenerClassName;
        while ((servletContextListenerClassName = properties.getProperty(configurationFile, "servletContextListener." + i)) != null) {

            try {
                Class servletContextListenerClass = Class.forName(servletContextListenerClassName, true, Thread.currentThread()
                        .getContextClassLoader());
                ServletContextListener servletContextListener = (ServletContextListener) servletContextListenerClass.newInstance();
                log.debug("ServletContextListener 'servletContextListener." + i + "'='" + servletContextListener + " loaded");
                servletContextListenersList.add(servletContextListener);
            } catch (Throwable t) {
                log.error(
                        "Exception loading 'servletContextListener." + i + "'='" + servletContextListenerClassName + "': " + t.toString(),
                        t);
                log.error("WARNING 'servletContextListener." + i + "'='" + servletContextListenerClassName + "' will NOT be loaded");
            }

            i++;
        }

        this.servletContextListeners = (ServletContextListener[]) servletContextListenersList.toArray(new ServletContextListener[0]);

        for (i = 0; i < this.servletContextListeners.length; i++) {
            ServletContextListener servletContextListener = this.servletContextListeners[i];
            try {
                servletContextListener.contextInitialized(servletContextEvent);
            } catch (Throwable t) {
                log.error("Exception invoking contextInitialized on 'servletContextListener." + i + "'='" + servletContextListener + "': "
                        + t.toString(), t);
            }
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("servletContextListeners", this.servletContextListeners).toString();
    }
}
