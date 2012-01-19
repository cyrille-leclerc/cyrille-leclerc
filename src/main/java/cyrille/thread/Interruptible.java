/*
 * Created on May 6, 2004
 */
package cyrille.thread;

/**
 * <p>
 * Designed to be associated with {@link cyrille.thread.CascadeInterruptingThread}to interrupt the
 * unberlying {@link java.lang.Runnable}
 * </p>
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public interface Interruptible {

    public void interrupt();
}