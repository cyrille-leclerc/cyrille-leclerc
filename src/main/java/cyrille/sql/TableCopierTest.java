package cyrille.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import junit.framework.TestCase;

public class TableCopierTest extends TestCase {

    public void copy(Connection sourceCnn, Connection targetCnn) throws Exception {
        String[] tableNames = new String[] {};
        for (int i = 0; i < tableNames.length; i++) {
            String tableName = tableNames[i];
            copy(tableName, sourceCnn, targetCnn);
        }
    }

    public void copy(String table, Connection sourceCnn, Connection targetCnn) throws Exception {
        ResultSet rst = sourceCnn.createStatement().executeQuery("select * from " + table);

        ResultSetMetaData metaData = rst.getMetaData();

        String insertSql = buildInsertPrepareStatement(table, metaData);

        PreparedStatement insertStmt = targetCnn.prepareStatement(insertSql);

        while (rst.next()) {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                Object value = rst.getObject(i);
                insertStmt.setObject(i, value);
            }
            insertStmt.addBatch();
        }

        insertStmt.executeBatch();
    }

    private String buildInsertPrepareStatement(String table, ResultSetMetaData metaData) throws SQLException {
        String insertSql = "insert into " + "(";
        String values = " values (";
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if (i > 1) {
                insertSql += ", ";
                values += ", ";
            }
            insertSql += columnName;
            values += "?";
        }
        insertSql += ")";
        values += ")";

        insertSql += values;
        return insertSql;
    }
}
