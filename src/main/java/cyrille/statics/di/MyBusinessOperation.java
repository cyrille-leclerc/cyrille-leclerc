/*
 * Created on Feb 17, 2006
 */
package cyrille.statics.di;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MyBusinessOperation {

    private MyService myService;

    private String hostName;

    private int port;

    public MyBusinessOperation(MyService myService, String hostName, int port) {
        super();
        this.myService = myService;
        this.hostName = hostName;
        this.port = port;
    }

    public String performMyBusinessOperation(String param1) {
        try {
            String result = this.myService.doJob(param1);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Exception invoking doJob with param=" + param1 + " on " + this.toString());
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("hostName", this.hostName).append("port", this.port).append("myService", this.myService).toString();
    }

}
