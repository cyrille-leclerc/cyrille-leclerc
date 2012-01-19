/*
 * Created on Oct 27, 2005
 */
package cyrille.lang;

public class MyStringUtils {

    private MyStringUtils() {
        super();
    }

    /**
     * <p>
     * Capitalize String. Already Capitalized chars are prefixed by '_' (underscore)
     * </p>
     * <p>
     * Sample : "testOne" returns "TEST_ONE"
     * </p>
     * 
     * @param in
     * @return capitalized string
     */
    public static String capitalize(String in) {
        StringBuffer sb = new StringBuffer(in.length());
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_');
                sb.append(c);
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();

    }
}
