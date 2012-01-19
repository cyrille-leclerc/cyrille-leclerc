/*
 * Created on Feb 17, 2006
 */
package cyrille.statics.di;

public class MyServiceMockForExceedingWeight implements MyService {

    public MyServiceMockForExceedingWeight() {
        super();
    }

    public String doJob(String in) {
        return "hello";
    }
}
