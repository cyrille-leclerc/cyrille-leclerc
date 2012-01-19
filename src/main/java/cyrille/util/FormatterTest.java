package cyrille.util;

import static junit.framework.Assert.assertEquals;

import java.util.Formatter;

import org.junit.Test;

public class FormatterTest {

    @Test
    public void testBasic() throws Exception {
        String actual = new Formatter().format("hello %s %s", "beautiful", "world").toString();
        String expected = "hello beautiful world";
        assertEquals(expected, actual);
    }
}
