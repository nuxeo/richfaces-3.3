<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="iNSpinnerPropertySubviewID">
<h:commandButton value="add test" action="#{inputNumberSpinner.addHtmlInputNumberSpinner}"></h:commandButton>
	<h:panelGrid columns="2" cellpadding="10px" border="1">
		<h:outputText value="Max: "></h:outputText>
		<h:inputText value="#{inputNumberSpinner.max}">
			<a4j:support reRender="SpinnerID" event="onchange"></a4j:support>
		</h:inputText>

		<h:outputText value="Min: "></h:outputText>
		<h:inputText value="#{inputNumberSpinner.min}">
			<a4j:support reRender="SpinnerID" event="onchange"></a4j:support>
		</h:inputText>

		<h:outputText value="Step: "></h:outputText>
		<h:inputText value="#{inputNumberSpinner.step}">
			<a4j:support reRender="SpinnerID" event="onchange"></a4j:support>
		</h:inputText>

		<h:outputText value="Size: "></h:outputText>
		<h:inputText value="#{inputNumberSpinner.inputSize}">
			<a4j:support reRender="SpinnerID" event="onchange"></a4j:support>
		</h:inputText>

		<h:outputText value="Cycled:"></h:outputText>
		<h:selectBooleanCheckbox value="#{inputNumberSpinner.cycled}">
			<a4j:support event="onclick" reRender="SpinnerID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Manual Input:"></h:outputText>
		<h:selectBooleanCheckbox value="#{inputNumberSpinner.manualInput}">
			<a4j:support event="onclick" reRender="SpinnerID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Disabled:"></h:outputText>
		<h:selectBooleanCheckbox value="#{inputNumberSpinner.disabled}">
			<a4j:support event="onclick" reRender="SpinnerID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Rendered:"></h:outputText>
		<h:selectBooleanCheckbox value="#{inputNumberSpinner.rendered}"
			onclick="submit()">
		</h:selectBooleanCheckbox>
		<h:outputText value="Switch Styles:" />
		<h:commandButton action="#{inputNumberSpinner.doStyles}"
			value="#{inputNumberSpinner.btnLabel}" />
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getValue" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('SpinnerID').value}" />
		</rich:column>
	</h:panelGrid>
</f:subview>