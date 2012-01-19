package cyrille.net;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class ConfigurableProxySelectorTest {
    
    @Test
    public void testSelectURIDelegatedToInitialProxySelector() throws Exception {
        ProxySelector proxySelector = new ConfigurableProxySelector();
        List<Proxy> actual = proxySelector.select(new URI("http://localhost:80/"));
        
        List<Proxy> expected = Arrays.asList(Proxy.NO_PROXY);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSetProxiesHostPortByHostName() throws Exception {
        String hostName = "myHost";
        String proxyHost = "myProxyServer";
        int proxyPort = 8080;
        
        ConfigurableProxySelector configurableProxySelector = new ConfigurableProxySelector();
        Map<String, Proxy> expected = new HashMap<String, Proxy>();
        Proxy expectedProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        expected.put(hostName, expectedProxy);
        
        Map<String, String> proxiesHostPortByHostName = new HashMap<String, String>();
        proxiesHostPortByHostName.put(hostName, proxyHost + ":" + proxyPort);
        
        configurableProxySelector.setProxiesHostPortByHostName(proxiesHostPortByHostName);
        
        Map<String, Proxy> actual = configurableProxySelector.proxiesByHostName;
        
        assertEquals(expected, actual);
    }
    
}
