/*
 * Created on Jul 17, 2003
 *
 */
package cyrille.sql;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.apache.commons.lang.exception.Nestable;
import org.apache.commons.lang.exception.NestableDelegate;

/**
 * Nestable SqlException
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class NestableSQLException extends SQLException implements Nestable {

    private static final long serialVersionUID = 1L;

    /**
     * The helper instance which contains much of the code which we delegate to.
     */
    protected NestableDelegate m_delegate = new NestableDelegate(this);

    /**
     * Holds the reference to the exception or error that caused this exception to be thrown.
     */
    private Throwable m_cause = null;

    /**
     * Constructs a new <code>NestableSQLException</code> without specified detail message.
     */
    public NestableSQLException() {
        super();
    }

    /**
     * Constructs a new <code>NestableSQLException</code> with specified detail message.
     * 
     * @param msg
     *            The error message.
     */
    public NestableSQLException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new <code>NestableSQLException</code> with specified nested
     * <code>Throwable</code>.
     * 
     * @param cause
     *            the exception or error that caused this exception to be thrown
     */
    public NestableSQLException(Throwable cause) {
        super();
        this.m_cause = cause;
    }

    /**
     * Constructs a new <code>NestableSQLException</code> with specified nested
     * <code>SQLException</code>.
     * 
     * @param cause
     *            the exception or error that caused this exception to be thrown
     */
    public NestableSQLException(SQLException cause) {
        super(cause.getMessage(), cause.getSQLState(), cause.getErrorCode());
        this.m_cause = cause;
        this.setNextException(cause);
    }

    /**
     * @param reason
     * @param SQLState
     */
    public NestableSQLException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    /**
     * @param reason
     * @param SQLState
     * @param vendorCode
     */
    public NestableSQLException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    /**
     * Constructs a new <code>NestableSQLException</code> with specified detail message and nested
     * <code>Throwable</code>.
     * 
     * @param msg
     *            the error message
     * @param cause
     *            the exception or error that caused this exception to be thrown
     */
    public NestableSQLException(String msg, Throwable cause) {
        super(msg);
        this.m_cause = cause;
    }

    @Override
    public Throwable getCause() {
        return this.m_cause;
    }

    /**
     * Returns the detail message string of this throwable. If it was created with a null message,
     * returns the following: (cause==null ? null : cause.toString()).
     */
    @Override
    public String getMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        } else if (this.m_cause != null) {
            return this.m_cause.toString();
        } else {
            return null;
        }
    }

    public String getMessage(int index) {
        if (index == 0) {
            return super.getMessage();
        } else {
            return this.m_delegate.getMessage(index);
        }
    }

    public String[] getMessages() {
        return this.m_delegate.getMessages();
    }

    public Throwable getThrowable(int index) {
        return this.m_delegate.getThrowable(index);
    }

    public int getThrowableCount() {
        return this.m_delegate.getThrowableCount();
    }

    public Throwable[] getThrowables() {
        return this.m_delegate.getThrowables();
    }

    public int indexOfThrowable(Class type) {
        return this.m_delegate.indexOfThrowable(type, 0);
    }

    public int indexOfThrowable(Class type, int fromIndex) {
        return this.m_delegate.indexOfThrowable(type, fromIndex);
    }

    @Override
    public void printStackTrace() {
        this.m_delegate.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream out) {
        this.m_delegate.printStackTrace(out);
    }

    @Override
    public void printStackTrace(PrintWriter out) {
        this.m_delegate.printStackTrace(out);
    }

    public final void printPartialStackTrace(PrintWriter out) {
        super.printStackTrace(out);
    }
}
