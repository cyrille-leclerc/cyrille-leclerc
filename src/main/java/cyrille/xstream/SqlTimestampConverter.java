/*
 * Created on Aug 8, 2004
 */
package cyrille.xstream;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;
import com.thoughtworks.xstream.converters.basic.ThreadSafeSimpleDateFormat;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class SqlTimestampConverter extends AbstractBasicConverter implements Converter {

    private static final int DATE_FORMATS_POOL_MAX_SIZE = 20;

    private static final int DATE_FORMATS_POOL_MIN_SIZE = 2;

    ThreadSafeSimpleDateFormat m_dateFormat;

    /**
     * 
     */
    public SqlTimestampConverter(String pattern) {
        super();
        this.m_dateFormat = new ThreadSafeSimpleDateFormat(pattern, DATE_FORMATS_POOL_MIN_SIZE, DATE_FORMATS_POOL_MAX_SIZE);
    }

    /**
     * @see com.thoughtworks.xstream.converters.basic.AbstractBasicConverter#canConvert(java.lang.Class)
     */
    @Override
    public boolean canConvert(Class type) {
        return Timestamp.class.equals(type);
    }

    /**
     * @see com.thoughtworks.xstream.converters.basic.AbstractBasicConverter#fromString(java.lang.String)
     */
    @Override
    protected Object fromString(String str) {
        try {
            return this.m_dateFormat.parse(str);
        } catch (ParseException e) {
            throw new ConversionException("Exception converting '" + str + "': " + e.getMessage(), e);
        }
    }

    /**
     * @see com.thoughtworks.xstream.converters.basic.AbstractBasicConverter#toString(java.lang.Object)
     */
    @Override
    protected String toString(Object obj) {
        Date date = (Date) obj;
        return this.m_dateFormat.format(date);
    }

}