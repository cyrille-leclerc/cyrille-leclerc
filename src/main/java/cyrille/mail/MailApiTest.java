/*
 * Created on Sep 25, 2003
 */
package cyrille.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class MailApiTest extends TestCase {

    /**
     * Constructor for MailApiTest.
     * 
     * @param name
     */
    public MailApiTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(MailApiTest.class);
    }

    public void testSendMail() throws UnsupportedEncodingException, MessagingException {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setText("This is my text");
        mimeMessage.setSubject("the subject");
        mimeMessage.setSender(new InternetAddress("cleclerc@pobox.com", "Cyrille Le Clerc"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("c.leclerc@fr.vuitton.com"));

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.vuitton.lvmh", null, null);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
    }

    public void testSendMailWithAttachment() throws UnsupportedEncodingException, MessagingException {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setText("This is my text");
        mimeMessage.setSubject("the subject");
        mimeMessage.setSender(new InternetAddress("cleclerc@pobox.com", "Cyrille Le Clerc"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("c.leclerc@fr.vuitton.com"));

        Multipart multipart = new MimeMultipart();
        mimeMessage.setContent(multipart);

        BodyPart bodyPart = new MimeBodyPart();
        multipart.addBodyPart(bodyPart);
        bodyPart.setText("this is the text");

        URL url = getClass().getResource("deliveryOrder.pdf");
        DataSource dataSource = new FileDataSource(url.getFile());
        BodyPart bodyPart2 = new MimeBodyPart();
        multipart.addBodyPart(bodyPart2);
        bodyPart2.setDataHandler(new DataHandler(dataSource));
        bodyPart2.setFileName("deliveryOrder.pdf");

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.vuitton.lvmh", null, null);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
    }

    public void testSendMailWithAttachmentFromStream() throws MessagingException, IOException {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setText("This is my text");
        mimeMessage.setSubject("the subject");
        mimeMessage.setSender(new InternetAddress("cleclerc@pobox.com", "Cyrille Le Clerc"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("c.leclerc@fr.vuitton.com"));

        Multipart multipart = new MimeMultipart();
        mimeMessage.setContent(multipart);

        BodyPart bodyPart = new MimeBodyPart();
        multipart.addBodyPart(bodyPart);
        bodyPart.setText("this is the text");

        DataSource dataSource = new StreamDataSource(getClass().getResourceAsStream("deliveryOrder.pdf"), "dsName");
        BodyPart bodyPart2 = new MimeBodyPart();
        multipart.addBodyPart(bodyPart2);
        bodyPart2.setDataHandler(new DataHandler(dataSource));
        bodyPart2.setFileName("deliveryOrder.pdf");

        mimeMessage.saveChanges();

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.vuitton.lvmh", null, null);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
    }

    public void testSendEncodedMail() throws UnsupportedEncodingException, MessagingException {

        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        // big5 for chinese, shift-jis for japanese, euc-kr for korean,
        // iso-8859-1 for western alphabet,
        String encoding = "UTF-8";
        mimeMessage.setText("This is my text", encoding);
        mimeMessage.setSubject("the subject", encoding);
        mimeMessage.setSender(new InternetAddress("cleclerc@pobox.com", "Cyrille Le Clerc"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("cleclerc@pobox.com"));

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.vuitton.lvmh", null, null);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
    }

    private class StreamDataSource implements DataSource {

        private String m_contentType = "application/octet-stream";

        private String m_name;

        private byte[] m_data;

        public StreamDataSource(InputStream in, String name) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) >= 0) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            this.m_data = byteArrayOutputStream.toByteArray();
            this.m_name = name;
        }

        /**
         * @see javax.activation.DataSource#getContentType()
         */
        public String getContentType() {
            return this.m_contentType;
        }

        /**
         * @see javax.activation.DataSource#getInputStream()
         */
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(this.m_data);
        }

        /**
         * @see javax.activation.DataSource#getName()
         */
        public String getName() {
            return this.m_name;
        }

        /**
         * @see javax.activation.DataSource#getOutputStream()
         */
        public OutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

    }
}
