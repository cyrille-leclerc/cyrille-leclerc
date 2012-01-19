/*
 * Created on May 5, 2004
 */
package cyrille.thread.test;

import java.text.DecimalFormat;

import junit.framework.TestCase;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cyrille.thread.TimedRunnableSubOptimal;
import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class PooledExecutorWithTimedRunnableSubOptimalTest extends TestCase {

    private final static Log log = LogFactory.getLog(PooledExecutorWithTimedRunnableSubOptimalTest.class);

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PooledExecutorWithTimedRunnableSubOptimalTest.class);
    }

    public void testExecute() throws InterruptedException {
        log.debug("> testExecute");
        int maxThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

        final int SHORT_TASK_DURATION = 300;
        final int LONG_TASK_DURATION = SHORT_TASK_DURATION * 3;

        int numberOfRunnables = 30;
        long[] durationInMillis = new long[numberOfRunnables];
        for (int i = 0; i < durationInMillis.length; i++) {
            boolean isShortDuration = RandomUtils.nextBoolean();
            if (isShortDuration) {
                durationInMillis[i] = SHORT_TASK_DURATION;
            } else {
                durationInMillis[i] = LONG_TASK_DURATION;
            }
        }

        for (int i = 0; i < numberOfRunnables; i++) {
            Runnable runnable = new MockRunnable("job-" + new DecimalFormat("00").format(i), durationInMillis[i]);
            Runnable timedRunnable = new TimedRunnableSubOptimal(runnable, durationInMillis[i]);
            log.debug("add " + timedRunnable);
            executor.execute(timedRunnable);
        }
        Thread.sleep(10 * 1000);
        log.debug("< testExecute");
    }
}