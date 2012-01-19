/*
 * Created on May 5, 2004
 */
package cyrille.thread.test;

import java.text.DecimalFormat;
import java.util.Timer;

import junit.framework.TestCase;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cyrille.thread.TimedRunnable;
import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class PooledExecutorWithTimedRunnableTest extends TestCase {

    private final static Log log = LogFactory.getLog(PooledExecutorWithTimedRunnableTest.class);

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PooledExecutorWithTimedRunnableTest.class);
    }

    private ExecutorService m_executor;

    private Timer m_timer;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.m_timer = new Timer();
        int maxThreads = 5;
        this.m_executor = Executors.newFixedThreadPool(maxThreads);

    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Thread.sleep(2 * 1000);
        this.m_timer.cancel();
        this.m_executor.shutdown();
    }

    public void testExecute() throws InterruptedException {
        log.debug("> testExecute");

        final int SHORT_TASK_DURATION = 300;
        final int LONG_TASK_DURATION = SHORT_TASK_DURATION * 3;
        final int TIME_OUT_DURATION = SHORT_TASK_DURATION * 2;

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
            String name = "job-" + new DecimalFormat("00").format(i);
            if (durationInMillis[i] > TIME_OUT_DURATION) {
                name = "toLong-" + name;
            }
            Runnable runnable = new MockRunnable(name, durationInMillis[i]);
            Runnable timedRunnable2 = new TimedRunnable(runnable, TIME_OUT_DURATION, this.m_timer);
            log.debug("add " + timedRunnable2);
            this.m_executor.execute(timedRunnable2);
        }
        log.debug("< testExecute");
    }
}