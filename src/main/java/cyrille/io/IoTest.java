/*
 * Created on Oct 18, 2004
 */
package cyrille.io;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class IoTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(IoTest.class);
    }

    public void testRename() {
        URL fileUrl = getClass().getResource("test.txt");
        String sourceName = fileUrl.getFile();
        File source = new File(sourceName);
        String destName = fileUrl.getFile() + ".error";
        File dest = new File(destName);
        System.out.println("Rename to dest " + destName);
        boolean success = source.renameTo(dest);
        assertTrue(success);
    }
}
