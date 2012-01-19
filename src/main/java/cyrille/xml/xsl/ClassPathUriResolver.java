/*
 * Created on Oct 30, 2004
 */
package cyrille.xml.xsl;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class ClassPathUriResolver implements URIResolver {

    /**
     * 
     */
    public ClassPathUriResolver() {
        super();
    }

    /**
     * @see javax.xml.transform.URIResolver#resolve(java.lang.String, java.lang.String)
     */
    public Source resolve(String href, String base) throws TransformerException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(href);
        if (in == null) {
            if (href.charAt(0) == '/') {
                in = Thread.currentThread().getContextClassLoader().getResourceAsStream(href.substring(1));
            } else {
                in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + href);
            }
        }
        if (in == null) {
            throw new TransformerException("Resource '" + href + "' not found");
        }
        return new StreamSource(in);
    }

}
