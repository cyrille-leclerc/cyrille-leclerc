/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cyrille.xml.xpath;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class XpathTest {
    
    /**
     * WARNING : {@link XPath} is NOT thread safe !
     * 
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        String expectedName = "toyota";
        String expectedColor = "black";
        String xml = "<car><name>" + expectedName + "</name><color>" + expectedColor + "</color></car>";
        Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        
        String actualName = xpath.evaluate("//car/name", document);
        String actualColor = xpath.evaluate("//car/color", document);
        assertEquals(expectedColor, actualColor);
        assertEquals(expectedName, actualName);
    }
}
