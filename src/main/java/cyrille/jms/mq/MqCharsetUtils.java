package cyrille.jms.mq;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Utils to handle Websphere MQ Coded Character Set ID
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
public class MqCharsetUtils {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final Charset UNICODE = Charset.forName("Unicode");

    /**
     * Mapping between utf-8 and ibm-1208 is not done in the JVM {@link Charset#aliases()}
     */
    private static final int IBM_CHARACTER_SET_ID_UTF8 = 1208;

    /**
     * Mapping between utf-16/unicode and ibm-1200 is not done in the JVM {@link Charset#aliases()}
     */
    private static final int IBM_CHARACTER_SET_ID_UNICODE = 1200;

    /**
     * Private constructor for util class
     */
    private MqCharsetUtils() {
    }

    /**
     * <p>
     * Returns the {@link Charset#name()} associated with the given <code>ibmCharacterSetId</code>
     * </p>
     * <p>
     * Associations are defined by {@link Charset#aliases()} ; special cases are defined by <a
     * href="http://publib.boulder.ibm.com/infocenter/wmqv6/v6r0/topic/com.ibm.mq.csqzav.doc/csqzav0561.htm#csq77t8">IBM
     * Websphere MQ Infocenter - Character set identifiers</a>
     * </p>
     * 
     * @param ibmCharacterSetId
     *            ccsid (e.g. 819)
     * @return Charset charset name (e.g. "ISO-8859-1")
     */
    static public String getCharsetName(int ibmCharacterSetId) {
        Charset charset = getCharset(ibmCharacterSetId);
        return charset.name();
    }

    /**
     * <p>
     * Returns the {@link Charset#name()} associated with the given <code>ibmCharacterSetId</code>
     * </p>
     * <p>
     * Associations are defined by {@link Charset#aliases()} ; special cases are defined by <a
     * href="http://publib.boulder.ibm.com/infocenter/wmqv6/v6r0/topic/com.ibm.mq.csqzav.doc/csqzav0561.htm#csq77t8">IBM
     * Websphere MQ Infocenter - Character set identifiers</a>
     * </p>
     * 
     * @param ibmCharacterSetId
     *            ccsid (e.g. 819)
     * @return Charset charset (e.g. "ISO-8859-1")
     */
    static public Charset getCharset(int ibmCharacterSetId) {
        Charset charset;
        switch (ibmCharacterSetId) {
        case IBM_CHARACTER_SET_ID_UNICODE:
            charset = UNICODE;
            break;
        case IBM_CHARACTER_SET_ID_UTF8:
            charset = UTF8;
            break;
        default:
            charset = Charset.forName("IBM-" + ibmCharacterSetId);
            break;
        }
        return charset;
    }

    /**
     * <p>
     * Returns the IBM Coded Character Set ID associated with the given <code>charsetName</code>
     * </p>
     * <p>
     * Associations are defined by {@link Charset#aliases()} with aliases name matching "IBM-xxx"
     * pattern; special cases are defined by <a
     * href="http://publib.boulder.ibm.com/infocenter/wmqv6/v6r0/topic/com.ibm.mq.csqzav.doc/csqzav0561.htm#csq77t8">IBM
     * Websphere MQ Infocenter - Character set identifiers</a>
     * 
     * @param charsetName
     *            Charset name (e.g. "UTF-8")
     * @return ccsid (e.g. 1208)
     * @throws UnsupportedEncodingException
     */
    static public int getIbmCharacterSetId(String charsetName) throws UnsupportedEncodingException {
        Charset charset = Charset.forName(charsetName);
        return getIbmCharacterSetId(charset);
    }

    /**
     * <p>
     * Returns the IBM Coded Character Set ID associated with the given <code>charsetName</code>
     * </p>
     * <p>
     * Associations are defined by {@link Charset#aliases()} with aliases name matching "IBM-xxx"
     * pattern; special cases are defined by <a
     * href="http://publib.boulder.ibm.com/infocenter/wmqv6/v6r0/topic/com.ibm.mq.csqzav.doc/csqzav0561.htm#csq77t8">IBM
     * Websphere MQ Infocenter - Character set identifiers</a>
     * 
     * @param charsetName
     *            Charset name (e.g. "UTF-8")
     * @return ccsid (e.g. 1208)
     * @throws UnsupportedEncodingException
     */
    public static int getIbmCharacterSetId(Charset charset) throws UnsupportedEncodingException {
        int ibmCharacterSetId;

        if (charset.equals(UNICODE)) {
            ibmCharacterSetId = IBM_CHARACTER_SET_ID_UNICODE;
        } else if (charset.equals(UTF8)) {
            ibmCharacterSetId = IBM_CHARACTER_SET_ID_UTF8;
        } else {
            Set aliases = charset.aliases();
            ibmCharacterSetId = -1;
            for (Iterator itAliases = aliases.iterator(); itAliases.hasNext() && (ibmCharacterSetId == -1);) {
                String alias = (String) itAliases.next();
                if (alias.length() > "ibm-".length() && alias.substring(0, "ibm-".length()).equalsIgnoreCase("ibm-")) {
                    ibmCharacterSetId = Integer.parseInt(alias.substring("ibm-".length()));
                }
            }
            if (ibmCharacterSetId == -1) {
                throw new UnsupportedEncodingException("No IBM-xxx encoding found for charset '" + charset + "'");
            }
        }
        return ibmCharacterSetId;
    }
}
