/*
 * Created on Sep 21, 2003
 */
package cyrille.xml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class MyEntityResolver implements EntityResolver {
    private final static Log log = LogFactory.getLog(MyEntityResolver.class);

    /**
     * key public identifier, value DTD URL
     */
    private Map m_entityValidator = new HashMap();

    /**
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        boolean traceEnabled = log.isTraceEnabled();

        if (traceEnabled) {
            log.trace("resolveEntity('" + publicId + "', '" + systemId + "')");
        }

        // Has this public identifier been registered?
        String entityURL = null;
        if (publicId != null) {
            entityURL = (String) this.m_entityValidator.get(publicId);
        }

        // Has this system identifier been registered?
        if (entityURL == null && systemId != null) {
            entityURL = (String) this.m_entityValidator.get(systemId);
        }

        if (entityURL == null) {
            return (null);
        }

        // Return an input source to our alternative URL
        if (traceEnabled) {
            log.trace(" Resolving to alternate DTD '" + entityURL + "'");
        }

        try {
            return (new InputSource(entityURL));
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }

    /**
     * Register the specified DTD URL for the specified public identifier.
     * 
     * @param publicId
     *            Public identifier of the DTD to be resolved
     * @param entityURL
     *            The URL to use for reading this DTD
     */
    public void register(String publicId, String entityURL) {
        boolean traceEnabled = log.isTraceEnabled();
        if (traceEnabled) {
            log.trace("register('" + publicId + "', '" + entityURL + "'");
        }
        this.m_entityValidator.put(publicId, entityURL);
    }

}
