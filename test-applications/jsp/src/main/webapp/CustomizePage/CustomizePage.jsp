<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>



<f:view>
	<html xmlns="http://www.w3.org/1999/xhtml">
	<body>

	<h:form id="formID">
		<rich:messages></rich:messages>
		<br />
		
		<rich:colorPicker id="cp" value="#{colorPicker.value}"
			validator="#{colorPicker.validate}"
			 colorMode="rgb"></rich:colorPicker>
		<br />
		<h:commandButton action="submit();" value="Submit"></h:commandButton>
		<br />
		<a4j:commandButton value="ReRender" reRender="formID"></a4j:commandButton>
		<br />
		<h:outputText value="validatorMessage: "></h:outputText>
		<br />
		<h:inputText value="#{colorPicker.validatorMessage}"></h:inputText>
		<br />
		<h:outputText value="value: #{colorPicker.value}"></h:outputText>

	</h:form>

	</body>
	</html>

</f:view>

