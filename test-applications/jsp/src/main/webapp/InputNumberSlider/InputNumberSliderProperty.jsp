<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="inputNumberSliderPropertySubviewID">
	<h:commandButton value="add test" action="#{inputNumberSlider.addHtmlInputNumberSlider}"></h:commandButton>

	<h:panelGrid columns="2" cellspacing="10px" border="1">
		<h:outputText value="inputPosition"></h:outputText>
		<h:selectOneRadio value="#{inputNumberSlider.inputPosition}">
			<f:selectItem itemLabel="right" itemValue="right" />
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="top" itemValue="top" />
			<f:selectItem itemLabel="bottom" itemValue="bottom" />
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="Orientation:"></h:outputText>
		<h:selectOneRadio value="#{inputNumberSlider.orientation}" required="false">
			<f:selectItem itemLabel="vertical" itemValue="vertical" />
			<f:selectItem itemLabel="horizontal" itemValue="horizontal" />
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectOneRadio>
		
		<h:outputText value="Show Arrows:"></h:outputText>
		<h:selectBooleanCheckbox value="#{inputNumberSlider.showArrows}" required="false">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Width:"></h:outputText>
		<h:inputText value="#{inputNumberSlider.width}" title="in px">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="Height:"></h:outputText>
		<h:inputText value="#{inputNumberSlider.height}" title="in px">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="Max Value:"></h:outputText>
		<h:inputText value="#{inputNumberSlider.maxValue}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="Min Value:"></h:outputText>
		<h:inputText value="#{inputNumberSlider.minValue}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="Input Size:"></h:outputText>
		<h:inputText value="#{inputNumberSlider.inputSize}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="Input MaxLength:"></h:outputText>
		<h:inputText value="#{inputNumberSlider.maxlength}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="Step:"></h:outputText>
		<h:inputText value="#{inputNumberSlider.step}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="tabindex" />
		<h:inputText value="#{inputNumberSlider.tabindex}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="Disabled:"></h:outputText>
		<h:selectBooleanCheckbox value="#{inputNumberSlider.disabled}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Rendered:"></h:outputText>
		<h:selectBooleanCheckbox value="#{inputNumberSlider.rendered}"
			onclick="submit()">
		</h:selectBooleanCheckbox>

		<h:outputText value="Manual Input:"></h:outputText>
		<h:selectBooleanCheckbox
			value="#{inputNumberSlider.enableManualInput}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Boundary Values:"></h:outputText>
		<h:selectBooleanCheckbox
			value="#{inputNumberSlider.showBoundaryValues}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Show Input:"></h:outputText>
		<h:selectBooleanCheckbox value="#{inputNumberSlider.showInput}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="showToolTip" />
		<h:selectBooleanCheckbox value="#{inputNumberSlider.showToolTip}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="required" />
		<h:selectBooleanCheckbox value="#{inputNumberSlider.required}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="requiredMessage" />
		<h:inputText value="#{inputNumberSlider.requiredMessage}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:inputText>

		<h:outputText value="immediate" />
		<h:selectBooleanCheckbox value="#{inputNumberSlider.immediate}">
			<a4j:support event="onchange" reRender="SliderId"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Switch Styles" />
		<a4j:commandButton id="slBtn" value="#{inputNumberSlider.btnLabel}"
			action="#{inputNumberSlider.doStyles}" reRender="SliderId,slBtn">
		</a4j:commandButton>
	</h:panelGrid>
	<br />
	<br />

</f:subview>