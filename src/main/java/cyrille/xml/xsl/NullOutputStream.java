/*
 * Created on Oct 30, 2004
 */
package cyrille.xml.xsl;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class NullOutputStream extends OutputStream {

    /**
     * 
     */
    public NullOutputStream() {
        super();
    }

    /**
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException {
        // Null object, do nothing
    }

}
