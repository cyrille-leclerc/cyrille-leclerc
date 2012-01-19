package cyrille.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import junit.framework.TestCase;

public class MathTest extends TestCase {

    public void testBigDecimal() throws Exception {
        BigDecimal five = new BigDecimal(5);
        BigDecimal three = new BigDecimal(3);

        BigDecimal actual = five.divide(three);
        System.out.println(actual);
    }

    public void testBigDecimalJava5() throws Exception {
        MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal five = new BigDecimal(5, mathContext);
        BigDecimal three = new BigDecimal(3, mathContext);

        BigDecimal actual = five.divide(three);
        System.out.println(actual);
    }
}
