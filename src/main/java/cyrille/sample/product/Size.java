package cyrille.sample.product;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class Size {

    private String name;

    private Sex sex;

    public Size() {
        super();
    }

    public Size(String name, Sex sex) {
        this.name = name;
        this.sex = sex;
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

    public Sex getSex() {
        return this.sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
