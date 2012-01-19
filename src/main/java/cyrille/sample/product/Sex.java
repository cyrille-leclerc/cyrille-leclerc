package cyrille.sample.product;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class Sex {

    public static Sex MALE = new Sex("male");

    public static Sex FEMALE = new Sex("female");

    private String name;

    public Sex() {
        super();
    }

    public Sex(String name) {
        this.name = name;
    }

    /**
     * Returns the name.
     * 
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
