/*
 * Created on May 5, 2004
 */
package cyrille.thread.test;

import java.util.Timer;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cyrille.thread.CascadeInterruptingThread;
import cyrille.thread.TimedRunnable;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class InterruptibleRunnableTest extends TestCase {

    private final static Log log = LogFactory.getLog(InterruptibleRunnableTest.class);

    public static void main(String[] args) {
        junit.textui.TestRunner.run(InterruptibleRunnableTest.class);
    }

    private Timer m_timer;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.m_timer = new Timer();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        this.m_timer.cancel();
    }

    public void testDoRun() throws InterruptedException {
        log.debug("> testDoRun");
        final int TAKS_DURATION = 200;
        final int TIME_OUT = 500;
        Runnable runnable = new MockInterruptibleRunnable("test", TAKS_DURATION);
        TimedRunnable timedRunnable = new TimedRunnable(runnable, TIME_OUT, this.m_timer);
        Thread thread = new CascadeInterruptingThread(timedRunnable);
        thread.start();
        Thread.sleep(3 * TIME_OUT);
        assertFalse("task DID time out", timedRunnable.isTimedOut());
        log.debug("< testDoRun");
    }

    public void testTimeOut() throws InterruptedException {
        log.debug("> testTimeOut");
        final int TAKS_DURATION = 1000;
        final int TIME_OUT = 500;

        Runnable runnable = new MockInterruptibleRunnable("test", TAKS_DURATION);
        TimedRunnable timedRunnable = new TimedRunnable(runnable, TIME_OUT, this.m_timer);
        Thread thread = new CascadeInterruptingThread(timedRunnable);
        thread.start();
        Thread.sleep(3 * TIME_OUT);
        assertTrue("task did NOT time out", timedRunnable.isTimedOut());
        log.debug("< testTimeOut");
    }
}