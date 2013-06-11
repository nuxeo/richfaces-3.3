<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="queuePropertySubviewID">
	<h:commandButton value="add test" action="#{queue.addQueue}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="disabled" />
		<h:selectBooleanCheckbox value="#{queue.disabled}" onclick="submit()" />

		<h:outputText value="ignoreDupResponses" />
		<h:selectBooleanCheckbox value="#{queue.ignoreDupResponses}"
			onclick="submit()" />		

		<h:outputText value="requestDelay" />
		<h:inputText value="#{queue.requestDelay}">
			<a4j:support event="onchange" reRender="formID"></a4j:support>
		</h:inputText>

		<h:outputText value="size" />
		<h:inputText value="#{queue.size}">
			<a4j:support event="onchange" reRender="formID"></a4j:support>
		</h:inputText>

		<h:outputText value="sizeExceededBehavior" />
		<h:selectOneRadio value="#{queue.sizeExceededBehavior}" onchange="submit();">
			<f:selectItems value="#{queue.behaviors}" />
		</h:selectOneRadio>

		<h:outputText value="timeout" />
		<h:inputText value="#{queue.timeout}" id="timeoutID">
			<a4j:support event="onchange" reRender="formID"></a4j:support>
		</h:inputText>

	</h:panelGrid>
</f:subview>