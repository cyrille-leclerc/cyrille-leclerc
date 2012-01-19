/*
 * Created on Oct 30, 2004
 */
package cyrille.xml.xsl;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class NullErrorListener implements ErrorListener {

    /**
     * 
     */
    public NullErrorListener() {
        super();
    }

    /**
     * @see javax.xml.transform.ErrorListener#error(javax.xml.transform.TransformerException)
     */
    public void error(TransformerException exception) throws TransformerException {
        // Null object, do nothing
    }

    /**
     * @see javax.xml.transform.ErrorListener#fatalError(javax.xml.transform.TransformerException)
     */
    public void fatalError(TransformerException exception) throws TransformerException {
        // Null object, do nothing
    }

    /**
     * @see javax.xml.transform.ErrorListener#warning(javax.xml.transform.TransformerException)
     */
    public void warning(TransformerException exception) throws TransformerException {
        // Null object, do nothing
    }

}
