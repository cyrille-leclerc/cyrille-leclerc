/*
 * Created on Jun 29, 2005
 */
package cyrille.net;

import java.net.InetAddress;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.ToStringBuilder;

public class InetAddressTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(InetAddressTest.class);
    }

    public void testGetLocalHost() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(ToStringBuilder.reflectionToString(inetAddress));
        System.out.println("canonicalHostName=" + inetAddress.getCanonicalHostName() + ",hostName=" + inetAddress.getHostName()
                + ",hostAddress=" + inetAddress.getHostAddress());
    }

    public void testGetAllByName() throws Exception {
        InetAddress[] inetAddresses = InetAddress.getAllByName(null);
        for (InetAddress inetAddress : inetAddresses) {
            System.out.println(ToStringBuilder.reflectionToString(inetAddress));
            System.out.println("canonicalHostName=" + inetAddress.getCanonicalHostName() + ",hostName=" + inetAddress.getHostName()
                    + ",hostAddress=" + inetAddress.getHostAddress());
        }
    }

}
