/*
 * Created on Mar 30, 2004
 */
package cyrille.lang.reflect;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TestReflectBean {
    private String fieldHiddenByAGetter;

    /**
     * 
     */
    public TestReflectBean() {
        super();
    }

    /**
     * 
     */
    public TestReflectBean(String fieldHiddenByAGetter) {
        super();
        this.fieldHiddenByAGetter = fieldHiddenByAGetter;
    }

    /**
     * @return
     */
    public String getFieldHiddenByAGetter() {
        return "#fake#" + this.fieldHiddenByAGetter + "#fake#";
    }

    /**
     * @param string
     */
    public void setFieldHiddenByAGetter(String string) {
        this.fieldHiddenByAGetter = string;
    }

}
