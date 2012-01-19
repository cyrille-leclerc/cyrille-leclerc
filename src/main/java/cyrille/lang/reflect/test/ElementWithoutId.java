/*
 * Created on Jul 1, 2005
 */
package cyrille.lang.reflect.test;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ElementWithoutId {

    private String m_theAttribute;

    /**
     * @param attribute
     */
    public ElementWithoutId(String attribute) {
        super();
        this.m_theAttribute = attribute;
    }

    /**
     * @return Returns the theAttribute.
     */
    public String getTheAttribute() {
        return this.m_theAttribute;
    }

    /**
     * @param theAttribute
     *            The theAttribute to set.
     */
    public void setTheAttribute(String theAttribute) {
        this.m_theAttribute = theAttribute;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
