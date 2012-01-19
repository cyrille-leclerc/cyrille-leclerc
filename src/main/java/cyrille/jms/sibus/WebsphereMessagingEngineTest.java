/*
 * Created on Mar 8, 2007
 */
package cyrille.jms.sibus;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

public class WebsphereMessagingEngineTest extends TestCase {

    protected Queue queue;

    protected Topic topic;

    protected ConnectionFactory connectionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // System.setProperty("com.ibm.ws.sib.client.traceSetting",
        // "SIBTrm=all:SIBCommunications=all");

        Properties env = new Properties();
        env.put(Context.PROVIDER_URL, "iiop://localhost:2809");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
        env.put("java.naming.corba.orb", org.omg.CORBA.ORB.init((String[]) null, null));
        InitialContext ctx = new InitialContext(env);

        this.connectionFactory = (ConnectionFactory) ctx.lookup("jms/my-connection-factory");
        this.queue = (Queue) ctx.lookup("jms/my-queue");
        this.topic = (Topic) ctx.lookup("jms/my-topic");

    }

    public void testSendAndReceive() throws Exception {

        Connection connection = this.connectionFactory.createConnection();
        connection.start();
        {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            session.createConsumer(this.queue).setMessageListener(new MessageListener() {

                public void onMessage(Message message) {
                    System.err.println("Receive queue message " + message);

                }

            });

            session.createConsumer(this.topic).setMessageListener(new MessageListener() {

                public void onMessage(Message message) {
                    System.err.println("Receive topic message " + message);

                }

            });
        }
        {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            session.createProducer(this.queue).send(session.createTextMessage("hello queue"));
            session.createProducer(this.topic).send(session.createTextMessage("hello topic"));

            session.close();
        }
        Thread.sleep(3 * 1000);
        System.out.println("Send completed");
    }

    public void testReceive() throws Exception {

        Connection connection = this.connectionFactory.createConnection();
        connection.start();
        {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            session.createConsumer(this.queue).setMessageListener(new MessageListener() {

                public void onMessage(Message message) {
                    System.err.println("Receive queue message " + message);

                }

            });

        }
        {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            session.createConsumer(this.topic).setMessageListener(new MessageListener() {

                public void onMessage(Message message) {
                    System.err.println("Receive topic message " + message);

                }

            });
        }
        Thread.sleep(10 * 1000);
        System.out.println("Receive completed");
    }
}
