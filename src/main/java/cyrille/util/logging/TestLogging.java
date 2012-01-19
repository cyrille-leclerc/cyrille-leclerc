package cyrille.util.logging;

import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class TestLogging {
    private static Logger logger = Logger.getLogger(TestLogging.class.getName());

    public static void main(String[] args) {
        try {
            InputStream in = TestLogging.class.getResourceAsStream("logging.properties");
            LogManager logManager = LogManager.getLogManager();

            logManager.readConfiguration(in);
            TestLogging testLogging = new TestLogging();
            testLogging.testLogging();
            testLogging.testLogging("toto", "titi", "tutu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testLogging() throws Exception {
        logger.entering(this.getClass().getName(), "testLogging");
        logger.fine("this is my fine message");
        logger.throwing(TestLogging.class.getName(), "testLogging", new Exception("an Exception", new Exception("a cause")));
        logger.exiting(TestLogging.class.getName(), "testLogging");
    }

    public String testLogging(String a, String b, String c) throws Exception {
        logger.entering(this.getClass().getName(), "testLogging", new Object[] { a, b, c });
        String result = "zeResult";
        logger.fine("this is my fine message");
        logger.throwing(TestLogging.class.getName(), "testLogging", new Exception("an Exception", new Exception("a cause")));

        logger.exiting(TestLogging.class.getName(), "testLogging", result);
        return result;
    }
    
    /**
     * {@link Logger#isLoggable(java.util.logging.Level)}
     * 
     * {@link Logger#info(String)}
     * @throws Exception
     */
    public void testFlorilege() throws Exception {
        Logger logger = Logger.getLogger(TestLogging.class.getName());
                
    }
    
}
