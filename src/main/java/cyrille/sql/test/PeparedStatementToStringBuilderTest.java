/*
 * Created on Oct 17, 2003
 */
package cyrille.sql.test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import junit.framework.TestCase;
import cyrille.sql.PreparedStatementToStringBuilder;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class PeparedStatementToStringBuilderTest extends TestCase {

    /**
     * Constructor for PeparedStatementToStringBuilderTest.
     * 
     * @param name
     */
    public PeparedStatementToStringBuilderTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PeparedStatementToStringBuilderTest.class);
    }

    /*
     * Test for String toString()
     */
    public void testToString() throws SQLException {
        String sql = "select * from myTable where colString = ? and colInt = ? and colDate = ? and colTime = ? and colTimestamp = ?;";
        PreparedStatementToStringBuilder builder = new PreparedStatementToStringBuilder(sql);
        builder.setParameter(1, "myString");
        builder.setParameter(2, 123);
        builder.setParameter(3, new Date(System.currentTimeMillis()));
        builder.setParameter(4, new Time(System.currentTimeMillis()));
        builder.setParameter(5, new Timestamp(System.currentTimeMillis()));
        System.out.println("pattern\t'" + sql + "'");
        System.out.println("sql\t'" + builder.toString() + "'");
    }

    /*
     * Test for String toString()
     */
    public void testToStringDelimitorAsLastChar() throws SQLException {
        String sql = "select * from myTable where colString = ? and colInt = ? and colDate = ? and colTime = ? and colTimestamp = ?";
        PreparedStatementToStringBuilder builder = new PreparedStatementToStringBuilder(sql);
        builder.setParameter(1, "myString");
        builder.setParameter(2, 123);
        builder.setParameter(3, new Date(System.currentTimeMillis()));
        builder.setParameter(4, new Time(System.currentTimeMillis()));
        builder.setParameter(5, new Timestamp(System.currentTimeMillis()));
        System.out.println("pattern\t'" + sql + "'");
        System.out.println("sql\t'" + builder.toString() + "'");
    }
}