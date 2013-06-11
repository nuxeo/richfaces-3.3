<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:view>
	<html>
	<head>
	<title>Automator</title>
	<script type="text/javascript" src="javascripts/handlers.js"></script>
	<script type="text/javascript" src="javascripts/common.js"></script>
	</head>
	<body>
	<h:form id="mainForm">
		<div align="center"><h:panelGrid columns="2">
			<h:graphicImage value="/icons/logo.png" />
			<h:panelGroup>
				<span style="color: orange; font-size: 50; font-family: cursive">Automator</span>
			</h:panelGroup>
		</h:panelGrid></div>
		<div align="right"><h:panelGrid id="mainMenu" columns="3"
			border="1">
			<f:facet name="header">
				<h:outputText value="#{general.vers}" />
			</f:facet>
			<a4j:status id="a4jStatus" startText="WORK!" startStyle="color: red;"
				stopText="a4j:status" />
			<a4j:commandButton reRender="componentPage" value="reRender" />
			<a4j:commandLink reRender="componentPage" value="Return" immediate="true">
				<a4j:actionparam value="/main/componentsList.jsp"
					assignTo="#{general.componentPage}" />
			</a4j:commandLink>
		</h:panelGrid></div>
		<rich:spacer height="20"></rich:spacer>
		<h:panelGrid id="componentPage" columns="1">
			<jsp:include page="${general.componentPage}" />
		</h:panelGrid>
	</h:form>
	</body>
	</html>
</f:view>