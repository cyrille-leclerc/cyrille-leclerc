/*
 * Created on May 5, 2004
 */
package cyrille.thread;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.IllegalClassException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Wrapper to add a Timeout to the {@link java.lang.Runnable#run()}method of the underlying
 * <code>runnable</code>
 * </p>
 * <p>
 * Relies on
 * <ul>
 * <li>{@link Thread#interrupt()}when a timeout occurs</li>
 * <li>scheduling one {@link TimerTask}per instance of <code>timedRunnable</code> to the given
 * {@link java.util.Timer}</li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class TimedRunnable implements Runnable, Comparable, Interruptible {

    /**
     * <p>
     * Used to interrupt the thread if timeout occurs
     * </p>
     * 
     * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
     */
    public static class ThreadInterruptorTask extends TimerTask {

        private final static Log log = LogFactory.getLog(ThreadInterruptorTask.class);

        private Thread m_thread;

        /**
         * 
         */
        public ThreadInterruptorTask(Thread thread) {
            super();
            this.m_thread = thread;
        }

        /**
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            boolean traceEnabled = log.isTraceEnabled();
            if (traceEnabled) {
                log.trace("> run[" + this.toString() + "]");
            }
            // cleanup the new thread using an appropriate means
            // eg. stop(), interrupt() or whatever
            log.trace("Interrupt thread : " + this.m_thread.getName());
            this.m_thread.interrupt();

            // TODO Should we more violently kill the thread ?

            if (traceEnabled) {
                log.trace("< run");
            }
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return new ToStringBuilder(this).append("thread", this.m_thread.getName()).toString();
        }
    }

    private final static Log log = LogFactory.getLog(TimedRunnable.class);

    private long m_timeoutInMillis;

    private Runnable m_runnable;

    private boolean m_timedOut;

    private Timer m_timer;

    /**
     * @param runnable
     *            underlying <code>runnable</code> to execute
     * @param timeoutInMillis
     * @param timer
     *            Timer to handle timeouts with a {@link TimerTask}per TimedRunnabled instance
     * 
     */
    public TimedRunnable(Runnable runnable, long timeoutInMillis, Timer timer) {
        super();
        this.m_runnable = runnable;
        this.m_timeoutInMillis = timeoutInMillis;
        this.m_timer = timer;
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
        if (o instanceof TimedRunnable) {
            TimedRunnable otherTimedRunnable = (TimedRunnable) o;
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
        boolean traceEnabled = log.isTraceEnabled();
        if (traceEnabled) {
            log.trace("> run " + this.toString());
        }
        ThreadInterruptorTask task = new ThreadInterruptorTask(Thread.currentThread());
        this.m_timer.schedule(task, this.m_timeoutInMillis);
        boolean interrupted = false;
        try {
            this.m_runnable.run();
        } catch (RuntimeException e) {
            int i = ExceptionUtils.indexOfThrowable(e, InterruptedException.class);
            if (i == -1) {
                throw e;
            } else {
                interrupted = true;
                this.m_timedOut = true;
                if (traceEnabled) {
                    log.trace("Ignore InterruptedException " + this.toString());
                }
            }
        }
        if (!interrupted) {
            boolean cancelled = task.cancel();
            if (!cancelled) {
                log.error("Cancel failed, TimerTask is no longer scheduled : " + this.toString());
            }
        }
        if (traceEnabled) {
            log.trace("< run ");
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("timeoutInMillis", this.m_timeoutInMillis).append("runnable", this.m_runnable).append(
                "currentThread", Thread.currentThread().getName()).toString();
    }

    /**
     * @see cyrille.thread.Interruptible#interrupt()
     */
    public void interrupt() {
        boolean traceEnabled = log.isTraceEnabled();
        if (traceEnabled) {
            log.trace("> interrupt");
        }
        if (this.m_runnable instanceof Interruptible) {
            Interruptible interruptible = (Interruptible) this.m_runnable;
            interruptible.interrupt();
        }
        if (traceEnabled) {
            log.trace("< interrupt");
        }
    }
}