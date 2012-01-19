/*
 * Created on Aug 22, 2004
 */
package cyrille.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

/**
 * <p>
 * java code for a change password jsp page that would be associated with a users table with
 * password stores with MySql encrypt() function
 * </p>
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class ChangeMySqlPasswordTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ChangeMySqlPasswordTest.class);
    }

    public void changePassword() throws SQLException {
        String userName = "cleclerc";
        String oldPassword = "azerty";
        String newPassword = "qsdfgh";

        String sql = "SELECT ENCRYPT(?)";
        Connection cnn = null;
        PreparedStatement stmt = cnn.prepareStatement(sql);

        stmt.setString(1, oldPassword);
        ResultSet rst = stmt.executeQuery();
        boolean hasNext = rst.next();
        String encryptedOldPassword = rst.getString(1);
        rst.close();

        stmt.setString(1, newPassword);
        rst = stmt.executeQuery();
        hasNext = rst.next();
        String encryptedNewPassword = rst.getString(1);
        rst.close();

        String select = "select * from user where username = ?";
    }
}