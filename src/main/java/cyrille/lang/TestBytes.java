package cyrille.lang;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestBytes {
    public static void main(String[] args) {
        try {
            TestBytes testBytes = new TestBytes();
            testBytes.byteToChar();
        } catch (Exception e) {
        }
    }

    public void byteToChar() throws Exception {
        String cr = "\r";
        String lf = "\n";
        char colon = ':';
        byte colonByte = (byte) colon;
        byte[] crBytes = cr.getBytes();
        System.out.println("cr length " + crBytes.length + " - " + crBytes[0]);
        byte[] lfBytes = lf.getBytes();
        System.out.println("lf length " + lfBytes.length + " - " + lfBytes[0]);
        System.out.println("colon '" + new String(new byte[] { colonByte }) + "'");

    }

}
