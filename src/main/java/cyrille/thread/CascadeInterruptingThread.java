/*
 * Created on May 6, 2004
 */
package cyrille.thread;

import java.lang.ref.WeakReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class CascadeInterruptingThread extends Thread {

    private final static Log log = LogFactory.getLog(CascadeInterruptingThread.class);

    /**
     * use a {@link WeakReference}instead of a strong reference to garbage as soon as possible due
     * to Thread#exit() that manually dereference the runnable
     */
    private WeakReference m_targetReference;

    /**
     * 
     */
    public CascadeInterruptingThread() {
        super();
    }

    /**
     * @param target
     */
    public CascadeInterruptingThread(Runnable target) {
        super(target);
        this.m_targetReference = new WeakReference(target);
    }

    /**
     * @param name
     */
    public CascadeInterruptingThread(String name) {
        super(name);
    }

    /**
     * @param group
     * @param target
     */
    public CascadeInterruptingThread(ThreadGroup group, Runnable target) {
        super(group, target);
        this.m_targetReference = new WeakReference(target);
    }

    /**
     * @param target
     * @param name
     */
    public CascadeInterruptingThread(Runnable target, String name) {
        super(target, name);
        this.m_targetReference = new WeakReference(target);
    }

    /**
     * @param group
     * @param name
     */
    public CascadeInterruptingThread(ThreadGroup group, String name) {
        super(group, name);
    }

    /**
     * @param group
     * @param target
     * @param name
     */
    public CascadeInterruptingThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        this.m_targetReference = new WeakReference(target);
    }

    /**
     * @param group
     * @param target
     * @param name
     * @param stackSize
     */
    public CascadeInterruptingThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        this.m_targetReference = new WeakReference(target);
    }

    /**
     * @see java.lang.Thread#interrupt()
     */
    @Override
    public void interrupt() {
        super.interrupt();
        Runnable target = (Runnable) this.m_targetReference.get();
        if (target instanceof Interruptible) {
            Interruptible interruptible = (Interruptible) target;
            interruptible.interrupt();
        }
    }
}