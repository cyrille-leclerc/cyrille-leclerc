package cyrille.jdbc.hsqldb;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import junit.framework.TestCase;

public class HsqlDbTest extends TestCase {

    public void testFileDb() throws Exception {

        File file = new File("c:/var/data/eps/");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
        Class.forName("org.hsqldb.jdbcDriver");
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:/var/data/hsqldb/testdb", "sa", "");
        ResultSet rst = connection.createStatement().executeQuery("create table toto");
    }
}
