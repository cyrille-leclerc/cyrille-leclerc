package cyrille.sql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JdbcUtils {

    private JdbcUtils() {
        super();
    }

    public static void dumpMetadata(ResultSetMetaData metadata) throws SQLException {
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            System.out.println("catalogName=" + metadata.getCatalogName(i) + "\tSchemaName=" + metadata.getSchemaName(i) + "\tColumnName="
                    + metadata.getColumnName(i) + "\tcolumnType=" + metadata.getColumnTypeName(i));
        }
    }
}
