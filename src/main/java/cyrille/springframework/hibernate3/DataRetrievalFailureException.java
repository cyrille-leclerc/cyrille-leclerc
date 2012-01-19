/*
 * Created on May 7, 2006
 */
package cyrille.springframework.hibernate3;

import java.io.Serializable;

public class DataRetrievalFailureException extends org.springframework.dao.DataRetrievalFailureException {

    private static final long serialVersionUID = 1L;

    public DataRetrievalFailureException(String msg) {
        super(msg);
    }

    public DataRetrievalFailureException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public DataRetrievalFailureException(Class entityClass, Serializable id) {
        super("Exception retrieving entityClass=" + entityClass + " with id=" + id);
    }

    public DataRetrievalFailureException(String queryString, String paramName, Object value) {
        super("Exception retrieving queryString='" + queryString + "' with paramName='" + paramName + "', value='" + value + "'");
    }

}
