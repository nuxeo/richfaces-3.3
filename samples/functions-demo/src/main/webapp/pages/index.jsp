<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/functions" prefix="fn" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			<f:subview id="view">
				<h:form id="myForm">
					<h:inputText id="input" value="abc" />
				</h:form>
			</f:subview>

			<h:outputText value="#{fn:clientId('input')}" />
			<br />
			<h:outputText value="#{fn:element('input')}" />
			<br />
			<h:outputText value="#{fn:component('input')}" />
			<br />
			<h:outputText value="#{fn:findComponent('input').value}" />
			<br />

			Roles:
			<h:outputText rendered="#{fn:isUserInRole('admin, user')}" value="admin/user" />
			<br />
			<h:outputText rendered="#{fn:isUserInRole('admin')}" value="admin" />
			<br />
			<h:outputText rendered="#{fn:isUserInRole('user')}" value="user" />
			<br />
			
			
		</f:view>
	</body>	
</html>  
