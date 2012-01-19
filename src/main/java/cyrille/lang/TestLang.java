package cyrille.lang;

import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import cyrille.sample.product.Color;
import cyrille.sample.product.Product;
import cyrille.sample.product.Sex;
import cyrille.sample.product.Size;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class TestLang extends TestCase {

    /**
     * 
     */
    public TestLang() {
        super();
    }

    /**
     * @param name
     */
    public TestLang(String name) {
        super(name);
    }

    public void testBoolean() {
        boolean myPrimitiveBoolean = true;
        Boolean myBoolean = Boolean.valueOf(myPrimitiveBoolean);
        myBoolean.booleanValue();
        int myPrimitiveInt = 1;
        Integer myInteger = new Integer(myPrimitiveInt);
        myInteger.intValue();
    }

    public void testClone() {
        HashMap map = new HashMap();
        Color blue = new Color("blue", null);
        map.put("blue-key", blue);
        System.out.println("map.get(\"blue-key\") = " + map.get("blue-key"));

        HashMap clonedMap = (HashMap) map.clone();
        clonedMap.put("black-key", new Color("black", null));

        System.out.println("map.get(\"black-key\") = " + map.get("black-key"));

        Color myClonedBlue = (Color) clonedMap.get("blue-key");
        myClonedBlue.setName("red");

        System.out.println("map.get(\"blue-key\") = " + map.get("blue-key"));
    }

    public void testInstanceof() throws Exception {
        Object objArrayOfStrings = new String[] { "toto", "titi" };
        Object objString = "tutu";

        if (objArrayOfStrings instanceof String[]) {
            String[] myArrayOfStrings = (String[]) objArrayOfStrings;
            System.out.println("Cast successfull");
        } else {
            System.out.println("Cast UNsuccessfull");
        }
        if (objArrayOfStrings instanceof String) {
            String myArrayOfStrings = (String) objArrayOfStrings;
            System.out.println("Cast UNsuccessfull");
        } else {
            System.out.println("Cast failure successfull");
        }
        if (objString instanceof String) {
            String string = (String) objString;
            System.out.println("Cast successfull");
        }
    }

    public void testExponent() throws Exception {
        int val;
        val = 10 ^ 3;
        val = (int) Math.pow(10, 3);
        System.out.println("val :" + val);
    }

    public void testDivisionPrecision() {
        long durationInMillis = 90 * 60 * 1000; // 90 minutes
        float hours = (float) durationInMillis / (60 * 60 * 1000);
        float expected = (float) 1.5;
        assertEquals(expected, hours, 0.1);
    }

    public void testToString() {
        Product product = new Product("prod1", new Color("blue", "dark blue"), new Size("9.5", Sex.MALE));
        System.out.println("DEFAULT_STYLE \t" + ToStringBuilder.reflectionToString(product, ToStringStyle.DEFAULT_STYLE).toString());

        System.out.println("SIMPLE_STYLE \t" + ToStringBuilder.reflectionToString(product, ToStringStyle.SIMPLE_STYLE).toString());
        System.out.println("MULTI_LINE_STYLE\t" + ToStringBuilder.reflectionToString(product, ToStringStyle.MULTI_LINE_STYLE).toString());
        System.out.println("NO_FIELD_NAMES_STYLE\t"
                + ToStringBuilder.reflectionToString(product, ToStringStyle.NO_FIELD_NAMES_STYLE).toString());
        ToStringBuilder.setDefaultStyle(new MyStyle());
        System.out.println("MyStyle\t" + ToStringBuilder.reflectionToString(product, new MyStyle()).toString());

    }

    private class MyStyle extends ToStringStyle {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public MyStyle() {
            super();
            this.setUseClassName(true);
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
            this.setUseFieldNames(true);
        }
    }
}
