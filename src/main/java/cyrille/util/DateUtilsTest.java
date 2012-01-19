package cyrille.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateUtilsTest extends TestCase {

    public void testTruncate() throws Exception {
        Date actual = DateUtils.truncate(new GregorianCalendar(2007, 02, 03, 17, 15).getTime(), Calendar.DAY_OF_MONTH);
        System.out.println(actual);
        System.out.println(DateFormatUtils.format(actual, "dd/MM/yyyy"));
    }
}
