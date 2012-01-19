/*
 * Created on Jul 28, 2005
 */
package cyrille.jndi;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import junit.framework.TestCase;

public class DataSourceTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(DataSourceTest.class);
    }

    public void test() throws Exception {

        Properties properties = new Properties();
        InitialContext context = new InitialContext(properties);

        DataSource ds = (DataSource) context.lookup("jdbc/mydataSource");

        DataSource dsViaResourceRef = (DataSource) context.lookup("java:comp/env/jdbc/mydataSource");

        ds.getConnection();
        dsViaResourceRef.getConnection();

    }
}
