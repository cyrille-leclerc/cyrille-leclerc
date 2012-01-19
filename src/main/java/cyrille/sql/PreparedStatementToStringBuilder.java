/*
 * Created on Oct 17, 2003
 */
package cyrille.sql;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.StringTokenizer;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class PreparedStatementToStringBuilder {

    private final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    private static final char SQL_PARAMETER_CHAR = '?';

    private static final String SQL_PARAMETER_STRING = "?";

    private final static String TIME_FORMAT_PATTERN = "hh:mm:ss";

    private final static String TIMESTAMP_FORMAT_PATTERN = "yyyy-MM-dd hh:mm:ss";

    /**
     * 1 based array to fit PreparementStatements 1 based parameters indexes
     */
    private String[] parameters;

    private int parametersCount;

    private String[] splitSql;

    private String sql;

    /**
     * @param sql
     */
    public PreparedStatementToStringBuilder(String sql) {
        super();
        if (sql == null) {
            throw new NullArgumentException("sql");
        }
        this.sql = sql;
        // (+ 1) because array is 1 based
        this.parametersCount = count(this.sql, SQL_PARAMETER_CHAR) + 1;
        this.parameters = new String[this.parametersCount];
    }

    private void checkIndexOutOfBound(int idx) {
        if (idx >= this.parametersCount) {
            throw new IndexOutOfBoundsException("There is no parameter for index " + idx + " for the sql request " + this.sql);
        }
    }

    /**
     * Optimized (char instead of String) count implementation
     * 
     * @param str
     * @param chr
     * @return number of occurences of the given <code>char</code> in the <code>String</code>
     * 
     */
    private int count(String str, char chr) {
        int count = 0;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == chr) {
                count++;
            }
        }
        return count;
    }

    public void setNullParameter(int idx) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = null;
    }

    public void setParameter(int idx, boolean value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = String.valueOf(value);
    }

    public void setParameter(int idx, Date value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = "TO_DATE('" + DateFormatUtils.format(value, DATE_FORMAT_PATTERN) + "', 'YYYY-MM-DD')";
    }

    public void setParameter(int idx, double value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = String.valueOf(value);
    }

    public void setParameter(int idx, float value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = String.valueOf(value);
    }

    public void setParameter(int idx, int value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = String.valueOf(value);
    }

    public void setParameter(int idx, long value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = String.valueOf(value);
    }

    public void setParameter(int idx, Number value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = String.valueOf(value);
    }

    public void setParameter(int idx, Object value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = "[" + String.valueOf(value) + "]";
    }

    public void setParameter(int idx, String value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = "'" + value + "'";
    }

    public void setParameter(int idx, Time value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = "TO_DATE('" + DateFormatUtils.format(value, TIME_FORMAT_PATTERN) + "', 'HH24:MI:SS')";
    }

    public void setParameter(int idx, Timestamp value) {
        checkIndexOutOfBound(idx);
        this.parameters[idx] = "TO_DATE('" + DateFormatUtils.format(value, TIMESTAMP_FORMAT_PATTERN) + "', 'YYYY-MM-DD HH24:MI:SS')";
    }

    /**
     * Returns a filled sql statement
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // Lazy split
        if (this.splitSql == null) {
            StringTokenizer tkSql = new StringTokenizer(this.sql, SQL_PARAMETER_STRING, true);
            this.splitSql = new String[tkSql.countTokens()];
            int i = 0;
            while (tkSql.hasMoreElements()) {
                this.splitSql[i] = tkSql.nextToken();
                i++;
            }
        }

        StringBuffer sb = new StringBuffer();
        int parameterIdx = 1;
        for (String element : this.splitSql) {
            if (SQL_PARAMETER_STRING.equals(element)) {
                sb.append(this.parameters[parameterIdx]);
                parameterIdx++;
            } else {
                sb.append(element);
            }
        }
        return sb.toString();
    }
}