/*
 * Created on May 5, 2004
 */
package cyrille.thread.test;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MockRunnable implements Runnable, Comparable {

    private final static Log log = LogFactory.getLog(MockRunnable.class);

    private long m_durationInMillis;

    private String m_name;

    public MockRunnable(String name, long durationInMillis) {
        super();
        this.m_name = name;
        this.m_durationInMillis = durationInMillis;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        MockRunnable other = (MockRunnable) o;
        return new CompareToBuilder().append(this.m_name, other.m_name).toComparison();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
        log.debug("> run " + this.toString());
        try {
            Thread.sleep(this.m_durationInMillis);
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
        return new ToStringBuilder(this).append("name", this.m_name).append("durationInMillis", this.m_durationInMillis).append("underlyingThread",
                Thread.currentThread().getName()).toString();
    }
}