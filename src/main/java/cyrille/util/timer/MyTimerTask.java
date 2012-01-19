/*
 * Created on Aug 22, 2004
 */
package cyrille.util.timer;

import java.util.Random;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class MyTimerTask extends TimerTask {

    private final static Log log = LogFactory.getLog(MyTimerTask.class);

    /**
     * 
     */
    public MyTimerTask() {
        super();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        log.trace("> run");
        int sleepDuration = new Random().nextInt(100);
        try {
            Thread.sleep(sleepDuration);
        } catch (InterruptedException e) {
            log.error(e, e);
        }
        log.trace("< run");
    }

}