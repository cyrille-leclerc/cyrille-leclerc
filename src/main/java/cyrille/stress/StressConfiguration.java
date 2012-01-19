/*
 * Created on 6 avr. 2005
 *
 */
package cyrille.stress;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Cyrille Le Clerc
 */
public class StressConfiguration {

    private int numberOfInvocationsPerThread;

    private int numberOfThreads;

    private long thinkTimeInMillis;

    public StressConfiguration(int numberOfThreads, int numberOfInvocationsPerThread, int thinkTimeInMillis) {
        this.numberOfThreads = numberOfThreads;
        this.numberOfInvocationsPerThread = numberOfInvocationsPerThread;
        this.thinkTimeInMillis = thinkTimeInMillis;
    }

    /**
     * @return Returns the numberOfInvocationsPerThread.
     */
    public int getNumberOfInvocationsPerThread() {
        return this.numberOfInvocationsPerThread;
    }

    /**
     * @return Returns the numberOfThreads.
     */
    public int getNumberOfThreads() {
        return this.numberOfThreads;
    }

    public long getThinkTimeInMillis() {
        return this.thinkTimeInMillis;
    }

    /**
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("numberOfThreads", this.numberOfThreads).append("numberOfInvocationsPerThread",
                this.numberOfInvocationsPerThread).append("thinkTimeInMillis", this.thinkTimeInMillis).toString();
    }
}