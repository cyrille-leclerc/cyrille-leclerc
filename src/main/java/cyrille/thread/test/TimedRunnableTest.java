/*
 * Created on May 5, 2004
 */
package cyrille.thread.test;

import java.util.Timer;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cyrille.thread.TimedRunnable;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class TimedRunnableTest extends TestCase {

    private final static Log log = LogFactory.getLog(TimedRunnableTest.class);

    private Timer m_timer;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TimedRunnableTest.class);
    }

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

    public void testTimeOut() {
        log.debug("> testTimeOut");
        final int TAKS_DURATION = 1000;
        final int TIME_OUT = 500;

        Runnable runnable = new MockRunnable("test", TAKS_DURATION);
        TimedRunnable timedRunnable = new TimedRunnable(runnable, TIME_OUT, this.m_timer);
        timedRunnable.run();
        assertTrue("task did NOT time out", timedRunnable.isTimedOut());
        log.debug("< testTimeOut");
    }

    public void testDoRun() {
        log.debug("> testDoRun");
        final int TAKS_DURATION = 200;
        final int TIME_OUT = 500;
        Runnable runnable = new MockRunnable("test", TAKS_DURATION);
        TimedRunnable timedRunnable = new TimedRunnable(runnable, TIME_OUT, this.m_timer);
        timedRunnable.run();
        assertFalse("task DID time out", timedRunnable.isTimedOut());
        log.debug("< testDoRun");
    }
}