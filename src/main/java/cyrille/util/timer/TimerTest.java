/*
 * Created on Aug 22, 2004
 */
package cyrille.util.timer;

import java.util.Timer;
import java.util.TimerTask;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TimerTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TimerTest.class);
    }

    public void test() throws InterruptedException {
        Timer timer = new Timer();
        TimerTask task = new MyTimerTask();
        timer.scheduleAtFixedRate(task, 0, 5 * DateUtils.MILLIS_IN_SECOND);

        Thread.sleep(DateUtils.MILLIS_IN_MINUTE);
    }
}
