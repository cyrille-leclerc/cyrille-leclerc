/*
 * Created on Aug 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package cyrille.text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FormatTest extends TestCase {

    /**
     * Constructor for SimpleDateFormatTest.
     * 
     * @param name
     */
    public FormatTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(FormatTest.class);
    }

    /*
     * Test for java.util.Date parse(java.lang.String) with year on 4 digits
     */
    public void testParseYyyyForYyyy() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expectedDate = new GregorianCalendar(2003, 1, 13).getTime();
        Date parsedDate = dateFormat.parse("13/02/2003");
        assertEquals(expectedDate, parsedDate);
    }

    /*
     * Test for java.util.Date parse(java.lang.String) with year on 2 digits
     */
    public void testParseYyForYyyy() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expectedDate = new GregorianCalendar(2003, 1, 13).getTime();
        Date parsedDate = dateFormat.parse("13/02/03");
        assertEquals(expectedDate, parsedDate);
    }

    /*
     * Test for java.util.Date parse(java.lang.String) with year on 4 digits
     */
    public void testParseYyyyForYy() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date expectedDate = new GregorianCalendar(2003, 1, 13).getTime();
        Date parsedDate = dateFormat.parse("13/02/2003");
        assertEquals(expectedDate, parsedDate);
    }

    /*
     * Test for java.util.Date parse(java.lang.String) with year on 1 digits
     */
    public void testParseYForYy() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date expectedDate = new GregorianCalendar(2003, 1, 13).getTime();
        Date parsedDate = dateFormat.parse("13/02/3");
        assertEquals(expectedDate, parsedDate);
    }

    /*
     * Test for java.util.Date parse(java.lang.String) with year on 1 digits
     */
    public void testParseYForY() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/y");
        Date expectedDate = new GregorianCalendar(2003, 1, 13).getTime();
        Date parsedDate = dateFormat.parse("13/02/3");
        assertEquals(expectedDate, parsedDate);
    }

    /**
     * Test for java.util.Date parse(java.lang.String) with year on 2 digits
     */
    public void testParseYyForYy() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date expectedDate = new GregorianCalendar(2003, 1, 13).getTime();
        Date parsedDate = dateFormat.parse("13/02/03");
        assertEquals(expectedDate, parsedDate);
    }

    /**
     * Method testMessageFormat.
     */
    public void testMessageFormat() throws Exception {
        String expected = "Hello world 'toto'";
        String pattern = "Hello world ''{1}''";
        String actual = MessageFormat.format(pattern, new Object[] { "Cyrille", "toto" });
        assertEquals("pattern '" + pattern + "'", expected, actual);
    }

    public void testFormatDecimal() {
        String expected = "1.50";
        String pattern = "0.00";
        float input = (float) 1.5;
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String actual = decimalFormat.format(input);
        assertEquals("pattern '" + pattern + "'", expected, actual);
    }

    public void testFormatInt() {
        String expected = "005";
        String pattern = "000";
        int input = 5;
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String actual = decimalFormat.format(input);
        assertEquals("pattern '" + pattern + "'", expected, actual);
    }

    public void testFormat2() {
        String expected = "5";
        String pattern = "###";
        int input = 5;
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String actual = decimalFormat.format(input);
        assertEquals("pattern '" + pattern + "'", expected, actual);
    }

    /**
     * Test for java.lang.String format(java.lang.Object)
     */
    public void testFormatObject() {
    }

}
