/*
 * Created on Feb 17, 2006
 */
package cyrille.statics;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static ServiceLocator instance = new ServiceLocator();

    static public ServiceLocator getInstance() {
        return instance;
    }

    private Map<String, Object> services = new HashMap<String, Object>();

    private ServiceLocator() {
        super();
        this.services.put("MyService", new MyServiceImpl());
    }

    public Object locate(String serviceName) {
        return this.services.get(serviceName);
    }

    public void setInstance(String serviceName, Object instance) {
        this.services.put(serviceName, instance);
    }
}
