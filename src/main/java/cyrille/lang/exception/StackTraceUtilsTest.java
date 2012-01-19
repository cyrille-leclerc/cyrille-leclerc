/*
 * Created on Apr 20, 2005
 */
package cyrille.lang.exception;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class StackTraceUtilsTest extends TestCase {

    private String m_nestedStackTrace = "com.osa.mdsp.testing.NestableAssertionFailedError: type=EnablerGatewayIn,description=mspvp528,url=http://mspvp528_a:3200/wsgwsoaphttp1/soaphttpengine/urn:mdspwsgw%23ProxyService?enabler=MMSEnabler/V1.0/SenderSEI,httpHeaderHost=EnablerGatewayIn:3200,proxyHost=&lt;null&gt;,proxyPort=0 Unable to retrieve Enabler Gateway Client information [GTW_GC_TP_GEN]  : com.osa.mdsp.csp.fwk.error.MDSPCmnFwkException - 625/com.osa.mdsp.csp.fwk.asyncevent.AsyncEventDatabaseAccess/select TECID, EVENT_DATE, EVENT_TYPE, TARGET_RESOURCE from T_FWK_EVENT_BRDCST where EVENT_DATE&gt;=? order by EVENT_DATE asc/SQL error select TECID, EVENT_DATE, EVENT_TYPE, TARGET_RESOURCE from T_FWK_EVENT_BRDCST where EVENT_DATE&gt;=? order by EVENT_DATE asc;\r\n"
            + "    at com.osa.mdsp.testing.MultiFailureTestCase.failAndContinueTest(MultiFailureTestCaseOld.java:74);\r\n"
            + "    at com.osa.mdsp.testing.enablers.MMSSenderEnablerRemoteServiceTestCase.testMMSSenderEnablerSendMMS(MMSSenderEnablerRemoteServiceTestCase.java:44);\r\n"
            + "    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method);\r\n"
            + "    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:79);\r\n"
            + "    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java(Compiled Code));\r\n"
            + "    at com.osa.mdsp.testing.MultiFailureTestCase.run(MultiFailureTestCaseOld.java:109);\r\n"
            + "    at org.apache.commons.jelly.tags.ant.AntTag.doTag(AntTag.java:198);\r\n"
            + "    at org.apache.commons.jelly.impl.TagScript.run(TagScript.java:279);\r\n"
            + "    at org.apache.commons.jelly.impl.ScriptBlock.run(ScriptBlock.java:135);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag.runBodyTag(MavenGoalTag.java:79);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag$MavenGoalAction.performAction(MavenGoalTag.java:110);\r\n"
            + "    at com.werken.werkz.Goal.fire(Goal.java:639);\r\n"
            + "    at com.werken.werkz.Goal.attain(Goal.java:575);\r\n"
            + "    at com.werken.werkz.WerkzProject.attainGoal(WerkzProject.java:193);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenAttainGoalTag.doTag(MavenAttainGoalTag.java:127);\r\n"
            + "    at org.apache.commons.jelly.impl.TagScript.run(TagScript.java:279);\r\n"
            + "    at org.apache.commons.jelly.impl.ScriptBlock.run(ScriptBlock.java:135);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag.runBodyTag(MavenGoalTag.java:79);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag$MavenGoalAction.performAction(MavenGoalTag.java:110);\r\n"
            + "    at com.werken.werkz.Goal.fire(Goal.java:639);\r\n"
            + "    at com.werken.werkz.Goal.attain(Goal.java:575);\r\n"
            + "    at com.werken.werkz.WerkzProject.attainGoal(WerkzProject.java:193);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenAttainGoalTag.doTag(MavenAttainGoalTag.java:127);\r\n"
            + "    at org.apache.commons.jelly.impl.TagScript.run(TagScript.java:279);\r\n"
            + "    at org.apache.commons.jelly.impl.ScriptBlock.run(ScriptBlock.java:135);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag.runBodyTag(MavenGoalTag.java:79);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag$MavenGoalAction.performAction(MavenGoalTag.java:110);\r\n"
            + "    at com.werken.werkz.Goal.fire(Goal.java:639);\r\n"
            + "    at com.werken.werkz.Goal.attain(Goal.java:575);\r\n"
            + "    at org.apache.maven.plugin.PluginManager.attainGoals(PluginManager.java:671);\r\n"
            + "    at org.apache.maven.MavenSession.attainGoals(MavenSession.java:263);\r\n"
            + "    at org.apache.maven.cli.App.doMain(App.java:488);\r\n"
            + "    at org.apache.maven.cli.App.main(App.java:1239);\r\n"
            + "    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method);\r\n"
            + "    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:79);\r\n"
            + "    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:41);\r\n"
            + "    at com.werken.forehead.Forehead.run(Forehead.java:551);\r\n"
            + "    at com.werken.forehead.Forehead.main(Forehead.java:581);\r\n"
            + "Caused by: Unable to retrieve Enabler Gateway Client information [GTW_GC_TP_GEN]  : com.osa.mdsp.csp.fwk.error.MDSPCmnFwkException - 625/com.osa.mdsp.csp.fwk.asyncevent.AsyncEventDatabaseAccess/select TECID, EVENT_DATE, EVENT_TYPE, TARGET_RESOURCE from T_FWK_EVENT_BRDCST where EVENT_DATE&gt;=? order by EVENT_DATE asc/SQL error select TECID, EVENT_DATE, EVENT_TYPE, TARGET_RESOURCE from T_FWK_EVENT_BRDCST where EVENT_DATE&gt;=? order by EVENT_DATE asc;\r\n"
            + "    at org.apache.axis.message.SOAPFaultBuilder.createFault(SOAPFaultBuilder.java:221);\r\n"
            + "    at org.apache.axis.message.SOAPFaultBuilder.endElement(SOAPFaultBuilder.java:128);\r\n"
            + "    at org.apache.axis.encoding.DeserializationContext.endElement(DeserializationContext.java:1087);\r\n"
            + "    at org.apache.xerces.parsers.AbstractSAXParser.endElement(Unknown Source);\r\n"
            + "    at org.apache.xerces.impl.XMLNSDocumentScannerImpl.scanEndElement(Unknown Source);\r\n"
            + "    at org.apache.xerces.impl.XMLDocumentFragmentScannerImpl$FragmentContentDispatcher.dispatch(Unknown Source);\r\n"
            + "    at org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.scanDocument(Unknown Source);\r\n"
            + "    at org.apache.xerces.parsers.XML11Configuration.parse(Unknown Source);\r\n"
            + "    at org.apache.xerces.parsers.DTDConfiguration.parse(Unknown Source);\r\n"
            + "    at org.apache.xerces.parsers.XMLParser.parse(Unknown Source);\r\n"
            + "    at org.apache.xerces.parsers.AbstractSAXParser.parse(Unknown Source);\r\n"
            + "    at javax.xml.parsers.SAXParser.parse(Unknown Source);\r\n"
            + "    at org.apache.axis.encoding.DeserializationContext.parse(DeserializationContext.java:227);\r\n"
            + "    at org.apache.axis.SOAPPart.getAsSOAPEnvelope(SOAPPart.java:696);\r\n"
            + "    at org.apache.axis.Message.getSOAPEnvelope(Message.java:424);\r\n"
            + "    at org.apache.axis.handlers.soap.MustUnderstandChecker.invoke(MustUnderstandChecker.java:62);\r\n"
            + "    at org.apache.axis.client.AxisClient.invoke(AxisClient.java:206);\r\n"
            + "    at org.apache.axis.client.Call.invokeEngine(Call.java:2754);\r\n"
            + "    at org.apache.axis.client.Call.invoke(Call.java:2737);\r\n"
            + "    at org.apache.axis.client.Call.invoke(Call.java:2413);\r\n"
            + "    at org.apache.axis.client.Call.invoke(Call.java:2336);\r\n"
            + "    at org.apache.axis.client.Call.invoke(Call.java:1793);\r\n"
            + "    at com.osa.mdsp.csp.smms.mms.enabler.sei.MMSSenderEnablerRemoteSoapBindingStub.sendMMS(MMSSenderEnablerRemoteSoapBindingStub.java:262);\r\n"
            + "    at com.osa.mdsp.testing.enablers.MMSSenderEnablerRemoteServiceTestCase.testMMSSenderEnablerSendMMS(MMSSenderEnablerRemoteServiceTestCase.java:87);\r\n"
            + "    at com.osa.mdsp.testing.enablers.MMSSenderEnablerRemoteServiceTestCase.testMMSSenderEnablerSendMMS(MMSSenderEnablerRemoteServiceTestCase.java:41);\r\n"
            + "    ... 51 more";

    private String m_stackTrace = "com.osa.mdsp.testing.NestableAssertionFailedError: type=EnablerGatewayIn,description=mspvp528,url=http://mspvp528_a:3200/wsgwsoaphttp1/soaphttpengine/urn:mdspwsgw%23ProxyService?enabler=MMSEnabler/V1.0/SenderSEI,httpHeaderHost=EnablerGatewayIn:3200,proxyHost=&lt;null&gt;,proxyPort=0 Unable to retrieve Enabler Gateway Client information [GTW_GC_TP_GEN]  : com.osa.mdsp.csp.fwk.error.MDSPCmnFwkException - 625/com.osa.mdsp.csp.fwk.asyncevent.AsyncEventDatabaseAccess/select TECID, EVENT_DATE, EVENT_TYPE, TARGET_RESOURCE from T_FWK_EVENT_BRDCST where EVENT_DATE&gt;=? order by EVENT_DATE asc/SQL error select TECID, EVENT_DATE, EVENT_TYPE, TARGET_RESOURCE from T_FWK_EVENT_BRDCST where EVENT_DATE&gt;=? order by EVENT_DATE asc;\r\n"
            + "    at com.osa.mdsp.testing.MultiFailureTestCase.failAndContinueTest(MultiFailureTestCaseOld.java:74);\r\n"
            + "    at com.osa.mdsp.testing.enablers.MMSSenderEnablerRemoteServiceTestCase.testMMSSenderEnablerSendMMS(MMSSenderEnablerRemoteServiceTestCase.java:44);\r\n"
            + "    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method);\r\n"
            + "    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:79);\r\n"
            + "    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java(Compiled Code));\r\n"
            + "    at com.osa.mdsp.testing.MultiFailureTestCase.run(MultiFailureTestCaseOld.java:109);\r\n"
            + "    at org.apache.commons.jelly.tags.ant.AntTag.doTag(AntTag.java:198);\r\n"
            + "    at org.apache.commons.jelly.impl.TagScript.run(TagScript.java:279);\r\n"
            + "    at org.apache.commons.jelly.impl.ScriptBlock.run(ScriptBlock.java:135);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag.runBodyTag(MavenGoalTag.java:79);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag$MavenGoalAction.performAction(MavenGoalTag.java:110);\r\n"
            + "    at com.werken.werkz.Goal.fire(Goal.java:639);\r\n"
            + "    at com.werken.werkz.Goal.attain(Goal.java:575);\r\n"
            + "    at com.werken.werkz.WerkzProject.attainGoal(WerkzProject.java:193);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenAttainGoalTag.doTag(MavenAttainGoalTag.java:127);\r\n"
            + "    at org.apache.commons.jelly.impl.TagScript.run(TagScript.java:279);\r\n"
            + "    at org.apache.commons.jelly.impl.ScriptBlock.run(ScriptBlock.java:135);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag.runBodyTag(MavenGoalTag.java:79);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag$MavenGoalAction.performAction(MavenGoalTag.java:110);\r\n"
            + "    at com.werken.werkz.Goal.fire(Goal.java:639);\r\n"
            + "    at com.werken.werkz.Goal.attain(Goal.java:575);\r\n"
            + "    at com.werken.werkz.WerkzProject.attainGoal(WerkzProject.java:193);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenAttainGoalTag.doTag(MavenAttainGoalTag.java:127);\r\n"
            + "    at org.apache.commons.jelly.impl.TagScript.run(TagScript.java:279);\r\n"
            + "    at org.apache.commons.jelly.impl.ScriptBlock.run(ScriptBlock.java:135);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag.runBodyTag(MavenGoalTag.java:79);\r\n"
            + "    at org.apache.maven.jelly.tags.werkz.MavenGoalTag$MavenGoalAction.performAction(MavenGoalTag.java:110);\r\n"
            + "    at com.werken.werkz.Goal.fire(Goal.java:639);\r\n"
            + "    at com.werken.werkz.Goal.attain(Goal.java:575);\r\n"
            + "    at org.apache.maven.plugin.PluginManager.attainGoals(PluginManager.java:671);\r\n"
            + "    at org.apache.maven.MavenSession.attainGoals(MavenSession.java:263);\r\n"
            + "    at org.apache.maven.cli.App.doMain(App.java:488);\r\n"
            + "    at org.apache.maven.cli.App.main(App.java:1239);\r\n"
            + "    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method);\r\n"
            + "    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:79);\r\n"
            + "    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:41);\r\n"
            + "    at com.werken.forehead.Forehead.run(Forehead.java:551);\r\n"
            + "    at com.werken.forehead.Forehead.main(Forehead.java:581);\r\n";

    public static void main(String[] args) {
        junit.textui.TestRunner.run(StackTraceUtilsTest.class);
    }

    public void testSplitNestedStackTraces() {
        String[] stackTraces = StackTraceUtils.splitNestedStackTraces(this.m_nestedStackTrace);
        for (String nestedStackTrace : stackTraces) {
            System.out.println(nestedStackTrace);
        }
    }

    public void testTruncateStackTrace() {

        String searchedString = "at org.apache.commons.jelly.tags.ant.AntTag.doTag";
        String actual = StackTraceUtils.truncateStackTrace(this.m_stackTrace, searchedString);
        System.out.println(actual);
    }

    public void testTruncateNestedStackTrace() {

        String searchedString = "at org.apache.commons.jelly.tags.ant.AntTag.doTag";
        String actual = StackTraceUtils.truncateNestedStackTrace(this.m_nestedStackTrace, searchedString);
        System.out.println(actual);
    }

    public void testSplit() {
        String str = "value 1 separator value 2 separator value 3 separator value 4 separator value 5";
        String[] expected = new String[] { "value 1 ", " value 2 ", " value 3 ", " value 4 ", " value 5" };
        String[] actual = StackTraceUtils.split(str, "separator");
        assertEquals("result size", expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals("value " + i, expected[i], actual[i]);
        }
    }

    public void testSplitWithNoTrailingValue() {
        String str = "value 1 separator value 2 separator value 3 separator value 4 separator value 5separator";
        String[] expected = new String[] { "value 1 ", " value 2 ", " value 3 ", " value 4 ", " value 5" };
        String[] actual = StackTraceUtils.split(str, "separator");
        assertEquals("result size", expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals("value " + i, expected[i], actual[i]);
        }
    }

    public void testSplitWithNoSeparator() {
        String str = "value 1 ";
        String[] expected = new String[] { "value 1 " };
        String[] actual = StackTraceUtils.split(str, "separator");
        assertEquals("result size", expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals("value " + i, expected[i], actual[i]);
        }
    }

}
