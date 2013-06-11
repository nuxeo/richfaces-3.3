<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/panelmenu" prefix="pm"%>
<html>
<head>
	<title>RF-2192</title>
</head>
<body>
	<f:view>
		<h:form>
			<pm:panelMenu id="menu" expandSingle="true">
   				<pm:panelMenuGroup label="Group1">
   					<pm:panelMenuItem label="Item1" actionListener="#{menu.item1Clicked}"/>
   				</pm:panelMenuGroup>
   				<pm:panelMenuGroup label="Group2">
   					<pm:panelMenuItem label="Item2" actionListener="#{menu.item2Clicked}"/>
   				</pm:panelMenuGroup>
   				<pm:panelMenuGroup label="Group3">
   					<pm:panelMenuItem label="Item3" actionListener="#{menu.item3Clicked}"/>
   				</pm:panelMenuGroup>
			</pm:panelMenu>
		</h:form>
	</f:view>
</html>