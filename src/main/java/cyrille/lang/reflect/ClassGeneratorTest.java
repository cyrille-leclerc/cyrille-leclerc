/*
 * Created on Sep 20, 2004
 */
package cyrille.lang.reflect;

import java.io.PrintWriter;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class ClassGeneratorTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ClassGeneratorTest.class);
    }

    public void testGenerate() {
        ClassGenerator classGenerator = new ClassGenerator();
        PrintWriter writer = new PrintWriter(System.out);
        classGenerator.generate(List.class, writer);
        writer.flush();
    }

}
