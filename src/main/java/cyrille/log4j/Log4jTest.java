/*
 * Created on Sep 7, 2004
 */
package cyrille.log4j;

import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.config.PropertyPrinter;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class Log4jTest {
    
    @Ignore
    @Test
    public void testPrintConfiguration() {
        PrintWriter printWriter = new PrintWriter(System.out);
        new PropertyPrinter(printWriter);
        printWriter.close();
    }
    
    @Test
    public void testLogError() throws Exception {
        Logger logger = Logger.getLogger(Log4jTest.class);
        logger.error("My err message");
        logger.trace("My trace message");
        logger.debug("My trace message");
        logger.warn("My trace message", new Exception("My Exception", new Exception("My Cause")));
    }
}
