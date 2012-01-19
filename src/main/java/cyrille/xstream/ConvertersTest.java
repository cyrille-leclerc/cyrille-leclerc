package cyrille.xstream;

import java.math.BigDecimal;
import java.math.RoundingMode;

import junit.framework.TestCase;

import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;

public class ConvertersTest extends TestCase {

    public void testBigDecimalConverterToString() throws Exception {
        BigDecimalConverter converter = new BigDecimalConverter();

        BigDecimal bigDecimal = new BigDecimal(10.5f).setScale(2, RoundingMode.HALF_UP);

        String actual = converter.toString(bigDecimal);
        String expected = "10.50";
        assertEquals(expected, actual);
    }

    public void testFloatConverterToString() throws Exception {
        FloatConverter converter = new FloatConverter();

        float zeFloat = 10.5f;
        String actual = converter.toString(zeFloat);
        String expected = "10.5";
        assertEquals(expected, actual);
    }
}
