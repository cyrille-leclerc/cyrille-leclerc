package cyrille.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class ConfigurableProxySelector extends ProxySelector implements InitializingBean {
    
    private final Logger logger = Logger.getLogger(ConfigurableProxySelector.class);
    
    protected ProxySelector initialdefaultProxySelector;
    
    protected Map<String, Proxy> proxiesByHostName = new HashMap<String, Proxy>();
    
    public void setProxiesByHostName(Map<String, Proxy> proxiesByHostName) {
        this.proxiesByHostName = proxiesByHostName;
    }
    
    /**
     * @param proxiesHostPortByHostName key: hostname that requires to be proxified, value=proxyHost:proxyPort
     */
    public void setProxiesHostPortByHostName(Map<String, String> proxiesHostPortByHostName) {
        Map<String, Proxy> newProxiesByHostName = new HashMap<String, Proxy>();
        for (Entry<String, String> entry : proxiesHostPortByHostName.entrySet()) {
            String hostName = entry.getKey();
            String proxyHostPort = entry.getValue();
            String[] proxyHostPortAsArray = proxyHostPort.split(":");
            if (proxyHostPortAsArray.length != 2) {
                throw new IllegalArgumentException("Expected colon separated 'proxyHost:proxyPort' for host '" + hostName
                                                   + "' ; not supported '" + proxyHostPort + "'");
            }
            String proxyHost = proxyHostPortAsArray[0];
            int proxyPort;
            try {
                proxyPort = Integer.valueOf(proxyHostPortAsArray[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unsupported port '" + proxyHostPortAsArray[1] + "' for host '" + hostName
                                                   + "' and proxyHost '" + proxyHost + "'");
            }
            newProxiesByHostName.put(hostName, new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
        }
        
        this.proxiesByHostName = newProxiesByHostName;
        if (logger.isDebugEnabled()) {
            logger.debug("< setProxiesHostPortByHostName(proxiesHostPortByHostName=" + proxiesHostPortByHostName + ") : "
                         + proxiesByHostName);
        }
    }
    
    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        if (logger.isDebugEnabled()) {
            logger.debug("connectFailed(uri=" + uri + "sa=" + sa + "ioe=" + ioe + ")");
        }
        initialdefaultProxySelector.connectFailed(uri, sa, ioe);
    }
    
    @Override
    public List<Proxy> select(URI uri) {
        if (logger.isDebugEnabled()) {
            logger.debug("> select(uri=" + uri + ")");
        }
        List<Proxy> result;
        Proxy proxy = proxiesByHostName.get(uri.getHost());
        if (proxy == null) {
            result = initialdefaultProxySelector.select(uri);
        } else {
            result = Arrays.asList(proxy);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("< select(uri=" + uri + ") : " + result);
        }
        return result;
    }
    
    public void afterPropertiesSet() throws Exception {
        initialdefaultProxySelector = ProxySelector.getDefault();
        ProxySelector.setDefault(this);
    }
    
}
