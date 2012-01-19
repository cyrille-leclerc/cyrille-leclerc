/*
 * Created on May 5, 2004
 */
package cyrille.thread;

import org.apache.commons.lang.IllegalClassException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class TimedRunnableSubOptimal implements Runnable, Comparable {

    private final static Log log = LogFactory.getLog(TimedRunnableSubOptimal.class);

    private long m_millis;

    private Runnable m_runnable;

    private long m_startTime;

    private boolean m_timedOut;

    /**
     * 
     */
    public TimedRunnableSubOptimal(Runnable runnable, long millis) {
        super();
        this.m_runnable = runnable;
        this.m_millis = millis;
    }

    /**
     * @return Returns the timedOut.
     */
    public boolean isTimedOut() {
        return this.m_timedOut;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
        this.m_startTime = System.currentTimeMillis();
        boolean traceEnabled = log.isTraceEnabled();
        if (traceEnabled) {
            log.trace("> run " + this.toString());
        }
        Thread executionThread = new Thread(this.m_runnable, Thread.currentThread().getName() + "-execution");

        executionThread.start();

        try {
            executionThread.join(this.m_millis);
        } catch (InterruptedException e) {
            log.error("Interruption in " + this.toString(), e);
        } finally {
            if (executionThread.isAlive()) {
                this.m_timedOut = true;
                stopThreadSilently(executionThread);
            } else {
                this.m_timedOut = false;
            }
        }
        if (traceEnabled) {
            log.trace("< run ");
        }
    }

    private void stopThreadSilently(Thread thread) {
        boolean traceEnabled = log.isTraceEnabled();
        if (traceEnabled) {
            log.trace("> stopThreadSilently[thread=" + thread + "]");
        }
        // cleanup the new thread using an appropriate means
        // eg. stop(), interrupt() or whatever
        log.trace("Interrupt thread ");
        thread.interrupt();
        try {
            thread.join(500);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (thread.isAlive()) {
                log.trace("Destroy thread ");
                thread.destroy();
            }
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("startTime", this.m_startTime).append("millis", this.m_millis).append("runnable", this.m_runnable)
                .toString();
    }

    /**
     * <p>
     * Compares the underlying <code>TimedRunnable#m_runnable<code> with :<br>
     * - if the other object is a <code>TimedRunnable</code>, the other <code>TimedRunnable#m_runnable<code>
     * - otherwise with the other object
     * </p>
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        if (!(this.m_runnable instanceof Comparable)) {
            throw new IllegalClassException(Comparable.class, this.m_runnable.getClass());
        }
        Comparable comparable = (Comparable) this.m_runnable;
        Comparable otherComparable;
        if (o instanceof TimedRunnableSubOptimal) {
            TimedRunnableSubOptimal otherTimedRunnable = (TimedRunnableSubOptimal) o;
            if (!(otherTimedRunnable.m_runnable instanceof Comparable)) {
                throw new IllegalClassException(Comparable.class, otherTimedRunnable.m_runnable.getClass());
            }
            otherComparable = (Comparable) otherTimedRunnable.m_runnable;
        } else if (!(o instanceof Comparable)) {
            throw new IllegalClassException(Comparable.class, o.getClass());
        } else {
            otherComparable = (Comparable) o;
        }

        return comparable.compareTo(otherComparable);
    }
}