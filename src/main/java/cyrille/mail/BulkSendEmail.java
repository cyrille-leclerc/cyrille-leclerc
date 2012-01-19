/*
 * Created on Mar 8, 2006
 */
package cyrille.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;
import cyrille.stress.StressTestUtils;

public class BulkSendEmail extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(BulkSendEmail.class);
    }

    public void testSendEmail() throws Exception {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        // session.setDebug( true );
        Transport transport = session.getTransport("smtp");
        transport.connect("10.173.35.9", null, null);

        for (int i = 0; i < 1000; i++) {
            StressTestUtils.incrementProgressBarSuccess();
            testSendMail("" + i, session, transport);
        }
    }

    public void testSendMail(String id, Session session, Transport transport) throws UnsupportedEncodingException, MessagingException {

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setText("MDSP Project test email. In case of Problem, call Cyrille Le Clerc 06 61 33 60 86");
        mimeMessage.setSubject("MDSP Project test email " + id);
        mimeMessage.setSender(new InternetAddress("cleclerc@pobox.com", "Cyrille Le Clerc"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("0608110313@orange.fr"));

        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
    }
}
