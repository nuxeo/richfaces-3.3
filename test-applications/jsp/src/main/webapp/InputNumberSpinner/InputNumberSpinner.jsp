<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="inputNumberSpinnerSubviewID">
		<h:messages></h:messages>

		<rich:inputNumberSpinner id="SpinnerID" binding="#{inputNumberSpinner.htmlInputNumberSpinner}" tabindex="#{inputNumberSpinner.tabindex}" cycled="#{inputNumberSpinner.cycled}"
			disabled="#{inputNumberSpinner.disabled}" maxValue="#{inputNumberSpinner.max}" minValue="#{inputNumberSpinner.min}"
			step="#{inputNumberSpinner.step}" rendered="#{inputNumberSpinner.rendered}" value="#{inputNumberSpinner.value}"
			inputClass="#{style.inputClass}" styleClass="#{style.styleClass}" inputStyle="#{style.inputStyle}" style="#{style.style}"
			enableManualInput="#{inputNumberSpinner.manualInput}" inputSize="#{inputNumberSpinner.inputSize}"
			valueChangeListener="#{inputNumberSpinner.valueChangeListener}"
			oninputmousedown="#{event.onmousedown}" onblur="#{event.onblur}"
			onchange="#{event.onchange}" onclick="#{event.onclick}" 
			ondblclick="#{event.ondblclick}" onerror="#{event.onerror}" 
			onfocus="#{event.onfocus}" onselect="#{event.onselect}" 
			oninputkeydown="#{event.onkeydown}" oninputkeypress="#{event.onkeypress}" 
			oninputkeyup="#{event.onkeyup}" oninputmousemove="#{event.onmousemove}" 
			oninputmouseout="#{event.onmouseout}" oninputmouseover="#{event.onmouseover}" 
			onmouseup="#{event.onmouseup}" ondownclick="#{event.ondownclick}" 
			onupclick="#{event.onupclick}"
			accesskey="y">
			</rich:inputNumberSpinner>
			
			<h:panelGroup>
				<a4j:commandButton value="valueChangeListener (show)" reRender="valueCLID" />
				<h:outputText id="valueCLID" value=" #{inputNumberSpinner.valueChangeListener}" />
			</h:panelGroup>
		<rich:spacer height="20px"></rich:spacer>
</f:subview>
