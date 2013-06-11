<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
               xmlns:javaee="http://java.sun.com/JSP/TagLibraryDescriptor"
               version="1.0" exclude-result-prefixes="javaee">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>
     <xsl:param name="lang" />
     <xsl:param name="title" />
     <xsl:param name="separator" />
    
    <xsl:template match="javaee:table | table">
				<xsl:call-template name="tag" />
    </xsl:template>

	<xsl:template name="tag">
                <xsl:copy-of select="./*"/>
	</xsl:template>
</xsl:transform>