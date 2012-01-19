/*
 * Created on Sep 21, 2003
 */
package cyrille.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class MyURIResolver implements URIResolver {
    private final static Log log = LogFactory.getLog(MyURIResolver.class);

    /**
     * key public identifier, value DTD URL
     */
    private Map m_entityValidator = new HashMap();

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

    /**
     * @see javax.xml.transform.URIResolver#resolve(java.lang.String, java.lang.String)
     */
    public Source resolve(String href, String base) throws TransformerException {
        boolean traceEnabled = log.isTraceEnabled();

        if (traceEnabled) {
            log.trace("resolveEntity('" + href + "', '" + base + "')");
        }

        // Has this public identifier been registered?
        String entityURL = null;
        if (href != null) {
            entityURL = (String) this.m_entityValidator.get(href);
        }

        // Has this system identifier been registered?
        if (entityURL == null && base != null) {
            entityURL = (String) this.m_entityValidator.get(base);
        }

        if (entityURL == null) {
            return (null);
        }

        // Return an input source to our alternative URL
        if (traceEnabled) {
            log.trace(" Resolving to alternate DTD '" + entityURL + "'");
        }

        try {
            return (new StreamSource(entityURL));
        } catch (Exception e) {
            throw new TransformerException(e);
        }
    }

}
