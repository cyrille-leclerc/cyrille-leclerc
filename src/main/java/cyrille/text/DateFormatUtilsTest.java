/*
 * Created on Mar 30, 2004
 */
package cyrille.text;

import java.util.Calendar;
import java.util.Locale;

import junit.framework.TestCase;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class DateFormatUtilsTest extends TestCase {

    /**
     * Constructor for DateFormatUtilsTest.
     * 
     * @param name
     */
    public DateFormatUtilsTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(DateFormatUtilsTest.class);
    }

    public void test() {
        Calendar calendar = Calendar.getInstance();
        // Locale locale = Locale.ITALIAN;
        Locale locale = new Locale("FR");
        // DateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy", locale);
        String actual = DateFormatUtils.format(calendar.getTime(), "EEEE dd MMMM yyyy", locale);
        String actual2 = DateFormatUtils.format(new java.util.Date(), "EEEE dd MMMM yyyy", new java.util.Locale("FR"));

        System.out.println("actual " + actual);
        System.out.println("actual2 " + actual2);
        
        System.out.println("capitalize actual2 " + WordUtils.capitalize(actual2));
    }
}
