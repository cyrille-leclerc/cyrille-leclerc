/*
 * Created on Sep 17, 2004
 */
package cyrille.rmi.iiop;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class Request implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String m_value;

    /**
     * 
     */
    public Request() {
        super();
    }

    /**
     * @param value
     */
    public Request(String value) {
        super();
        this.m_value = value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", this.m_value).toString();
    }

    /**
     * @return Returns the value.
     */
    public String getValue() {
        return this.m_value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(String value) {
        this.m_value = value;
    }
}