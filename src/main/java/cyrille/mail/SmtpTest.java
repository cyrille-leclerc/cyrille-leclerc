/*
 * 
 */
package cyrille.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

public class SmtpTest {

    @Test
    public void test() throws Exception {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);
        Transport transport = session.getTransport("smtp");
        transport.connect("localhost", null, null);

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setSubject("Test " + DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss"));

        mimeMessage.setText("Test " + DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss") + "\r\n"
                + "contact cyrille.leclerc@pobox.com");
        mimeMessage.setSender(new InternetAddress("cyrille.leclerc@pobox.com", "Cyrille Le Clerc"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("cleclerc@pobox.com"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("cyrille.leclerc@pobox.com"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("cyrille.leclerc@gmail.com"));

        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

    }
}
