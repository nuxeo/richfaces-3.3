<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="comboBoxSubview">
	<div>The &lt;rich:comboBox&gt; is a component, that provides
	editable combo box element on a page.</div>
	<rich:spacer height="30"></rich:spacer>

	<h:panelGrid id="comboBoxGrid" columns="1">
		<h:messages id="mess" style="color: red" />
		<rich:comboBox id="comboBox" binding="#{comboBoxGeneral.comboBox}"
			align="#{comboBoxGeneral.align}"
			immediate="#{comboBoxGeneral.immediate}"
			value="#{comboBoxGeneral.value}"
			validator="#{comboBoxGeneral.validate}"
			required="#{comboBoxGeneral.required}"
			validatorMessage="#{comboBoxGeneral.validatorMessage}"
			converter="#{comboBoxGeneral.convert}"
			converterMessage="#{comboBoxGeneral.converterMessage}"
			onblur="callOnblur('comboBox')"
			onmouseover="callOnmouseover('comboBox')"
			onclick="callOnclick('comboBox')" onchange="callOnchange('comboBox')"
			ondblclick="callOndblclick('comboBox')"
			onfocus="callOnfocus('comboBox')"
			onkeydown="callOnkeydown('comboBox')"
			onkeypress="callOnkeypress('comboBox')"
			onkeyup="callOnkeyup('comboBox')"
			onlistcall="callOnlistcall('comboBox')"
			onmousedown="callOnmousedown('comboBox')"
			onmousemove="callOnmousemove('comboBox')"
			onmouseout="callOnmouseout('comboBox')"
			onmouseup="callOnmouseup('comboBox')"
			onselect="callOnselect('comboBox')"
			valueChangeListener="#{comboBoxGeneral.valueChangeListener}">
			
			<f:selectItems value="#{comboBoxGeneral.selectItems}" />
			<f:selectItem itemValue="ValidatorCheck" itemLabel="Gosha" />
			<f:selectItem itemValue="ConverterCheck" itemLabel="Gosha" />
		</rich:comboBox>
		
		<h:inputHidden id="hiddenValidatorInput"
			value="#{comboBoxGeneral.validatorMessageTest}" />
		<h:inputHidden id="hiddenConverterInput"
			value="#{comboBoxGeneral.converterMessageTest}" />
	</h:panelGrid>
	<rich:spacer height="30"></rich:spacer>

	<h:panelGrid columns="3" border="1">
		<f:facet name="header">
			<h:outputText value="Results of testing" />
		</f:facet>
		<a4j:commandButton value="testGeneralAttrs" onmousedown="checkValidatorMessage()"
			onmouseup="checkConverterMessage()"
			actionListener="#{comboBoxGeneral.testGeneralAttributes}"
			reRender="generalResult, comboBoxGrid"></a4j:commandButton>
		<a4j:commandButton value="testHandlers"
			actionListener="#{comboBoxHandlers.testHandlers}"
			reRender="handlersResult, comboBoxGrid"></a4j:commandButton>
		<a4j:commandButton value="testStyles"
			actionListener="#{comboBoxStyles.testStyles}"
			reRender="stylesResult, comboBoxGrid"></a4j:commandButton>
		<h:panelGroup>
			<h:panelGrid id="generalResult"
				binding="#{comboBoxGeneral.panelGrid}" columns="2">
			</h:panelGrid>
		</h:panelGroup>
		<h:panelGroup>
			<h:panelGrid id="handlersResult"
				binding="#{comboBoxHandlers.panelGrid}" columns="2">
			</h:panelGrid>
		</h:panelGroup>
		<h:panelGroup>
			<h:panelGrid id="stylesResult" binding="#{comboBoxStyles.panelGrid}"
				columns="2">
			</h:panelGrid>
		</h:panelGroup>
		<f:facet name="footer">
			<h:outputText value="See results above..." />
		</f:facet>
	</h:panelGrid>
</f:subview>
