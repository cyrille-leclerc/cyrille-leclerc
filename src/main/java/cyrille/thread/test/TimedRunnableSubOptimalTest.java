/*
 * Created on May 5, 2004
 */
package cyrille.thread.test;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cyrille.thread.TimedRunnableSubOptimal;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class TimedRunnableSubOptimalTest extends TestCase {

    private final static Log log = LogFactory.getLog(TimedRunnableSubOptimalTest.class);

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TimedRunnableSubOptimalTest.class);
    }

    public void testTimeOut() {
        log.debug("> testTimeOut");
        final int TAKS_DURATION = 1000;
        final int TIME_OUT = 500;
        Runnable runnable = new MockRunnable("test", TAKS_DURATION);
        TimedRunnableSubOptimal timedRunnable = new TimedRunnableSubOptimal(runnable, TIME_OUT);
        timedRunnable.run();
        assertTrue("task did NOT time out", timedRunnable.isTimedOut());
        log.debug("< testTimeOut");
    }

    public void testDoRun() {
        log.debug("> testDoRun");
        final int TAKS_DURATION = 200;
        final int TIME_OUT = 500;
        Runnable runnable = new MockRunnable("test", TAKS_DURATION);
        TimedRunnableSubOptimal timedRunnable = new TimedRunnableSubOptimal(runnable, TIME_OUT);
        timedRunnable.run();
        assertFalse("task DID time out", timedRunnable.isTimedOut());
        log.debug("< testDoRun");
    }
}