/*
 * Created on Aug 11, 2005
 */
package cyrille.mail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.FetchProfile.Item;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImapTest extends TestCase {

    private static final Log log = LogFactory.getLog(ImapTest.class);

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ImapTest.class);
    }

    public void test() throws Exception {
        InputStream in = getClass().getResourceAsStream("emails.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        // 33617068928@myorange.dk Authentication Failure
        String password = "W4N40RaNG3";

        String msisdn;
        while ((msisdn = reader.readLine()) != null) {
            msisdn = msisdn.trim();
            if (msisdn.startsWith("#") || msisdn.length() == 0) {
                log.debug("skip comment " + msisdn);
            } else {

                String user = msisdn;

                // "33617068928@myorange.dk";
                try {
                    test(user, password);
                } catch (AuthenticationFailedException afe) {
                    log.error("Authentication failed with user=" + user + ", password=" + password);
                } catch (Exception e) {
                    log.error("Exception with user=" + user + ", password=" + password + " : " + e.toString(), e);
                }
            }
        }
    }

    public void test(String user, String password) throws MessagingException {
        String hostname = "localhost";
        int port = 3344;
        String protocol = "imap";

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", hostname);
        props.setProperty("mail.imap.port", String.valueOf(port));

        Session s = Session.getInstance(props, null);

        Store store = s.getStore(protocol);

        log.debug("Connect " + user + " at" + hostname + ":" + port + " ...");
        store.connect(hostname, port, user, password);

        Folder defaultFolder = store.getDefaultFolder();

        Folder[] folders = new Folder[] { defaultFolder.getFolder("INBOX") };
        for (Folder folder : folders) {
            dumpFolder(folder, "");
        }
    }

    public void dumpFolder(Folder folder, String offset) throws MessagingException {
        log.debug(offset + "" + folder.getName() + "(" + folder.getMessageCount() + ") /");

        // SUB FOLDERS
        Folder[] folders = folder.list("%");
        for (Folder subFolder : folders) {
            dumpFolder(subFolder, "\t" + offset);
        }

        // EMAILS
        folder.open(Folder.READ_WRITE);
        Message[] messages = folder.getMessages();

        FetchProfile fetchProfile = new FetchProfile();
        fetchProfile.add(Item.ENVELOPE);
        fetchProfile.add(Item.FLAGS);
        folder.fetch(messages, fetchProfile);

        for (Message message : messages) {
            String line = offset + "\t\"" + message.getSubject() + "\"\t";
            Address[] addresses = message.getFrom();
            for (Address address : addresses) {
                line += address.toString() + " ";
            }
            log.debug(line);
        }
        folder.close(false);
    }
}
