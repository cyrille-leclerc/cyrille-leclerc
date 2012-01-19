/*
 * Created on Feb 17, 2006
 */
package cyrille.statics;

import junit.framework.TestCase;

public class MyBusinessOperationTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(MyBusinessOperationTest.class);
    }

    public void testMyBusinessOperation() {
        MyService myServiceMockForExceedingWeight = new MyServiceMockForExceedingWeight();

        ServiceLocator.getInstance().setInstance("MyService", myServiceMockForExceedingWeight);

        MyBusinessOperation myBusinessOperation = new MyBusinessOperation();

        String actual = myBusinessOperation.performMyBusinessOperation();
    }

    public void test2MyBusinessOperation() {
        MyService myServiceMockForExceedingWeight = new MyServiceMockForExceedingWeight();

        ServiceLocator.getInstance().setInstance("AnotherService", myServiceMockForExceedingWeight);

        MyBusinessOperation myBusinessOperation = new MyBusinessOperation();

        String actual = myBusinessOperation.performMyBusinessOperation();
    }
}
