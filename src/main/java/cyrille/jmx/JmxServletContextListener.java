package cyrille.jmx;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.jmx.StatisticsService;

/**
 * Registers MBeans in Websphere's {@link MBeanServer} at web application startup
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
public class JmxServletContextListener implements ServletContextListener {

    private final static Logger logger = Logger.getLogger(JmxServletContextListener.class);

    protected MBeanServer mbeanServer;

    protected SessionFactory sessionFactory;

    protected List<ObjectName> registeredObjectNames;

    /**
     * Unregister MBeans
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        for (ObjectName objectName : this.registeredObjectNames) {
            try {
                this.mbeanServer.unregisterMBean(objectName);
                logger.debug("MBean successfully unregistered : " + objectName);
            } catch (Exception e) {
                logger.error("Exception unregistering " + objectName, e);
            }
        }
    }

    /**
     * Configures Hibernate and register its MBean
     * 
     * @param servletContextEvent
     * @param enterpriseApplicationName
     * @param webModuleName
     * @throws Exception
     */
    private void configureAndRegisterHibernate(ServletContextEvent servletContextEvent, String enterpriseApplicationName,
            String webModuleName) throws Exception {

        Configuration configuration = new AnnotationConfiguration();
        configuration.configure();
        this.sessionFactory = configuration.buildSessionFactory();

        // build the ObjectName you want
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("type", "statistics");
        hashtable.put("J2EEApplication", enterpriseApplicationName);
        hashtable.put("sessionFactory", webModuleName);
        ObjectName statisticsObjectName = new ObjectName("hibernate", hashtable);
        StatisticsService hibernateStatistics = new StatisticsService();
        hibernateStatistics.setSessionFactory(this.sessionFactory);

        ObjectInstance objectInstance = this.mbeanServer.registerMBean(hibernateStatistics, statisticsObjectName);
        this.registeredObjectNames.add(objectInstance.getObjectName());
        logger.debug("Hibernate StatisticsService MBean registered as " + statisticsObjectName);
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            /*
             * Use introspection instead of class to compile without Websphere jars <br/>
             * MBeanServer mbs =
             * com.ibm.websphere.management.AdminServiceFactory.getMBeanFactory().getMBeanServer();
             */
            Class<?> adminServiceFactoryClass = Class.forName("com.ibm.websphere.management.AdminServiceFactory");
            Method getMBeanFactoryMethod = adminServiceFactoryClass.getMethod("getMBeanFactory", new Class[] {});
            Object mbeanFactory = getMBeanFactoryMethod.invoke(null, new Object[] {});
            Method getMBeanServerMethod = mbeanFactory.getClass().getMethod("getMBeanServer", new Class[] {});

            this.mbeanServer = (MBeanServer) getMBeanServerMethod.invoke(mbeanFactory, new Object[] {});

            // TODO : find enterpriseAppName from Servletcontext
            String enterpriseApplicationName = "ivtApp";
            String webModuleName = servletContextEvent.getServletContext().getServletContextName();

            configureAndRegisterHibernate(servletContextEvent, enterpriseApplicationName, webModuleName);

        } catch (Exception e) {
            // log before rethrowing because servlet container may not display clear error message
            logger.fatal("Exception configuring components", e);
            throw new RuntimeException("Exception configuring components", e);
        }
    }
}
