
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="colorPickerPropertySubviewID">
	<h:commandButton value="add test"
		action="#{colorPicker.addColorPicker}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="colorMode" />
		<h:selectOneRadio value="#{colorPicker.colorMode}">
			<f:selectItem itemLabel="rgb" itemValue="rgb" />
			<f:selectItem itemLabel="hex" itemValue="hex" />
		</h:selectOneRadio>

		<h:outputText value="showEvent: "></h:outputText>
		<h:inputText value="#{colorPicker.showEvent}">
		</h:inputText>

		<h:outputText value="converterMessage: "></h:outputText>
		<h:inputText value="#{colorPicker.converterMessage}">
		</h:inputText>

		<h:outputText value="flat" />
		<h:selectBooleanCheckbox value="#{colorPicker.flat}">
		</h:selectBooleanCheckbox>

		<h:outputText value="immediate" />
		<h:selectBooleanCheckbox value="#{colorPicker.immediate}">
		</h:selectBooleanCheckbox>

		<h:outputText value="rendered" />
		<h:selectBooleanCheckbox value="#{colorPicker.rendered}">
		</h:selectBooleanCheckbox>

		<h:outputText value="required" />
		<h:selectBooleanCheckbox value="#{colorPicker.required}">
		</h:selectBooleanCheckbox>

		<h:outputText value="facets" />
		<h:selectBooleanCheckbox value="#{colorPicker.facets}">
		</h:selectBooleanCheckbox>

		<h:outputText value="requiredMessage: "></h:outputText>
		<h:inputText value="#{colorPicker.requiredMessage}">
		</h:inputText>

		<h:outputText value="validatorMessage: "></h:outputText>
		<h:inputText value="#{colorPicker.validatorMessage}">
		</h:inputText>

		<h:commandButton actionListener="#{colorPicker.checkBinding}"
			value="Binding">
			<a4j:support event="onclick" reRender="bindLabelID"></a4j:support>
		</h:commandButton>
		<h:outputText value="#{colorPicker.bindLabel}" id="bindLabelID" />

	</h:panelGrid>
</f:subview>