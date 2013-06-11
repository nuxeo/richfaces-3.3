<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="inplaceInputPropertySubviewID">
	<h:commandButton value="add test"
		action="#{inplaceInput.addHtmlInplaceInput}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="maxlength: "></h:outputText>
		<h:inputText value="#{inplaceInput.maxLength}" onchange="submit();">
		</h:inputText>

		<h:outputText value="value"></h:outputText>
		<h:outputText id="inplaceInputValueID" value="#{inplaceInput.value}">
		</h:outputText>

		<h:outputText value="defaultLabel"></h:outputText>
		<h:inputText value="#{inplaceInput.defaultLabel}" onchange="submit();">
		</h:inputText>

		<h:outputText value="inputWidth"></h:outputText>
		<h:inputText value="#{inplaceInput.inputWidth}" onchange="submit();">
		</h:inputText>

		<h:outputText value="maxInputWidth"></h:outputText>
		<h:inputText value="#{inplaceInput.maxInputWidth}"
			onchange="submit();">
		</h:inputText>

		<h:outputText value="minInputWidth"></h:outputText>
		<h:inputText value="#{inplaceInput.minInputWidth}"
			onchange="submit();">
		</h:inputText>

		<h:outputText value="tabindex"></h:outputText>
		<h:inputText value="#{inplaceInput.tabindex}" onchange="submit();">
		</h:inputText>

		<h:outputText value="editEvent"></h:outputText>
		<h:inputText value="#{inplaceInput.editEvent}" onchange="submit();">
		</h:inputText>

		<h:outputText value="controlsVerticalPosition"></h:outputText>
		<h:selectOneRadio value="#{inplaceInput.controlsVerticalPosition}"
			onchange="submit();">
			<f:selectItem itemLabel="top" itemValue="top" />
			<f:selectItem itemLabel="bottom" itemValue="bottom" />
			<f:selectItem itemLabel="center" itemValue="center" />
		</h:selectOneRadio>

		<h:outputText value="controlsHorizontalPosition"></h:outputText>
		<h:selectOneRadio value="#{inplaceInput.controlsHorizontalPosition}"
			onchange="submit();">
			<f:selectItem itemLabel="right" itemValue="right" />
			<f:selectItem itemLabel="center" itemValue="center" />
			<f:selectItem itemLabel="left" itemValue="left" />
		</h:selectOneRadio>

		<h:outputText value="selectOnEdit"></h:outputText>
		<h:selectBooleanCheckbox value="#{inplaceInput.selectOnEdit}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="showControls"></h:outputText>
		<h:selectBooleanCheckbox value="#{inplaceInput.showControls}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="rendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{inplaceInput.rendered}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="immediate"></h:outputText>
		<h:selectBooleanCheckbox value="#{inplaceInput.immediate}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="required"></h:outputText>
		<h:selectBooleanCheckbox value="#{inplaceInput.required}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="requiredMessage"></h:outputText>
		<h:inputText value="#{inplaceInput.requiredMessage}"
			onchange="submit();">
		</h:inputText>

		<h:commandButton actionListener="#{inplaceInput.checkBinding}"
			value="Binding"></h:commandButton>
		<h:outputText value="#{inplaceInput.bindLabel}"></h:outputText>

		<h:outputText value="layout"></h:outputText>
		<h:selectOneRadio value="#{inplaceInput.layout}" onchange="submit();">
			<f:selectItem itemLabel="inline" itemValue="inline" />
			<f:selectItem itemLabel="block" itemValue="block" />
		</h:selectOneRadio>

	</h:panelGrid>

	<a4j:commandLink
		onclick="$('formID:inplaceInputSubviewID:inplaceInputId').component.edit()"
		value="edit"></a4j:commandLink>
	<br />
	<a4j:commandLink
		onclick="$('formID:inplaceInputSubviewID:inplaceInputId').component.save()"
		value="save"></a4j:commandLink>
	<br />
	<a4j:commandLink
		onclick="$('formID:inplaceInputSubviewID:inplaceInputId').component.cancel()"
		value="cancel"></a4j:commandLink>
	<br />
	<a4j:commandLink
		onclick="alert($('formID:inplaceInputSubviewID:inplaceInputId').component.getValue())"
		value="getValue"></a4j:commandLink>
	<br />
	<a4j:commandLink
		onclick="$('formID:inplaceInputSubviewID:inplaceInputId').component.setValue('setValue')"
		value="setValuel"></a4j:commandLink>
	<br />
	<f:verbatim>
		<h:outputText value="Component Control test"
			style="FONT-WEIGHT: bold;"></h:outputText>
		<br />
		<a href="#" id="setValueID">setValue('testValue:
		~!@#$%^&*()_+=-[]{}"|;/')</a>
		<br />
	</f:verbatim>
	<br />
	<rich:componentControl attachTo="setValueID" event="onclick"
		for="inplaceInputId" operation="setValue">
		<f:param name="value" value="testValue: ~!@#$%^&*()_+=-[]{}|;/>/" />
	</rich:componentControl>
	<br />
	<%--
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getValue" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('inplaceInputId').value}" />
		</rich:column>
	</h:panelGrid>
	--%>
</f:subview>