/*
 * Created on May 15, 2007
 */
package cyrille.jms.sample;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBResult;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.JmsTemplate102;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.util.Assert;

public class JmsQueueSender {

    static class JaxbMessageConverter implements MessageConverter {

        protected JAXBContext jaxbContext;

        protected TransformerFactory transformerFactory;

        public JaxbMessageConverter(JAXBContext jaxbContext) {
            this.jaxbContext = jaxbContext;
            this.transformerFactory = TransformerFactory.newInstance();
        }

        public Object fromMessage(Message message) throws JMSException, MessageConversionException {
            try {
                Assert.isInstanceOf(TextMessage.class, message);
                TextMessage textMessage = (TextMessage) message;

                JAXBResult result = new JAXBResult(this.jaxbContext);

                Transformer transformer = this.transformerFactory.newTransformer();

                transformer.transform(new StreamSource(new StringReader(textMessage.getText())), result);

                return result.getResult();
            } catch (Exception e) {
                throw new RuntimeException("Exception unmarshalling message " + message, e);
            }
        }

        public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
            try {
                Source source = new JAXBSource(this.jaxbContext, object);
                Transformer transformer = this.transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                transformer.transform(source, new StreamResult(out));

                String text = out.toString("UTF-8");

                TextMessage textMessage = session.createTextMessage(text);

                // TODO enforce encoding on the message

                return textMessage;
            } catch (Exception e) {
                throw new RuntimeException("Exception marshalling object " + object, e);
            }

        }
    }

    private JmsTemplate jmsTemplate;

    private Queue queue;

    public void setConnectionFactory(ConnectionFactory cf) {
        this.jmsTemplate = new JmsTemplate102(cf, false);
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void simpleSend() {
        this.jmsTemplate.send(this.queue, new MessageCreator() {

            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello queue world");
            }
        });
    }

    public void sendWithConversion() {
        Object xmlSource = null;
        this.jmsTemplate.convertAndSend("testQueue", xmlSource, new MessagePostProcessor() {

            public Message postProcessMessage(Message message) throws JMSException {
                message.setIntProperty("AccountID", 1234);
                message.setJMSCorrelationID("123-00001");
                return message;
            }
        });
    }

}