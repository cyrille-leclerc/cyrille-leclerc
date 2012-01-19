/*
 * Created on Feb 17, 2006
 */
package cyrille.statics;

public class MyBusinessOperation {

    public MyBusinessOperation() {
        super();
    }

    public String performMyBusinessOperation() {
        MyService myService = (MyService) ServiceLocator.getInstance().locate("MyService");
        String result = myService.doJob("blabla");
        return result;
    }
}
