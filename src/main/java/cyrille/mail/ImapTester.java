/*
 * Created on Aug 11, 2005
 */
package cyrille.mail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cyrille.stress.StressTestUtils;
import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/**
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class ImapTester extends TestCase {

    private static final Log log = LogFactory.getLog(ImapTester.class);

    private String IMAP_SERVER_HOST = "localhost";

    private int IMAP_SERVER_PORT = 3344;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ImapTester.class);
    }

    public void testLoadRunnerData() throws Exception {
        InputStream in = getClass().getResourceAsStream("msisdn_s3a_wap1.dat");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        final String password = "W4N40RaNG3";

        // skip first line
        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            String[] elements = StringUtils.split(line, ',');
            final String ommMailboxUser = "0" + elements[0].substring(2) + "@omm-myorange.dk";
            // final String externalMailbox1User = elements[1];
            // final String externalMailbox2User = elements[2];

            // OMM mailbox
            Runnable runnableOmmMailbox = new Runnable() {

                public void run() {
                    testOmmMailbox(ommMailboxUser, password);
                }
            };
            executorService.execute(runnableOmmMailbox);

            /*
             * // External mailbox Runnable runnableExternaMailbox1 = new Runnable() {
             * 
             * public void run() { testExternalMailbox(externalMailbox1User, password); } };
             * executorService.execute(runnableExternaMailbox1);
             * 
             * Runnable runnableExternalMailbox2 = new Runnable() {
             * 
             * public void run() { testExternalMailbox(externalMailbox2User, password); } };
             * executorService.execute(runnableExternalMailbox2);
             */
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }

    public void testExternalMailbox(String user, String password) {
        try {
            Session s = openSession();

            Store store = s.getStore();

            store.connect(this.IMAP_SERVER_HOST, this.IMAP_SERVER_PORT, user, password);

            Folder defaultFolder = store.getDefaultFolder();

            Folder folder = defaultFolder.getFolder("INBOX");
            int messageCount = folder.getMessageCount();
            if (messageCount == 0) {
                log.error("No message in external mailbox INBOX for " + user);
                StressTestUtils.incrementProgressBarFailure();
            } else {
                // log.debug(user + " INBOX(" + messageCount + ")");
                StressTestUtils.incrementProgressBarSuccess();
            }
        } catch (AuthenticationFailedException afe) {
            log.error("Authentication failed with user=" + user + ", password=" + password);
        } catch (Exception e) {
            log.error("Exception with user=" + user + ", password=" + password + " : " + e.toString(), e);
        }
    }

    public void testOmmMailbox(String user, String password) {
        StressTestUtils.incrementProgressBarSuccess();
        try {

            Session s = openSession();

            Store store = s.getStore();
            store.connect(this.IMAP_SERVER_HOST, this.IMAP_SERVER_PORT, user, password);

            Folder defaultFolder = store.getDefaultFolder();

            Folder inboxFolder = defaultFolder.getFolder("INBOX");
            if (inboxFolder.getMessageCount() == 0) {
                // log.debug("No message in INBOX for " + user);
            } else {
                log.warn("Unexpected " + inboxFolder.getMessageCount() + " messages in omm mailbox INBOX folder " + user);
            }
            Folder[] folders = inboxFolder.list();
            if (folders.length != 2) {
                log.warn("Unexpected number of external mailboxes (" + folders.length + ") for omm mailbox " + user);
            }
            for (Folder folder : folders) {
                Folder externalMailboxInbox = folder.getFolder("INBOX");

                if (externalMailboxInbox.getMessageCount() == 0) {
                    log.error("No message in external account " + folder.getName() + " for omm mailbox " + user);
                    StressTestUtils.incrementProgressBarFailure();
                }
            }
            store.close();
        } catch (AuthenticationFailedException afe) {
            log.error("Authentication failed with user=" + user + ", password=" + password);
        } catch (Exception e) {
            log.error("Exception with user=" + user + ", password=" + password + " : " + e.toString(), e);
        }
    }

    private Session openSession() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", this.IMAP_SERVER_HOST);
        props.setProperty("mail.imap.port", String.valueOf(this.IMAP_SERVER_PORT));

        Session s = Session.getDefaultInstance(props, null);
        return s;
    }

}
