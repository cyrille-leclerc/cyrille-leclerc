/*
 * Created on Mar 11, 2007
 */
package cyrille.jms.activemq;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import junit.framework.TestCase;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
public class ActiveMqTest extends TestCase {

    Connection connection;

    ConnectionFactory connectionFactory;

    private void dumpMessageHeaders(Message message) throws JMSException {
        System.out.println("> dumpMessageHeaders");
        Enumeration<?> propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            Object value = message.getObjectProperty(name);
            System.out.println(name + "=" + value);
        }
        System.out.println("< dumpMessageHeaders");
    }

    private void sendBinaryMessage(String charset) throws JMSException, UnsupportedEncodingException {
        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("default");

        MessageProducer messageProducer = session.createProducer(queue);

        BytesMessage bytesMessage = session.createBytesMessage();
        String messageAsString = "Hello world " + charset + " יטא as bytes";
        bytesMessage.writeBytes(messageAsString.getBytes(charset));

        dumpMessageHeaders(bytesMessage);

        messageProducer.send(bytesMessage);

        session.close();
    }

    public void testSendInTemporaryQueue() throws Exception {
        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createTemporaryQueue();
        MessageProducer messageProducer = session.createProducer(queue);

        try {
            TextMessage textMessage = session.createTextMessage("Hello temporary queue");

            messageProducer.send(textMessage);
            dumpMessageHeaders(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
            if (e.getLinkedException() != null) {
                e.getLinkedException().printStackTrace();
            }
            throw e;
        }

        session.close();
    }

    public void testSendManyInTemporaryQueue() throws Exception {
        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createTemporaryQueue();
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 0; i < 10000; i++) {
            try {
                TextMessage textMessage = session.createTextMessage("Hello temporary queue");

                messageProducer.send(textMessage);
                dumpMessageHeaders(textMessage);
                // Thread.sleep(1000);
            } catch (JMSException e) {
                e.printStackTrace();
                if (e.getLinkedException() != null) {
                    e.getLinkedException().printStackTrace();
                }
                throw e;
            }
        }
        session.close();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        ((ActiveMQConnectionFactory) connectionFactory).setBrokerURL("vm://localhost?broker.persistent=false&broker.useJmx=false");

        this.connection = connectionFactory.createConnection();
        this.connection.start();
        ConnectionMetaData connectionMetaData = this.connection.getMetaData();

        System.out.println("> connectionMetaData");
        System.out.println("properties");
        Enumeration<String> jmsxPropertyNames = connectionMetaData.getJMSXPropertyNames();
        while (jmsxPropertyNames.hasMoreElements()) {
            String propertyName = jmsxPropertyNames.nextElement();
            System.out.println(propertyName);
        }
        System.out.println("< connectionMetaData");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        this.connection.close();
    }

    public void testBrowse() throws Exception {
        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("default");

        QueueBrowser queueBrowser = session.createBrowser(queue);

        Enumeration enumeration = queueBrowser.getEnumeration();
        while (enumeration.hasMoreElements()) {
            Message message = (Message) enumeration.nextElement();
            String msg;
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                msg = textMessage.toString();
            } else {
                msg = message.toString();
            }
            System.out.println("browse " + msg);
        }

    }

    public void testReceive() throws Exception {
        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("default");

        MessageConsumer messageConsumer = session.createConsumer(queue, null);

        Message message;
        while ((message = messageConsumer.receive(1000)) != null) {
            System.out.println("receive " + message);
        }

    }

    public void testSendAndReceive() throws Exception {
        MessageConsumer messageConsumer;
        {

            Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("default");

            messageConsumer = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE).createConsumer(queue, null);
            MessageListener sysoutListener = new MessageListener() {

                public void onMessage(Message message) {
                    System.out.println("testSendAndReceive.receive");
                    System.out.println(message);
                }

            };
            messageConsumer.setMessageListener(sysoutListener);
        }

        {
            Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("default");
            MessageProducer messageProducer = session.createProducer(queue);

            TextMessage textMessage = session.createTextMessage("Hello world with headers");
            textMessage.setJMSMessageID("my-message-id-1");
            textMessage.setJMSCorrelationID("my-correlation-id-1");
            textMessage.setJMSType("my-type");
            textMessage.setStringProperty("mystringproperty", "my-value");

            messageProducer.send(textMessage);

        }
        Thread.sleep(2 * 1000);
        messageConsumer.close();
    }

    public void testSendTextMessage() throws Exception {
        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("default");

        MessageProducer messageProducer = session.createProducer(queue);

        TextMessage textMessage = session.createTextMessage("Hello world JMS Message");

        messageProducer.send(textMessage);

        dumpMessageHeaders(textMessage);

        session.close();

    }

    public void testSendTextMessageWithProperties() throws Exception {
        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("default");
        MessageProducer messageProducer = session.createProducer(queue);

        TextMessage textMessage = session.createTextMessage("Hello world with headers");
        textMessage.setJMSMessageID("my-message-id-1");
        textMessage.setJMSCorrelationID("my-correlation-id-1");
        textMessage.setJMSType("my-type");
        textMessage.setStringProperty("mystringproperty", "my-value");

        messageProducer.send(textMessage);

        dumpMessageHeaders(textMessage);

        session.close();
    }
}
