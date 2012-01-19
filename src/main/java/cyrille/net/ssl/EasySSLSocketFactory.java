/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cyrille.net.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class EasySSLSocketFactory extends SSLSocketFactory {
    
    protected SSLSocketFactory sslSocketFactory;
    
    public EasySSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        super();
        
        SSLContext sslContext = SSLContext.getInstance("SSL");
        TrustManager[] trustAllCerts = new TrustManager[] {
            
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    System.out.println("checkClientTrusted(authType=" + authType + ")");
                    for (X509Certificate certificate : certs) {
                        System.out.println(certificate);
                    }
                }
                
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    System.out.println("checkServerTrusted(authType=" + authType + ")");
                    for (X509Certificate certificate : certs) {
                        System.out.println(certificate);
                    }
                }
            }
        };
        
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        
        sslSocketFactory = sslContext.getSocketFactory();
    }
    
    @Override
    public Socket createSocket() throws IOException {
        return sslSocketFactory.createSocket();
    }
    
    @Override
    public Socket createSocket(InetAddress inetaddress, int i, InetAddress inetaddress1, int j) throws IOException {
        return sslSocketFactory.createSocket(inetaddress, i, inetaddress1, j);
    }
    
    @Override
    public Socket createSocket(InetAddress inetaddress, int i) throws IOException {
        return sslSocketFactory.createSocket(inetaddress, i);
    }
    
    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean flag) throws IOException {
        return sslSocketFactory.createSocket(socket, s, i, flag);
    }
    
    @Override
    public Socket createSocket(String s, int i, InetAddress inetaddress, int j) throws IOException, UnknownHostException {
        return sslSocketFactory.createSocket(s, i, inetaddress, j);
    }
    
    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        return sslSocketFactory.createSocket(s, i);
    }
    
    @Override
    public boolean equals(Object obj) {
        return sslSocketFactory.equals(obj);
    }
    
    @Override
    public String[] getDefaultCipherSuites() {
        return sslSocketFactory.getDefaultCipherSuites();
    }
    
    @Override
    public String[] getSupportedCipherSuites() {
        return sslSocketFactory.getSupportedCipherSuites();
    }
    
    @Override
    public int hashCode() {
        return sslSocketFactory.hashCode();
    }
    
    @Override
    public String toString() {
        return sslSocketFactory.toString();
    }
    
}
