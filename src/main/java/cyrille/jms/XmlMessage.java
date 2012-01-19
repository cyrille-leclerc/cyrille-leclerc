/*
 * Created on Apr 8, 2007
 */
package cyrille.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;

/**
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
public interface XmlMessage extends Message {

    void setSource(Source xmlSource) throws JMSException;

    Source getSource() throws JMSException;

    /**
     * 
     * @return
     * @see Transformer#getOutputProperty(String)
     */
    Properties getOutputProperties();

    /**
     * 
     * @param name
     * @return
     * @throws IllegalArgumentException
     * @see Transformer#getOutputProperty(String)
     */
    String getOutputProperty(String name) throws IllegalArgumentException;

    /**
     * 
     * @param oformat
     * @see Transformer#setOutputProperties(Properties)
     */
    void setOutputProperties(Properties oformat);

    /**
     * 
     * @param name
     * @param value
     * @throws IllegalArgumentException
     * @see Transformer#setOutputProperty(String, String)
     */
    void setOutputProperty(String name, String value) throws IllegalArgumentException;
}
