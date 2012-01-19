/*
 * Created on Oct 17, 2003
 */
package cyrille.lang;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TestApacheStringUtils extends TestCase {

    /**
     * Constructor for TestApacheStringUtils.
     * 
     * @param name
     */
    public TestApacheStringUtils(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestApacheStringUtils.class);
    }

    public void testSplitEndingWithDelimitor() {
        String pattern = "select * from myTable where col1 = ? and col2 = ?";
        String[] splittedPattern = StringUtils.split(pattern, "?");
        System.out.println("pattern\t'" + pattern + "'");
        for (int i = 0; i < splittedPattern.length; i++) {
            System.out.println("element " + i + "\t'" + splittedPattern[i] + "'");
        }
    }

    public void testSplitEndingWithText() {
        String pattern = "select * from myTable where col1 = ? and col2 = ? ";
        String[] splittedPattern = StringUtils.split(pattern, "?");
        System.out.println("pattern\t'" + pattern + "'");
        for (int i = 0; i < splittedPattern.length; i++) {
            System.out.println("element " + i + "\t'" + splittedPattern[i] + "'");
        }
    }
}
