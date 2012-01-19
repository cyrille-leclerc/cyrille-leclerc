package cyrille.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

public class UrlEncoderTest {
    @Test
    public void testUrlEncodeSimple() throws Exception {
        String url = "http://my.target.com?myparam=" + URLEncoder.encode("#my-value#", "UTF-8");
        System.out.println("url\t" + url);
        System.out.println("encoded url\t" + url);
    }
    
    @Test
    public void testUrlEncode() throws Exception {
        String url = "https://cam.sfr.fr/cam/sso/deconnexion/deconnexion.jsp?TARGET="
                     + URLEncoder.encode("https://www.sfr.fr/cas/logout?url="
                                         + URLEncoder.encode("http://www.sfr.fr/?sfrintid=HD_Deconnexion ", "UTF-8"), "UTF-8");
        System.out.println(url);
    }
    
    @Test
    public void test() throws Exception {
        String url = "https://sso-neufbox.sfr.fr/sso/servlet/Check?"
                     + "appId=ABONNESNEUF&"
                     + "loginUrl=https%3A%2F%2Fmoncompte-neufbox.sfr.fr%2Fmoncompte-webapp%2Fconnexion%2FloginAction.action&"
                     + "forwardUrl=https%3A%2F%2Fmoncompte-neufbox.sfr.fr%2Fmoncompte-webapp%2Fmoncompte%2FgererOffre%2FgererOffre.action%3FidSso%3D%23%23SSOID%23%23";
        
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        System.out.println("Encoded url = " + encodedUrl);
    }
    
    @Test
    public void testDecode() throws Exception {
        String targetUrl = "https://sso-neufbox.sfr.fr/sso/servlet/Check?appId=ABONNESNEUF&loginUrl=https%3A%2F%2Fmoncompte-neufbox.sfr.fr%2Fmoncompte-webapp%2Fconnexion%2FloginAction.action&forwardUrl=https%3A%2F%2Fmoncompte-neufbox.sfr.fr%2Fmoncompte-webapp%2Fmoncompte%2FgererOffre%2FgererOffre.action%3FidSso%3D%23%23SSOID%23%23";
        String decodedTargetUrl = URLDecoder.decode(targetUrl, "UTF-8");
        System.out.println("Decoded url = " + decodedTargetUrl);
    }
    
    @Test
    public void testEncodeSsoIdTwice() throws Exception {
        String forwardUrl = "https://moncompte-neufbox.sfr.fr/moncompte-webapp/moncompte/gererOffre/gererOffre.action?idSso="
                            + URLEncoder.encode("##SSOID##", "UTF-8");
        System.out.println("forwardUrl\t" + forwardUrl);
        String ssoCheckUrl = "https://sso-neufbox.sfr.fr/sso/servlet/Check?" + "appId=ABONNESNEUF&" + "loginUrl="
                             + URLEncoder.encode("https://moncompte-neufbox.sfr.fr/moncompte-webapp/connexion/loginAction.action", "UTF-8")
                             + "&" + "forwardUrl=" + URLEncoder.encode(forwardUrl, "UTF-8");
        System.out.println("ssoCheckUrl\t" + ssoCheckUrl);
        
        System.out.println("Double encoded ssoId " + ssoCheckUrl);
        System.out.println("Reencoded Double encoded ssoId " + URLEncoder.encode(ssoCheckUrl, "UTF-8"));
    }
    
    @Test
    public void testDecodeChar() throws Exception {
        decode("%3A");
        decode("%3B");
        decode("%3C");
        decode("%3D");
    }
    
    private void decode(String encoded) throws UnsupportedEncodingException {
        String decoded = URLDecoder.decode(encoded, "UTF-8");
        System.out.println(encoded + "\t" + decoded);
    }
    
    @Test
    public void test20090410() throws Exception {
        String url = "https://sso-neufbox.sfr.fr/sso/servlet/Check?appId=ABONNESNEUF&loginUrl=https%3A%2F%2Fmoncompte-neufbox.sfr.fr%2Fmoncompte-webapp%2Fconnexion%2FloginAction.action&forwardUrl=https%3A%2F%2Fmoncompte-neufbox.sfr.fr%2Fmoncompte-webapp%2Fmoncompte%2FgererOffre%2FgererOffre.action%3FidSso%3D%23%23SSOID%23%23";
        String encodedUrl = URLEncoder.encode(url, "UTF-8");
        System.out.println("url\t" + url);
        System.out.println("encodedUrl\t" + encodedUrl);
    }
    @Test
    public void test20090414() throws Exception {
        String target = "https://sso-neufbox.sfr.fr/sso/servlet/Check?appId=ABONNESNEUF&loginUrl=https%3A%2F%2Fmoncompte-neufbox.sfr.fr%2Fmoncompte-webapp%2Fconnexion%2FloginAction.action&forwardUrl=https%3A%2F%2Fmoncompte-neufbox.sfr.fr%2Fmoncompte-webapp%2Fmoncompte%2FgererOffre%2FgererOffre.action%3FidSso%3D%23%23SSOID%23%23";
        String url = "https://cms.sfr.fr/cas/login?target=" + URLEncoder.encode(target, "UTF-8") + "&password=levallois&username=iplabeliplabel@sfr.fr";
        System.out.println(url);
    }
}
