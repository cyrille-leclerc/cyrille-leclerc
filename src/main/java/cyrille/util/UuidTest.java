package cyrille.util;

import java.util.UUID;

import junit.framework.TestCase;

public class UuidTest extends TestCase {

    public void testRandomUuid() throws Exception {
        for (int i = 0; i < 10; i++) {
            String randomUuidAsString = UUID.randomUUID().toString();
            System.out.println("length=" + randomUuidAsString.length() + ", value=" + randomUuidAsString);
        }
    }
}
