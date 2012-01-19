/*
 * Created on May 3, 2006
 */
package cyrille.springframework.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "bean:name=java5AnnotedJmxTestServiceImpl", description = "The Java5AnnotedJmxTestServiceImpl", log = true, logFile = "jmx.log", currencyTimeLimit = 15, persistPolicy = "OnUpdate", persistPeriod = 200, persistLocation = "Java5AnnotedJmxTestServiceImplPersistLocation", persistName = "Java5AnnotedJmxTestServiceImplPersistName")
public class Java5AnnotedJmxTestServiceImpl implements JmxTestService {

    private int poolMaxSize = 5;

    @ManagedAttribute(description = "The PoolMaxSize Attribute")
    public int getPoolMaxSize() {
        return this.poolMaxSize;
    }

    public void setPoolMaxSize(int poolMaxSize) {
        this.poolMaxSize = poolMaxSize;
    }

    @ManagedAttribute(description = "Current Size of the pool")
    public int getPoolCurrentSize() {
        return this.poolMaxSize;
    }

    @ManagedOperation(description = "Purge the cache")
    public void purgeCache() {
        System.out.println("purgeCache called");
    }

    public void dontExposeMe() {
        throw new RuntimeException();
    }
}