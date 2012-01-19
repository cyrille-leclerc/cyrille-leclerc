/*
 * Created on Sep 14, 2003
 */
package cyrille.sample.product;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class SubProduct {

    private String name;

    public SubProduct() {
        super();
    }

    public SubProduct(String name) {
        this();
        this.name = name;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}