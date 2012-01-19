package cyrille.lang.time;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

import junit.framework.TestCase;

public class FastDateFormatTest extends TestCase {

    public void test() throws Exception {
        FastDateFormat dateFormat = FastDateFormat.getInstance("MM/dd/yyyy HH:mm");
        
        Date date = (Date) dateFormat.parseObject("07/12/2007 11:48");
        
        System.out.println(date);
    }
}
