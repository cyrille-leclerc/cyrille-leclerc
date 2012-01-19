package cyrille.jms.mq;

import junit.framework.TestCase;

/**
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
public class MqCharsetUtilsTest extends TestCase {

    public void testGetCharsetName819() {

        String actual = MqCharsetUtils.getCharsetName(819);
        String expected = "ISO-8859-1";
        assertEquals(expected, actual);
    }

    public void testGetCharsetName923() {

        String actual = MqCharsetUtils.getCharsetName(923);
        String expected = "ISO-8859-15";
        assertEquals(expected, actual);
    }

    public void testGetCharsetName1208() {

        String actual = MqCharsetUtils.getCharsetName(1208);
        String expected = "UTF-8";
        assertEquals(expected, actual);
    }

    public void testGetCharsetName1200() {

        String actual = MqCharsetUtils.getCharsetName(1200);
        String expected = "UTF-16";
        assertEquals(expected, actual);
    }

    public void testGetIbmCharacterSetIdUTF8() throws Exception {

        int actual = MqCharsetUtils.getIbmCharacterSetId("UTF8");
        int expected = 1208;
        assertEquals(expected, actual);
    }

    public void testGetIbmCharacterSetIdUTF_8() throws Exception {

        int actual = MqCharsetUtils.getIbmCharacterSetId("UTF-8");
        int expected = 1208;
        assertEquals(expected, actual);
    }

    public void testGetIbmCharacterSetIdUtf8() throws Exception {

        int actual = MqCharsetUtils.getIbmCharacterSetId("utf8");
        int expected = 1208;
        assertEquals(expected, actual);
    }

    public void testGetIbmCharacterSetIdUtf_8() throws Exception {

        int actual = MqCharsetUtils.getIbmCharacterSetId("utf-8");
        int expected = 1208;
        assertEquals(expected, actual);
    }

    public void testGetIbmCharacterSetIdISO88591() throws Exception {

        int actual = MqCharsetUtils.getIbmCharacterSetId("ISO-8859-1");
        int expected = 819;
        assertEquals(expected, actual);
    }

    public void testGetIbmCharacterSetIdISO885915() throws Exception {

        int actual = MqCharsetUtils.getIbmCharacterSetId("ISO-8859-15");
        int expected = 923;
        assertEquals(expected, actual);
    }
}
