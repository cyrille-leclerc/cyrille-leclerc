/*
 * Created on Nov 3, 2006
 */
package cyrille.sql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

public class GetOracleViewsTest extends TestCase {

    public void test() throws Exception {

        File baseFolder = new File("C:/data/projects/eLuxury/svn/configurationManagement/production/oracle/elux6prd/views");

        String login = "lgorokhova";
        String password = "Blu52E";

        extractViews(baseFolder, login, password);

        login = password = "crystal";

        extractViews(baseFolder, login, password);

    }

    /**
     * @param baseFolder
     * @param login
     * @param password
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private void extractViews(File baseFolder, String login, String password) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@10.35.153.153:1521:elux6prd", login, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM SYS.ALL_VIEWS WHERE OWNER like '" + login.toUpperCase() + "'");
        while (resultSet.next()) {
            String owner = resultSet.getString("owner");
            String viewName = resultSet.getString("view_name");
            String code = resultSet.getString("text");

            PrintWriter out = new PrintWriter(new FileWriter(new File(baseFolder, "create-" + owner + "-" + viewName + ".sql")));
            out.println("CREATE OR REPLACE VIEW " + owner + "." + viewName + " AS");
            out.println(code);
            out.close();
        }
    }
}
