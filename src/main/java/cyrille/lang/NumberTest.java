package cyrille.lang;

import static org.junit.Assert.*;
import org.junit.Test;

public class NumberTest {
    
    @Test
    public void testAutoboxing() {
        Long amount = Long.valueOf(1000);
        amount = amount / 100;
        System.out.println(amount + "");
    }
    
    @Test
    public void testDecode() throws Exception {
        String valueAsString = "194";
        int actual = Integer.decode(valueAsString);
        assertEquals(194, actual);
    }
}
