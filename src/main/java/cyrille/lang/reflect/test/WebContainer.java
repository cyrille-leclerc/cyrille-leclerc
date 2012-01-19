/*
 * Created on Jul 1, 2005
 */
package cyrille.lang.reflect.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class WebContainer {

    private ElementWithoutId m_elementWithoutId;

    private String m_id;

    private List m_transports = new ArrayList();

    /**
     * @param id
     */
    public WebContainer(String id) {
        super();
        this.m_id = id;
    }

    /**
     * @return Returns the elementWithoutId.
     */
    public ElementWithoutId getElementWithoutId() {
        return this.m_elementWithoutId;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return this.m_id;
    }

    /**
     * @return Returns the transports.
     */
    public List getTransports() {
        return this.m_transports;
    }

    /**
     * @param elementWithoutId
     *            The elementWithoutId to set.
     */
    public void setElementWithoutId(ElementWithoutId elementWithoutId) {
        this.m_elementWithoutId = elementWithoutId;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.m_id = id;
    }

    /**
     * @param transports
     *            The transports to set.
     */
    public void setTransports(List transports) {
        this.m_transports = transports;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
