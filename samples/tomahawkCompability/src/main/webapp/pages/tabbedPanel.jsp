<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<html>
<head>
<title>A4J form sample page</title>
</head>
<body>
<h1>Ajax submitted form with Tomahawk components</h1>
<f:view>
	<a4j:form id="ajaxform-with-defis" ajaxSubmit="true" reRender="tabs,counter">
		<h:panelGrid columns="2" border="1">
			<h:commandButton action="#{bean.inc}" value="Increment" />
			<h:commandLink value="Decrement" action="#{bean.dec}"></h:commandLink>
			<h:outputText value="Counter:" />
			<h:outputText id="counter" value="#{bean.counter}" />
		</h:panelGrid>
		<h:panelGroup id="tabs">
		<t:panelTabbedPane serverSideTabSwitch="true">
			<t:panelTab label="A">
			<h:inputText id="a" value="First" />
			<h:outputLabel value="First tab input" for="a"></h:outputLabel>
			</t:panelTab>
			<t:panelTab label="B">
			<h:inputText id="b" value="First" />
			<h:outputLabel value="Second tab input" for="b"></h:outputLabel>
			</t:panelTab>
		</t:panelTabbedPane>
		</h:panelGroup>
	</a4j:form>
	<h2>Same form without user-provided ID</h2>
	<f:subview id="subview">
	<a4j:form ajaxSubmit="true" reRender="counterB" onsubmit="alert('onsubmit');">
		<h:panelGrid columns="2" border="1">
			<h:commandButton action="#{beanB.inc}" value="Increment" />
			<h:commandLink value="Decrement" action="#{beanB.dec}"></h:commandLink>
			<h:outputText value="Counter:" />
			<h:outputText id="counterB" value="#{beanB.counter}" />
		</h:panelGrid>
		</a4j:form>
	</f:subview>
	<a4j:log hotkey="M"/>
</f:view>
</body>
</html>
