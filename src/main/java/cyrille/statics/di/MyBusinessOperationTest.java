/*
 * Created on Feb 17, 2006
 */
package cyrille.statics.di;

import junit.framework.TestCase;

public class MyBusinessOperationTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(MyBusinessOperationTest.class);
    }

    public void testMyBusinessOperation() {
        MyService myServiceMockForExceedingWeight = new MyServiceMockForExceedingWeight();

        MyBusinessOperation myBusinessOperation = new MyBusinessOperation(myServiceMockForExceedingWeight, "toto", 90);

        String actual = myBusinessOperation.performMyBusinessOperation("toto");
    }
}
