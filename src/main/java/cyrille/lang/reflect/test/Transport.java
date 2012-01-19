/*
 * Created on Jul 1, 2005
 */
package cyrille.lang.reflect.test;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Transport {

    private String m_host;

    private String m_id;

    /**
     * @param host
     * @param id
     */
    public Transport(String id, String host) {
        super();
        this.m_host = host;
        this.m_id = id;
    }

    /**
     * @return Returns the host.
     */
    public String getHost() {
        return this.m_host;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return this.m_id;
    }

    /**
     * @param host
     *            The host to set.
     */
    public void setHost(String host) {
        this.m_host = host;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.m_id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
