/*
 * HelloMBean.java - MBean interface describing the management operations and
 * attributes for the Hello World MBean. In this case there are two operations,
 * "sayHello" and "add", and two attributes, "Name" and "CacheSize".
 */

package cyrille.jmx;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

public interface HelloMBean {

    // operations

    void sayHello();

    int add(int x, int y);

    // attributes

    // a read-only attribute called Name of type String
    String getName();

    // a read-write attribute called CacheSize of type int
    int getCacheSize();

    void setCacheSize(int size);

    CompositeData getMyCompositeData() throws Exception;

    TabularData getMyTabularData() throws Exception;
}
