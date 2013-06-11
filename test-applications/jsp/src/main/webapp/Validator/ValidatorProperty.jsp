<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<f:subview id="validatorPropertySubviewID">
	<a4j:commandButton value="add(ajaxValidator) test"
		action="#{validator.add}"></a4j:commandButton>
	&nbsp;
	<a4j:commandButton value="add(graphValidator) test"
		action="#{dataValidator.add}"></a4j:commandButton>
	<br />
	<br />
	<h:panelGrid columns="2" style="display:block; vertical-align:top;">
		<h:outputText value="Ajax Validator test"
			style="align:center; font-weight:bold;" />
		<h:outputText value="Graph Validator test"
			style="align:center; font-weight:bold;" />
		<h:panelGrid columns="2" border="2">

			<h:outputText value="ajaxSingle" />
			<h:selectBooleanCheckbox value="#{validator.ajaxSingle}"
				onchange="submit();" />

			<h:commandButton actionListener="#{validator.checkBinding}"
				value="Binding" />
			<h:outputText value="#{validator.bindLabel}" />

			<h:outputText value="disableDefault" />
			<h:selectBooleanCheckbox value="#{validator.disableDefault}"
				onchange="submit();" />

			<h:outputText value="eventsQueue" />
			<h:inputText value="#{validator.eventsQueue}">
				<a4j:support event="onchange" reRender="ajaxValidatorID"></a4j:support>
			</h:inputText>

			<h:outputText value="ignoreDupResponces" />
			<h:selectBooleanCheckbox value="#{validator.ignoreDupResponses}"
				onchange="submit();" />

			<h:outputText value="immediate" />
			<h:selectBooleanCheckbox value="#{validator.immediate}"
				onchange="submit();" />

			<h:outputText value="limitToList" />
			<h:selectBooleanCheckbox value="#{validator.limitToList}"
				onchange="submit();" />

			<h:outputText value="renderRegionOnly" />
			<h:selectBooleanCheckbox value="#{validator.renderRegionOnly}"
				onchange="submit();" />

			<h:outputText value="requestDelay" />
			<h:inputText value="#{validator.requestDelay}">
				<a4j:support event="onchange" reRender="ajaxValidatorID"></a4j:support>
			</h:inputText>

			<h:outputText value="selfRendered" />
			<h:selectBooleanCheckbox value="#{validator.selfRendered}"
				onchange="submit();" />

			<h:outputText value="submitted" />
			<h:selectBooleanCheckbox value="#{validator.submitted}"
				onchange="submit();" />

			<h:outputText value="summary" />
			<h:inputText value="#{validator.summary}">
				<a4j:support event="onchange" reRender="ajaxValidatorID"></a4j:support>
			</h:inputText>

			<h:outputText value="timeout" />
			<h:inputText value="#{validator.timeout}">
				<a4j:support event="onchange" reRender="ajaxValidatorID"></a4j:support>
			</h:inputText>
		</h:panelGrid>


		<h:panelGrid columns="2" border="2" style="float:top;">
			<h:commandButton actionListener="#{dataValidator.checkBinding}"
				value="Binding" />
			<h:outputText value="#{dataValidator.bindLabel}" />

			<h:outputText value="summary" />
			<h:inputText value="#{dataValidator.summary}">
				<a4j:support event="onchange" reRender="graphValidatorID"></a4j:support>
			</h:inputText>			
		</h:panelGrid>
	</h:panelGrid>

</f:subview>