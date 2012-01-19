/*
 * Hello.java - MBean implementation for the Hello World MBean. This class must
 * implement all the Java methods declared in the HelloMBean interface, with the
 * appropriate behavior for each one.
 */

package cyrille.jmx;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

public class Hello implements HelloMBean {

    private static final int DEFAULT_CACHE_SIZE = 200;

    private int cacheSize = DEFAULT_CACHE_SIZE;

    private final String name = "Reginald";

    public int add(int x, int y) {
        return x + y;
    }

    /*
     * Getter for the CacheSize attribute. The pattern shown here is frequent: the getter returns a
     * private field representing the attribute value, and the setter changes that field.
     */
    public int getCacheSize() {
        return this.cacheSize;
    }

    /*
     * Getter for the Name attribute. The pattern shown here is frequent: the getter returns a
     * private field representing the attribute value. In our case, the attribute value never
     * changes, but for other attributes it might change as the application runs. Consider an
     * attribute representing statistics such as uptime or memory usage, for example. Being
     * read-only just means that it can't be changed through the management interface.
     */
    public String getName() {
        return this.name;
    }

    public void sayHello() {
        System.out.println("hello, world");
    }

    /*
     * Setter for the CacheSize attribute. To avoid problems with stale values in multithreaded
     * situations, it is a good idea for setters to be synchronized.
     */
    public synchronized void setCacheSize(int size) {
        this.cacheSize = size;

        /*
         * In a real application, changing the attribute would typically have effects beyond just
         * modifying the cacheSize field. For example, resizing the cache might mean discarding
         * entries or allocating new ones. The logic for these effects would be here.
         */
        System.out.println("Cache size now " + this.cacheSize);
    }

    /**
     * @see cyrille.jmx.HelloMBean#getMyCompositeData()
     */
    public CompositeData getMyCompositeData() throws Exception {
        CompositeData cds = getMyCompositeData("zeName", new Integer(3));

        return cds;
    }

    public CompositeData getMyCompositeData(String name, Integer value) throws Exception {
        String[] itemNames = new String[] { "name", "value" };
        String[] itemDescriptions = new String[] { "Name of the truc", "Value of the truc" };
        OpenType[] itemTypes = new OpenType[] { SimpleType.STRING, SimpleType.INTEGER };
        CompositeType compositeType = new CompositeType("compositeTypeTypeName", "compositeTypeDescription", itemNames, itemDescriptions,
                itemTypes);
        Object[] itemValues = new Object[] { name, value };

        CompositeDataSupport cds = new CompositeDataSupport(compositeType, itemNames, itemValues);

        return cds;
    }

    /**
     * @see cyrille.jmx.HelloMBean#getMyTabularData()
     */
    public TabularData getMyTabularData() throws Exception {
        CompositeType rowType;
        {
            String[] itemNames = new String[] { "key1", "key2", "value" };

            String[] itemDescriptions = new String[] { "desc1", "desc2", "zeValue" };

            OpenType[] itemTypes = new OpenType[] { SimpleType.STRING, SimpleType.INTEGER, SimpleType.INTEGER };

            rowType = new CompositeType("rowTypeName", "rowDescription", itemNames, itemDescriptions, itemTypes);

        }

        String[] indexNames = new String[] { "key1", "key2" };

        TabularType tabularType = new TabularType("typeName", "description", rowType, indexNames);

        TabularDataSupport tabularDataSupport = new TabularDataSupport(tabularType);

        for (int i = 0; i < 5; i++) {
            String[] itemNames = new String[] { "key1", "key2", "value" };
            Object[] itemValues = new Object[] { "key1-" + i, new Integer(i), new Integer(i * 10) };
            CompositeData compositeData = new CompositeDataSupport(rowType, itemNames, itemValues);
            tabularDataSupport.put(compositeData);
        }

        return tabularDataSupport;

    }
}
