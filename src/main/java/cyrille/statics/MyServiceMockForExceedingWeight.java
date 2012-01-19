/*
 * Created on Feb 17, 2006
 */
package cyrille.statics;

public class MyServiceMockForExceedingWeight implements MyService {

    public MyServiceMockForExceedingWeight() {
        super();
    }

    public String doJob(String in) {
        return "hello";
    }
}
