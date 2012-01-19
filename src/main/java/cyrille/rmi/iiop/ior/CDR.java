// CDR.java
//
// Copyright 1997, 1998, 1999 Entrance Software GmbH, Kassel
//
// Author : 03/09/1997 Paul Watzlaw
// Last update: 11/10/1997
//              11/18/1997 Convert characters to lower case.
//                         To avoid an overflow use a char array instead of a
//                         byte array (_CDRArray).
//              12/29/1998 Paul Watzlaw
//                         Changed package name from DE.ENTRANCE.CORBA20
//                         to de.entrance.CORBA20.
//              12/30/1998 Paul Watzlaw
//                         Return stringLen-1 bytes in method getString.
//
// pwatzlaw@entrance.de

package cyrille.rmi.iiop.ior;

public class CDR {

    protected boolean byteOrder;

    protected int byteLen, bytePos = 1;

    protected char[] CDRArray;

    public char getChar() {
        return this.CDRArray[this.bytePos++];
    }

    public long getULong() {
        skip(4);

        return getBytes(4);
    }

    public int getUShort() {
        skip(2);

        return (int) getBytes(2);
    }

    public char[] getSequence() {
        char[] sequence;
        double seqLen;

        seqLen = getULong();
        sequence = new char[(int) seqLen];
        for (int i = 0; i < seqLen; i++) {
            sequence[i] = this.CDRArray[this.bytePos++];
        }
        return sequence;
    }

    public String getString() {
        // In IDL an unsigned long is a 32-bit unsigned integer. In Java a long
        // is a 64-bit signed two's-complement integer.

        double stringLen;

        stringLen = getULong();
        this.bytePos += (int) stringLen;
        return new String(this.CDRArray, this.bytePos - (int) stringLen, (int) stringLen - 1);
    }

    public void parseByteArray(char[] sequence) {
        this.CDRArray = sequence;
        init();
    }

    public void parseHexString(String hexCDRString) {
        char cdrByte;

        this.byteLen = hexCDRString.length() / 2;
        this.CDRArray = new char[this.byteLen];
        for (int i = 0; i < this.byteLen * 2; i += 2) {
            cdrByte = hex2dual(hexCDRString.charAt(i));
            cdrByte = (char) (cdrByte << 4);
            cdrByte |= hex2dual(hexCDRString.charAt(i + 1));
            this.CDRArray[i / 2] = cdrByte;
        }
        init();
    }

    public String toHexString() {
        return new String();
    }

    // Protected methods.

    protected long getBytes(int count) {
        // Copy the octets depending on the server's byte order. Java Virtual
        // Machine uses ordering in big-endian (IOR flag is 0 = false).

        long buff = 0;

        if (this.byteOrder) {
            // Server's ordering is little-endian (IOR flag is 1 = true).

            for (int i = count - 1; i >= 0; i--) {
                buff += this.CDRArray[this.bytePos + i];
                if (i != 0) {
                    buff = (buff << 8);
                }
            }
            this.bytePos += count;
            /*
             * for ( int i = 0; i < count; i++) buff += CDRArray[bytePos++]*((long) Math.pow(256,
             * i));
             */
        } else {
            // Server's ordering is big-endian (IOR flag is 0 = false).

            for (int i = 0; i < count; i++) {
                buff += this.CDRArray[this.bytePos++];
                if (i != count - 1) {
                    buff = (buff << 8);
                }
            }
            /*
             * for ( int i = 0; i < count; i++) buff += CDRArray[bytePos++]*((long) Math.pow(256,
             * count-i-1));
             */
        }
        return buff;
    }

    protected char hex2dual(char hc) {
        char dual = 0;

        if (Character.isDigit(hc)) {
            dual = (char) (new Integer("" + hc).intValue());
        } else {
            hc = Character.toLowerCase(hc);

            if (hc == 'a') {
                dual = 10;
            }
            if (hc == 'b') {
                dual = 11;
            }
            if (hc == 'c') {
                dual = 12;
            }
            if (hc == 'd') {
                dual = 13;
            }
            if (hc == 'e') {
                dual = 14;
            }
            if (hc == 'f') {
                dual = 15;
            }
        }
        return dual;
    }

    protected void init() {
        if (this.CDRArray[0] == 0) {
            this.byteOrder = false;
        } else {
            this.byteOrder = true;
        }
    }

    protected void skip(int alignment) {
        double remainder;

        remainder = this.bytePos % alignment;
        if (remainder > 0) {
            this.bytePos += (alignment - (int) remainder);
        }
    }
}