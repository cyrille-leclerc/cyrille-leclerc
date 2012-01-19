/*
 * Created on May 5, 2004
 */
package cyrille.thread.test;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cyrille.thread.Interruptible;

public class MockInterruptibleRunnable implements Runnable, Comparable, Interruptible {

    private final static Log log = LogFactory.getLog(MockInterruptibleRunnable.class);

    private long durationInMillis;

    private String name;

    public MockInterruptibleRunnable(String name, long durationInMillis) {
        super();
        this.name = name;
        this.durationInMillis = durationInMillis;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        MockInterruptibleRunnable other = (MockInterruptibleRunnable) o;
        return new CompareToBuilder().append(this.name, other.name).toComparison();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
        log.debug("> run " + this.toString());
        try {
            Thread.sleep(this.durationInMillis);
        } catch (InterruptedException e) {
            String msg = "InterruptedException executing : " + this.toString();
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
        log.debug("< run " + this.toString());
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", this.name).append("durationInMillis", this.durationInMillis).append("underlyingThread",
                Thread.currentThread().getName()).toString();
    }

    /**
     * @see cyrille.thread.Interruptible#interrupt()
     */
    public void interrupt() {
        log.debug("> interrupt");
        log.debug("< interrupt");
    }
}