<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<html>
<head>
<title></title>

<style type="text/css">
.customHeader {
	color: #000000;
	background-image: url();
	background-color: #32CD32;
}
</style>

</head>

<body>
<f:view>
	<h:form id="state_form">
		<h:panelGrid columns="2">
			<h:outputText value="username" />
			<h:inputText value="#{state.bean.name}" />
			<h:outputText value="password" />
			<h:inputText value="#{state.bean.password}" />
			<h:outputText value="confirm" rendered="#{state.showConfirm}" />
			<h:inputText value="#{state.bean.confirmPassword}"
				rendered="#{state.showConfirm}" />
			<h:commandLink action="switch" value="#{state.link}" immediate="true"/>
			<h:panelGroup>
		<h:commandButton action="#{state.action.ok}" value="#{state.okBtn}" 
			 />
		<h:commandButton action="#{state.ok}" value="Action" />
			</h:panelGroup>
		</h:panelGrid>
		<h:messages></h:messages>
	</h:form>
</f:view>
</body>
</html>
