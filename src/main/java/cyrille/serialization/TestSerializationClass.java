/*
 * Created on Sep 23, 2004
 */
package cyrille.serialization;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * initial value : 6959510851294476589
 * </p>
 * <p>
 * change javadoc : 6959510851294476589
 * </p>
 * <p>
 * change order of private variables : 6959510851294476589
 * </p>
 * <p>
 * change order of public methods : 6959510851294476589
 * </p>
 * <p>
 * change internal code : 6959510851294476589
 * </p>
 * <p>
 * change internal code bis 6959510851294476589
 * </p>
 * <p>
 * overwrite toString 3641273734099362453
 * </p>
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class TestSerializationClass implements Serializable {

    /**
     * 
     */
    //private static final long serialVersionUID = 1L;

    private int value;

    private String name;

    /**
     * 
     */
    public TestSerializationClass() {
        super();
        for (int i = 0; i < 5; i++) {
            System.out.println("dummy code");
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this).append("name", this.name).toString();
//    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}