package cyrille.perf4j;

import java.net.URL;
import java.util.Random;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.log4j.extras.DOMConfigurator;
import org.junit.Test;
import org.perf4j.LoggingStopWatch;
import org.perf4j.log4j.Log4JStopWatch;

public class Perf4Test {
    
    final Logger logger = Logger.getLogger(Perf4Test.class);
    
    @Test
    public void test() throws Exception {
        
        Random random = new Random();
        
        URL log4jConfigurationUrl = getClass().getResource("log4j.xml");
        Assert.assertNotNull(log4jConfigurationUrl);
        DOMConfigurator.configure(log4jConfigurationUrl);

        for (int i = 0; i < 1000; i++) {
            // By default the Log4JStopWatch uses the Logger named org.perf4j.TimingLogger
            LoggingStopWatch stopWatch = new Log4JStopWatch();
            try {
                // for demo purposes just sleep
                Thread.sleep(random.nextInt(100));
                
                if (random.nextBoolean()) {
                    throw new RuntimeException();
                }
                logger.info("Normal logging messages only go to the console : success");
                stopWatch.stop("myaction.success");
            } catch (RuntimeException e) {
                logger.info("Normal logging messages only go to the console : failure");
                stopWatch.stop("myaction.failure");
            }
            
        }
        
    }
}
