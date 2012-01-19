// IORTest.java
//
// Copyright 1997 Entrance Software GmbH, Kassel
//
// Author : 03/09/1997 Paul Watzlaw
// Last update: 11/10/1997
//              11/18/1997 Three new test IORs from a newsgroup posting,
//                         from the echo example of omniORB and the CORBAnet
//                         demo.
//              29/12/1998 Paul Watzlaw
//                         Changed package name from DE.ENTRANCE.CORBA20
//                         to de.entrance.CORBA20.
//
// pwatzlaw@entrance.de
package cyrille.rmi.iiop.ior;

public class IORDecode {

    public static void main(String[] arg) {
        String hexIOR;
        IOR ior;

        if (arg.length == 1) {
            try {
                hexIOR = new String(arg[0]);
                ior = new IOR(hexIOR);
                // System.out.println(arg[0]);
                ior.print();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        } else {
            System.out.println("usage: java IORDecode IORstring");
            System.out.println("   ou: java IORDecode `cat fichier.OR`");
        }

        // From a readme file of the free IIOP implementation from SUN.
        // hexIOR = new
        // String("IOR:00000000000000010000000000000001000000000000001800010000000000066172676f6e0015b3000000046b657930");
        // ior = new IOR(hexIOR);
        // ior.print();

        // From an Orbix server.
        // hexIOR = new
        // String("IOR:010000001300000049444c3a416363466163746f72793a312e300000010000000000000045000000010100000a0000007370696465726d616e0003042d0000003a5c7370696465726d616e3a496e7465726e65745365727665723a303a3a49523a416363466163746f7279003a");
        // ior = new IOR(hexIOR);
        // ior.print();

        // From a CORBA newsgroup posting. I suppose it's an OmniBroker IOR.
        // hexIOR = new
        // String("IOR:000000000000001049444C3A6D79636C6173733A312E3000000000010000000000000038000100000000000D3134372E37362E3234342E390020138A0000001C4F422F49442B4E554D0049444C3A6D79636C6173733A312E30003000");
        // ior = new IOR(hexIOR);
        // ior.print();

        // From the echo example of OmniORB. I'm not sure if this IOR's object
        // key
        // is interpreted correctly. But the object key is a sequence of octets,
        // so
        // theoretically it can contain everything.
        // hexIOR = new
        // String("IOR:000000000000000d49444c3a4563686f3a312e300077e450000000010000000000000028000100000000000d3139322e352e3233392e3134002095f30000000c3369f9a0a5a66bef00000001");
        // ior = new IOR(hexIOR);
        // ior.print();

        // This IOR seems to be from the CORBAnet demo. It contains two tagged
        // profiles. The first tag is new, possibly for a Visigenic specific
        // profile.
        // The second profile is TAG_INTERNET_IOP.
        // hexIOR = new
        // String("IOR:012020202100000049444c3a434f5242416e65742f526f6f6d496e666f726d6174696f6e3a312e3000202020020000000153495670000000010101200500000073756e6700202020d7000000010000004e00000001504d43000000002100000049444c3a434f5242416e65742f526f6f6d496e666f726d6174696f6e3a312e30002020201a000000564953494f5242202d20494f4e414f52422054455354494e4700202000000000000000006a000000010100200c0000003139322e3136382e312e3300600420204e00000001504d43000000002100000049444c3a434f5242416e65742f526f6f6d496e666f726d6174696f6e3a312e30002020201a000000564953494f5242202d20494f4e414f52422054455354494e4700");
        // ior = new IOR( hexIOR);
        // ior.print();
    }
}