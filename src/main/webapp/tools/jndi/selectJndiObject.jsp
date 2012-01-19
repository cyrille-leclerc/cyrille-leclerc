<form name="urlChooser" action="displayResponse.jsp" target="displayResponse">
	Provider URL : <input type="text" name="providerUrl" value="corbaloc::10.162.2.22:2813,:10.162.2.23:2814" size="100"><br>
	Object jndi path: <input type="text" name="jndiPath" value="cell/clusters/ENA1Cluster/ejb/com/osa/mdsp/csp/ena/smms/sms/enabler/ejb/SMSSenderEnabler" size="100"><br>
	Display initialContext.nameInNamespace <input type="checkbox" name="displayInitialContextNameInNamespace" value="true"><br>
	<input type="submit" value="test">
</form>
