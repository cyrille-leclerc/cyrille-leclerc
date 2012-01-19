/*
 * Created on Oct 4, 2004
 */
package cyrille.rmi.iiop;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class JndiUtils {

    private static final Log log = LogFactory.getLog(JndiUtils.class);

    /**
     * 
     */
    private JndiUtils() {
        super();
    }

    /**
     * Creates the subcontext identified by the given <code>path</code>
     * 
     * @param context
     * @param path
     *            slash separated hierarchy of sub contexts (e.g. /mdsp/brodcasterlistener/
     * @throws NamingException
     */
    public static Context createSubcontext(Context context, String path) throws NamingException {
        Validate.notNull(context, "context");
        Validate.notNull(path, "path");
        if (log.isDebugEnabled()) {
            log.debug("> createSubcontext(context=" + context.getNameInNamespace() + ", path=" + path + ")");
        }
        String[] subContextsNames = StringUtils.split(path, "/");
        Context currentContext = context;
        for (String subContextName : subContextsNames) {
            try {
                currentContext = (Context) currentContext.lookup(subContextName);
                if (log.isDebugEnabled()) {
                    log.debug("Context '" + subContextName + "' already exist");
                }
            } catch (NameNotFoundException e) {
                currentContext = currentContext.createSubcontext(subContextName);
                if (log.isDebugEnabled()) {
                    log.debug("Context '" + subContextName + "' created");
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("< createSubcontext() : " + currentContext.getNameInNamespace());
        }
        return currentContext;
    }

}