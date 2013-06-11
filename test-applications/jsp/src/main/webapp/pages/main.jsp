<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/styles.css" type="text/css" />

 
</head>
<body>
<f:view>
	<h:form id="mainFormID">
		<rich:panel>
			<f:verbatim>
				<div align="center"><font size="4" color="#191970">RichFaces Test Application</font></div>
			</f:verbatim>
		</rich:panel>
		<rich:spacer></rich:spacer>
		<rich:panel>
			<h:panelGrid columns="2" cellpadding="5px" cellspacing="5px">
				<h:commandLink value="RichFaces" action="RichFaces"></h:commandLink>
				<h:commandLink value="Div" action="TestDiv"></h:commandLink>
				<h:commandLink value="Select" action="Map" disabled="true"></h:commandLink>
				<h:commandLink value="Customize page" action="CustomizePage"></h:commandLink>
				<a href="/jsp/Authentication/index.jsf">Authentication</a>
			</h:panelGrid>
		</rich:panel>	
		<rich:spacer></rich:spacer>
		<rich:panel>
			<h:outputText style="align:right;" value="RichFaces: #{a4j.version}" />
		</rich:panel>
	</h:form>
</f:view>
</body>
</html>
