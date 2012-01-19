/*
 * Created on Sep 13, 2003
 */
package cyrille.xml;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TransformerService {
    private Map templatesCache = new HashMap();

    private Templates getTemplate(URL xsl) throws TransformerConfigurationException, IOException {
        Templates templates = (Templates) this.templatesCache.get(xsl);
        if (this.templatesCache == null) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            templates = transformerFactory.newTemplates(new StreamSource(xsl.openStream()));
            this.templatesCache.put(xsl, templates);
        }
        return templates;
    }

    public void transform(Source source, URL xsl, Result result) throws Exception {
        Templates templates = getTemplate(xsl);
        Transformer aliceTransformer = templates.newTransformer();
        aliceTransformer.transform(source, result);
    }
}
