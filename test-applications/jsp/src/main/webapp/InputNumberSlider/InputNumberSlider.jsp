<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="inputNumberSliderSubviewID">


<h:inputText required="true" value="Delete me"/>
	<h:panelGrid columns="1">
		<rich:inputNumberSlider accesskey="i"
			binding="#{inputNumberSlider.htmlInputNumberSlider}" id="SliderId"
			barStyle="#{style.barStyle}"
			disabled="false"
			enableManualInput="false"
			handleSelectedClass="#{style.handleSelectedClass}"
			inputClass="#{style.inputClass}"
			inputPosition="#{inputNumberSlider.inputPosition}"
			showArrows="#{inputNumberSlider.showArrows}"
			orientation="#{inputNumberSlider.orientation}"
			inputStyle="#{style.inputStyle}" 
			tipStyle="#{style.tipStyle}"
			style="#{style.style}" 
			immediate="#{inputNumberSlider.immediate}"
			showToolTip="#{inputNumberSlider.showToolTip}"
			tabindex="#{inputNumberSlider.tabindex}"
			value="#{inputNumberSlider.value}"
			valueChangeListener="#{inputNumberSlider.handlerMethod}"
			required="#{inputNumberSlider.rendered}"
			requiredMessage="#{inputNumberSlider.requiredMessage}"
			inputSize="#{inputNumberSlider.inputSize}"
			maxValue="#{inputNumberSlider.maxValue}"
			minValue="#{inputNumberSlider.minValue}"
			rendered="#{inputNumberSlider.rendered}"
			showBoundaryValues="#{inputNumberSlider.showBoundaryValues}"
			step="#{inputNumberSlider.step}"
			showInput="#{inputNumberSlider.showInput}"
			width="#{inputNumberSlider.width}"
			height="#{inputNumberSlider.height}" 
			barClass="#{style.barClass}"
			tipClass="#{style.barClass}" 
			handleClass="#{style.handleStyle}" styleClass="#{style.tipStyle}"
			maxlength="#{inputNumberSlider.maxlength}" onblur="#{event.onblur}"
			onchange="#{event.onchange}" onclick="#{event.onclick}"
			ondblclick="#{event.ondblclick}" onerror="#{event.onerror}"
			onfocus="#{event.onfocus}" onmousedown="#{event.onmousedown}"
			onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
			onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"
			onselect="#{event.onselect}" onslide="#{event.onslide}"
			oninputclick="#{event.oninputclick}"
			oninputdblclick="#{event.oninputdblclick}"
			oninputkeydown="#{event.oninputkeydown}"
			oninputkeypress="#{event.oninputkeypress}"
			oninputkeyup="#{event.oninputkeyup}"
			oninputmousedown="#{event.oninputmousedown}"
			oninputmousemove="#{event.oninputmousemove}"
			oninputmouseout="#{event.oninputmouseout}"
			oninputmouseover="#{event.oninputmouseover}"
			oninputmouseup="#{event.oninputmouseup}">
		</rich:inputNumberSlider>
		<h:panelGroup>
			<a4j:commandButton value="valueChangeListener (show)"
				reRender="valueCLID" />
			<h:outputText id="valueCLID"
				value=" #{inputNumberSlider.valueChanged}" />
		</h:panelGroup>
	</h:panelGrid>
	<rich:spacer height="20px"></rich:spacer>

	<h:form>
		<rich:tabPanel switchType="client">
			<rich:tab label="First">
            Here is tab #1
            <rich:inputNumberSlider value="10" />
			</rich:tab>
			<rich:tab label="Second">
            Here is tab #2
            <rich:inputNumberSlider value="20" />
			</rich:tab>
		</rich:tabPanel>
		<h:commandButton action="submit" value="Submit" />
	</h:form>
</f:subview>