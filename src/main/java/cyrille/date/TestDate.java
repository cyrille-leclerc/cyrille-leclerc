package cyrille.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TestDate {

    public static void main(String[] args) {
        try {
            TestDate test = new TestDate();
            // test.testsWeeks();
            test.testWeeksSymphonie();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void testsWeeks() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK, 3);
        System.out.println("date = " + cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println("first day of week " + cal.getTime());
        DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        System.out.println(dfYYYYMMDD.format(cal.getTime()));
    }

    public void testCalendarMaximums() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        // System.out.println("least max weeks " +
        // calendar.getLeastMaximum(Calendar.WEEK_OF_YEAR) + " - greatest
        // maximum " + calendar.getGreatestM(Calendar.WEEK_OF_YEAR));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        for (int i = 1995; i < 2010; i++) {
            calendar.set(Calendar.YEAR, i);
            System.out.println("Date '" + format.format(calendar.getTime()) + "' max number of days in this February month\t'"
                    + calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "' \t- max weeks in year '"
                    + calendar.getActualMaximum(Calendar.WEEK_OF_YEAR) + "'");
        }
    }

    public void testEndOfWeek() {
        Calendar calendarEndingDate = Calendar.getInstance(new Locale("FR"));
        calendarEndingDate.setFirstDayOfWeek(Calendar.MONDAY);
        Date endingDate = new GregorianCalendar(2002, 2, 30).getTime();
        calendarEndingDate.setTime(endingDate);
        // calendarEndingDate.add(Calendar.WEEK_OF_YEAR, 1);
        // calendarEndingDate.add(Calendar.DAY_OF_YEAR, -1);
        calendarEndingDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        System.out.println("Last day for " + df.format(endingDate) + ", " + df.format(calendarEndingDate.getTime()));
    }

    public void testWeeksSymphonie() {
        int fromYear = 2003;
        int fromWeek = 23;
        int toYear = 2003;
        int toWeek = 25;

        DateFormat format = new SimpleDateFormat("E dd/MM/yyyy (ww)");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, fromYear);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.WEEK_OF_YEAR, fromWeek);
        System.out.println("calendar before set Monday " + format.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println("calendar after set Monday " + format.format(calendar.getTime()));

        Calendar calDayMax = Calendar.getInstance();
        calDayMax.set(Calendar.YEAR, toYear);
        calDayMax.setFirstDayOfWeek(Calendar.MONDAY);
        calDayMax.set(Calendar.WEEK_OF_YEAR, toWeek);
        calDayMax.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date dayMax = calDayMax.getTime();

        System.out.println("dat Max : " + format.format(dayMax));
    }
}
