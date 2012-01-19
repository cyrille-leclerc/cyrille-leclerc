package cyrille.jms.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import junit.framework.TestCase;

import org.apache.activemq.ActiveMQConnectionFactory;

public class BasicTest extends TestCase {

    public void test() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false&broker.useJmx=false");

        // cher
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // cher
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // cher
        Destination queue = session.createQueue("default");

        // moyen cher
        MessageProducer messageProducer = session.createProducer(queue);

        Message message = session.createTextMessage("hello world");

        messageProducer.send(message);
    }
}
