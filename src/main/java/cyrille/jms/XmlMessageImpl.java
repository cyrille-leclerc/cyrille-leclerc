/*
 * Created on Apr 9, 2007
 */
package cyrille.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class XmlMessageImpl extends MessageImpl implements XmlMessage {

    TextMessage textMessage;

    Properties outputProperties = new Properties();

    public XmlMessageImpl(TextMessage textMessage) throws TransformerConfigurationException, TransformerFactoryConfigurationError {
        super(textMessage);
        this.textMessage = textMessage;
    }

    public Properties getOutputProperties() {
        return this.outputProperties;
    }

    public String getOutputProperty(String name) throws IllegalArgumentException {
        return this.outputProperties.getProperty(name);
    }

    public Source getSource() throws JMSException {
        return null;// TODO
    }

    public void setOutputProperties(Properties oformat) {
        this.outputProperties = oformat;

    }

    public void setOutputProperty(String name, String value) throws IllegalArgumentException {
        this.outputProperties.put(name, value);

    }

    public void setSource(Source xmlSource) throws JMSException {

    }
}
