/*
 * Copyright 2008-2010 Xebia and the original author or authors.
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
package cyrille.xml.xsd;

import org.junit.Test;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.sun.xml.xsom.XSAnnotation;
import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.XSType;
import com.sun.xml.xsom.parser.XSOMParser;
import com.sun.xml.xsom.util.DomAnnotationParserFactory;

/**
 * usefull doc : http://www.w3.org/TR/2004/REC-xmlschema-1-20041028/components.gif
 * 
 * @author <a href="mailto:cyrille@cyrilleleclerc.com">Cyrille Le Clerc</a>
 */
public class XsomXsdParserSample {

    @Test
    public void test() throws Exception {
        XSOMParser parser = new XSOMParser();
        parser.setAnnotationParser(new DomAnnotationParserFactory());
        parser.parse("http://www.xebia.fr/schema/management/xebia-management-extras.xsd");
        XSSchemaSet xsSchemaSet = parser.getResult();
        XSSchema schema = xsSchemaSet.getSchema("http://www.xebia.fr/schema/xebia-management-extras");

        // dumpElementDeclration(schema.getElementDecl("dbcp-datasource"), "");

        dumpDbcpDatasource(schema.getElementDecl("dbcp-datasource"));

    }

    protected void dumpElementDeclration(XSElementDecl elementDeclaration, String indentation) {
        XSType type = elementDeclaration.getType();

        String documentation = getDocumentation(elementDeclaration);
        // TODO DISABLE
        documentation = StringUtils.replace(documentation, "\n", "<br/>");

        if (type.isComplexType()) {
            XSComplexType currentComplexType = type.asComplexType();

            // ATTRIBUTES
            String valueType = null;
            Boolean required = null;
            String defaultValue = null;
            XSAttributeUse valueAttributeUse = currentComplexType.getAttributeUse("", "value");
            if (valueAttributeUse != null) {
                XSAttributeDecl valueAttributeDeclaration = valueAttributeUse.getDecl();
                valueType = valueAttributeDeclaration.getType().getName();
                required = valueAttributeUse.isRequired();
                defaultValue = ObjectUtils.nullSafeToString(valueAttributeUse.getDefaultValue());
            }

            System.out.println(indentation + "|| *{{{" + elementDeclaration.getName() + "}}}* || " + valueType + " || " + required + " || "
                    + defaultValue + " || " + documentation + " ||");

            XSContentType currentContentType = currentComplexType.getContentType();
            XSParticle currentParticle = currentContentType.asParticle();
            if (currentParticle == null) {
                System.out.println("skip ");
            } else {
                XSTerm term = currentParticle.getTerm();

                if (term.isModelGroup()) {
                    XSModelGroup modelGroup = term.asModelGroup();

                    for (XSParticle particle : modelGroup.getChildren()) {

                        XSTerm particleTerm = particle.getTerm();

                        if (particleTerm.isElementDecl()) {
                            // xs:element inside complex type
                            dumpElementDeclration(particleTerm.asElementDecl(), indentation + "   ");
                        }
                    }
                }
            }
        } else {
            System.out.println(indentation + elementDeclaration.getName() + " " + documentation);
        }
    }

    /**
     * Dump
     * 
     * @param dataSourceElementDecl
     * @param indentation
     */
    private void dumpDbcpDatasource(XSElementDecl dataSourceElementDecl) {
        XSType dataSourceElementType = dataSourceElementDecl.getType();
        Assert.isTrue(dataSourceElementType.isComplexType());
        XSComplexType dataSourceComplexType = dataSourceElementType.asComplexType();
        XSContentType dataSourceContentType = dataSourceComplexType.getContentType();
        Assert.isTrue(dataSourceContentType instanceof XSParticle);
        XSParticle dataSourceParticle = dataSourceContentType.asParticle();
        XSTerm dataSourceTerm = dataSourceParticle.getTerm();
        Assert.isTrue(dataSourceTerm.isModelGroup());
        XSModelGroup dataSourceModelGroup = dataSourceTerm.asModelGroup();

        for (XSParticle configurationElementParticle : dataSourceModelGroup.getChildren()) {

            XSTerm configurationElementTerm = configurationElementParticle.getTerm();
            Assert.isTrue(configurationElementTerm.isElementDecl());
            XSElementDecl configurationElementDecl = configurationElementTerm.asElementDecl();
            XSType configurationElementType = configurationElementDecl.getType();
            Assert.isTrue(configurationElementType.isComplexType());
            XSComplexType configurationElementComplexType = configurationElementType.asComplexType();

            XSAttributeUse valueAttributeUse = configurationElementComplexType.getAttributeUse("", "value");
            Assert.notNull(valueAttributeUse, "'value' attribute not found for " + configurationElementDecl);
            XSAttributeDecl valueAttributeDeclaration = valueAttributeUse.getDecl();
            String valueType = valueAttributeDeclaration.getType().getName();
            boolean required = configurationElementParticle.getMinOccurs() == 1;
            String defaultValue = valueAttributeUse.getDefaultValue() == null ? "" : valueAttributeUse.getDefaultValue().toString();

            String documentation = getDocumentation(configurationElementDecl);
            documentation = documentation.trim();
            String[] splittedDocumentation = org.apache.commons.lang.StringUtils.split(documentation, "\n");
            splittedDocumentation = StringUtils.trimArrayElements(splittedDocumentation);
            documentation = StringUtils.arrayToDelimitedString(splittedDocumentation, "<br/>");

            System.out.println("|| *{{{" + configurationElementDecl.getName() + "}}}* || " + valueType + " || " + required + " || "
                    + defaultValue + " || " + documentation + " ||");
        }

    }

    private String getDocumentation(XSElementDecl elementDeclaration) {
        String documentation;
        XSAnnotation annotation = elementDeclaration.getAnnotation();
        Element annotationElement = (Element) annotation.getAnnotation();
        Element documentationElement = DomUtils.getChildElementByTagName(annotationElement, "documentation");
        if (documentationElement != null) {
            documentation = DomUtils.getTextValue(documentationElement);
        } else {
            documentation = null;
        }
        return documentation;
    }
}
