package cyrille.xml.xsl;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

/*
 * Copyright 2002-2006 the original author or authors.
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

/**
 * Test case of <a href="https://issues.apache.org/jira/browse/XALANJ-2244">XALANJ-2244 : xsl:sort does not work with nodeset parameters</a>.
 * 
 * {@link #testSortedForEachWithParameterizedNodeSet()} fails with Xalan-2.7.0. The workaround wrapping the parameterized nodeset in a
 * <code>nodeset</code> works as shown in {@link #testSortedForEachWithWorkAroundedParameterizedNodeSet()}.
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
public class XALANJ_2444_Test extends TestCase {

    protected String xml = "<root>\n" + "  <parent>\n" + "    <child>\n" + "      <sort-key>1</sort-key>\n"
            + "      <label>item-1</label>\n" + "    </child>\n" + "    <child>\n" + "      <sort-key>3</sort-key>\n"
            + "      <label>item-3</label>\n" + "    </child>\n" + "    <child>\n" + "      <sort-key>2</sort-key>\n"
            + "      <label>item-2</label>\n" + "    </child>\n" + "  </parent>\n" + "</root>";

    public void dump(String xml, String xsl, String generated) {
        System.out.println("===============================");
        System.out.println("XML");
        System.out.println();
        System.out.println(xml);
        System.out.println();
        System.out.println("===============================");
        System.out.println("XSL");
        System.out.println();
        System.out.println(xsl);
        System.out.println();
        System.out.println("===============================");
        System.out.println("GENERATED");
        System.out.println();
        System.out.println(generated);
        System.out.println();
    }

    /**
     * Directly using the template's parameter <code>$path</code> in the <code>select</code> attribute of the <code>xsl:for-each</code>
     * element fails as described in <a href="https://issues.apache.org/jira/browse/XALANJ-2244">XALANJ-2244 : xsl:sort does not work with
     * nodeset parameters</a>.
     */
    public void testSortedForEachWithParameterizedNodeSet() throws Exception {
        String xsl = "<xsl:stylesheet version='1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns='http://www.w3.org/TR/xhtml1/strict'>\n"
                + "  <xsl:template match='root'>\n"
                + "    <xsl:call-template name='test-sorted-for-each-parameterized'>\n"
                + "      <xsl:with-param name='path' select='parent/child' />\n"
                + "    </xsl:call-template>\n"
                + "  </xsl:template>\n"
                + "  \n"
                + "  <xsl:template name='test-sorted-for-each-parameterized'>\n"
                + "    <xsl:param name='path'/>\n"
                + "    <test-sorted-for-each-parameterized>\n"
                + "      <xsl:for-each select='$path'>\n"
                + " <xsl:sort select='sort-key' data-type='number' />\n"
                + "        <xsl:element name='{label}' />\n"
                + "      </xsl:for-each>\n" + "    </test-sorted-for-each-parameterized>\n" + "  </xsl:template>\n" + "</xsl:stylesheet>";

        String actual = transform(xml, xsl);
        String expected = "<test-sorted-for-each-parameterized xmlns=\"http://www.w3.org/TR/xhtml1/strict\"><item-1/><item-2/><item-3/></test-sorted-for-each-parameterized>";
        dump(xml, xsl, actual);
        assertEquals(expected, actual);

    }

    /**
     * Workaround using <code>nodeset()</code> function in the <code>select</code> attribute of the <code>for-each</code> element.
     * Looks like <code>&lt;xsl:for-each select='nodeset($path)' &gt; ... &lt;/xsl:for-each&gt;</code>.
     */
    public void testSortedForEachWithWorkAroundedParameterizedNodeSet() throws Exception {
        String xsl = "<xsl:stylesheet version='1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns='http://www.w3.org/TR/xhtml1/strict'>\n"
                + "  <xsl:template match='root'>\n"
                + "    <xsl:call-template name='test-sorted-for-each-parameterized-workaround'>\n"
                + "      <xsl:with-param name='path' select='parent/child' />\n"
                + "    </xsl:call-template>\n"
                + "  </xsl:template>\n"
                + "  \n"
                + "  <xsl:template name='test-sorted-for-each-parameterized-workaround'>\n"
                + "    <xsl:param name='path'/>\n"
                + "    <test-sorted-for-each-parameterized-workaround>\n"
                + "      <xsl:for-each select='nodeset($path)'>\n"
                + " <xsl:sort select='sort-key' data-type='number' />\n"
                + "        <xsl:element name='{label}' />\n"
                + "      </xsl:for-each>\n"
                + "    </test-sorted-for-each-parameterized-workaround>\n"
                + "  </xsl:template>\n"
                + "</xsl:stylesheet>";

        String actual = transform(xml, xsl);
        String expected = "<test-sorted-for-each-parameterized-workaround xmlns=\"http://www.w3.org/TR/xhtml1/strict\"><item-1/><item-2/><item-3/></test-sorted-for-each-parameterized-workaround>";
        dump(xml, xsl, actual);
        assertEquals(expected, actual);

    }

    /**
     * Nodeset used in the <code>select</code> attribute of the <code>xsl:for-each</code> is passed as an <code>xsl:variable</code> to
     * ensure <a href="https://issues.apache.org/jira/browse/XALANJ-860">XALANJ-860</a> is fixed and is not the same as XALANJ-2244.
     */
    public void testSortedForEachWithVariabilizedNodeSet() throws Exception {
        String xsl = "<xsl:stylesheet version='1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns='http://www.w3.org/TR/xhtml1/strict'>\n"
                + "  <xsl:template match='root'>\n"
                + "    <xsl:variable name='path' select='parent/child' />\n"
                + "    <test-sorted-for-each-variabilized>\n"
                + "      <xsl:for-each select='$path'>\n"
                + "        <xsl:sort select='sort-key' data-type='number' />\n"
                + "        <xsl:element name='{label}' />\n"
                + "      </xsl:for-each>\n" + "    </test-sorted-for-each-variabilized>\n" + "  </xsl:template>\n" + "</xsl:stylesheet>";

        String actual = transform(xml, xsl);
        String expected = "<test-sorted-for-each-variabilized xmlns=\"http://www.w3.org/TR/xhtml1/strict\"><item-1/><item-2/><item-3/></test-sorted-for-each-variabilized>";
        dump(xml, xsl, actual);
        assertEquals(expected, actual);
    }

    /**
     * Helper method to perform the XSLT transformation ensuring we use an XSLTC Transformer (relying on {@link org.apache.xalan.xsltc.trax.TransformerFactoryImpl}).
     */
    public String transform(String xml, String xsl) throws Exception {
        // force usage of Xalan trax implementation
        TransformerFactory transformerFactory = (TransformerFactory) Class.forName("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl").newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new StringReader(xsl)));
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        StringWriter result = new StringWriter();
        transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(result));

        return result.toString();
    }
}
