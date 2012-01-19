/*
 * Created on May 6, 2006
 */
package cyrille.springframework.jmx;

public interface JmxTestService {

    public int getPoolMaxSize();

    public void setPoolMaxSize(int poolMaxSize);

    public int getPoolCurrentSize();

    public void purgeCache();

}