<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="text" encoding="iso-8859-1"/>

	<!-- Process all elements -->
	<xsl:template match="*">
		<xsl:call-template name="myTemplate" />
	</xsl:template>

	<xsl:template name = "myTemplate" >
		this is myTemplate
	</xsl:template>

</xsl:stylesheet>