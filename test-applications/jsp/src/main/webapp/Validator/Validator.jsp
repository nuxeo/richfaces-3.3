<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<f:subview id="validatorSubviewID">

	<rich:panel>
		<f:facet name="header">
			<h:outputText>Single input field with label and message. Validated by AJAX on every changing.</h:outputText>
		</f:facet>
		<h:outputLabel for="ltext" value="#{lengthBean.textDescription}" />
		<h:inputText id="ltext" value="#{lengthBean.text}">
			<rich:ajaxValidator event="onchange" />
		</h:inputText>
	</rich:panel>
	<h2>Input fields with label and message in the JSF dataTable. Each
	field validated by AJAX on "onchange" event</h2>
	<rich:graphValidator value="#{dataValidator}" id="graphValidatorID"
		binding="#{dataValidator.graphValidatorComponent}" summary="#{dataValidator.summary}"
		type="customGraphValidator">
		<rich:dataTable value="#{dataValidator.beans}" var="dataBean"
			id="table">
			<f:facet name="header">
				<h:outputText
					value="Validate values in the data table. Total sum for an all integer values validated for a value less then 20" />
			</f:facet>

			<h:column>
				<f:facet name="header">
					<h:outputText value="text field" />
				</f:facet>
				<h:outputLabel for="text" value="#{dataBean.textDescription}" />
				<h:inputText id="text" value="#{dataBean.text}">
					<rich:ajaxValidator event="onchange" />
				</h:inputText>
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="integer field" />
				</f:facet>
				<h:outputLabel for="intValue" value="#{dataBean.intDescription}" />
				<h:inputText id="intValue" value="#{dataBean.intValue}">
					<rich:ajaxValidator event="onchange" />
				</h:inputText>
			</h:column>
			<f:facet name="footer">
				<h:panelGroup>
					<h:outputText>in addition to fields validation, total sum for an all integer values validated for a value less then 20.</h:outputText>
					<a4j:commandButton value="Submit all fields"></a4j:commandButton>
				</h:panelGroup>
			</f:facet>
		</rich:dataTable>
	</rich:graphValidator>
	<br />

	<%--rich:graphValidator value="#{graphValidator}"--%>
	<h:panelGrid columns="2" border="2" id="panelGridID">

		<f:facet name="header">
			<h:outputText value="Ajax Validator test" />
		</f:facet>

		<h:outputLabel for="mailValue" value="Mail validation*:" />
		<h:panelGroup>
			<h:inputText value="#{validator.text}" id="mailValue">
				<rich:ajaxValidator binding="#{validator.ajaxValidatorComponent}"
					data="{param1:'param1',param2:'param2'}"
					disableDefault="#{validator.disableDefault}" event="onblur"
					eventsQueue="#{validator.eventsQueue}" focus="mailValue"
					id="ajaxValidatorID"
					ignoreDupResponses="#{validator.ignoreDupResponses}"
					limitToList="#{validator.limitToList}"
					onbeforedomupdate="#{event.onbeforedomupdate}"
					oncomplete="#{event.oncomplete}" onsubmit="#{event.onsubmit}"
					rendered="#{validator.rendered}"
					requestDelay="#{validator.requestDelay}"
					reRender="mailValue,booleanValue,assertTrue,eanValue,creditValue"
					status="mailValue" ajaxListener="#{validator.processAjax}"
					summary="#{validator.summary}" timeout="#{validator.timeout}">
					<a4j:support event="onblur" reRender="hiddenField" action="#"></a4j:support>
				</rich:ajaxValidator>
			</h:inputText>
			<rich:message for="mailValue" showDetail="true" showSummary="true" />
			<h:inputHidden value="#{validator.temp}" id="hiddenField"/>
		</h:panelGroup>

		<h:outputLabel for="booleanValue" value="AssertFalse validation:" />
		<h:panelGroup>
			<h:selectBooleanCheckbox value="#{validator.booleanValue}"
				id="booleanValue">
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:selectBooleanCheckbox>
			<rich:message for="booleanValue" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputLabel for="assertTrue" value="AssertTrue validation:" />
		<h:panelGroup>
			<h:selectBooleanCheckbox value="#{validator.assertTrue}"
				id="assertTrue">
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:selectBooleanCheckbox>
			<rich:message for="assertTrue" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputLabel for="dateValue" value="Future validation*:" />
		<h:panelGroup>
			<h:inputText value="#{validator.dateValue}" id="dateValue"
				converter="dateValueConverter">
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:inputText>
			<rich:message for="dateValue" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputLabel for="patternValue" value="Pattern(1234) validation:" />
		<h:panelGroup>
			<h:inputText value="#{validator.patternValue}" id="patternValue">
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:inputText>
			<rich:message for="patternValue" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputLabel for="eanValue" value="EAN validation:" />
		<h:panelGroup>
			<h:inputText value="#{validator.ean}" id="eanValue">
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:inputText>
			<rich:message for="eanValue" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputLabel for="creditValue" value="CreditCardNumber validation:" />
		<h:panelGroup>
			<h:inputText value="#{validator.creditValue}" id="creditValue">
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:inputText>
			<rich:message for="creditValue" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputLabel for="sizeValues" value="Size validation:" />
		<h:panelGroup>
			<h:selectOneMenu value="#{validator.sizeValue}">
				<f:selectItems id="sizeValues" value="#{validator.sizeValues}"></f:selectItems>
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:selectOneMenu>
			<rich:message for="sizeValues" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputLabel for="digit" value="Digits validation:(x.xxx)" />
		<h:panelGroup>
			<h:inputText value="#{validator.digit}" id="digit">
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:inputText>
			<rich:message for="digit" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputLabel for="range" value="Range validation(0-15):" />
		<h:panelGroup>
			<h:inputText value="#{validator.rangeValue}" id="range">
				<rich:ajaxValidator event="onchange"></rich:ajaxValidator>
			</h:inputText>
			<rich:message for="range" showDetail="true" showSummary="true" />
		</h:panelGroup>

		<h:outputText value="total length should be less than 50" />
		<a4j:commandButton value="validate all fields" reRender="panelGridID"></a4j:commandButton>

		<f:facet name="footer">
			<h:outputText
				value="*-total length &gt; 20,but &lt; 50 (graphValidator test)" />
		</f:facet>

	</h:panelGrid>
	<%--/rich:graphValidator--%>


</f:subview>