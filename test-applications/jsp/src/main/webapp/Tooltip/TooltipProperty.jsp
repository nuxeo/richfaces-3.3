<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="toolTipStraightforwardSubviewID">
	<h:commandButton value="add test" action="#{tooltip.addHtmlToolTip}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="Text:"></h:outputText>
		<h:inputText value="#{tooltip.value}">
			<a4j:support event="onchange" reRender="tooltipID" />
		</h:inputText>

		<h:outputText value="Delay"></h:outputText>
		<h:inputText value="#{tooltip.delay}">
			<a4j:support event="onchange" reRender="tooltipID" />
		</h:inputText>

		<h:outputText value="showDelay"></h:outputText>
		<h:inputText value="#{tooltip.showDelay}">
			<a4j:support event="onchange" reRender="tooltipID" />
		</h:inputText>

		<h:outputText value="hideDelay"></h:outputText>
		<h:inputText value="#{tooltip.hideDelay}">
			<a4j:support event="onchange" reRender="tooltipID" />
		</h:inputText>

		<h:outputText value="event:" />
		<h:selectOneMenu value="#{tooltip.event}" onchange="submit();">
			<f:selectItem itemLabel="onmouseover" itemValue="onmouseover" />
			<f:selectItem itemLabel="onclick" itemValue="onclick" />
			<f:selectItem itemLabel="onmouseup" itemValue="onmouseup" />
		</h:selectOneMenu>

		<h:outputText value="Layout:"></h:outputText>
		<h:selectOneRadio value="#{tooltip.layout}">
			<f:selectItem itemLabel="inline" itemValue="inline" />
			<f:selectItem itemLabel="block" itemValue="block" />
			<a4j:support event="onclick" reRender="tooltipID" />
		</h:selectOneRadio>

		<h:outputText value="Mode:"></h:outputText>
		<h:selectOneRadio value="#{tooltip.mode}">
			<f:selectItem itemLabel="client" itemValue="client" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
			<a4j:support event="onclick" reRender="tooltipID" />
		</h:selectOneRadio>

		<h:outputText value="Horizontal offset:"></h:outputText>
		<h:inputText value="#{tooltip.horizontalOffset}">
			<a4j:support event="onchange" reRender="tooltipID" />
		</h:inputText>

		<h:outputText value="Vertical offset:"></h:outputText>
		<h:inputText value="#{tooltip.verticalOffset}">
			<a4j:support event="onchange" reRender="tooltipID" />
		</h:inputText>

		<h:outputText value="Follow mouse:"></h:outputText>
		<h:selectBooleanCheckbox value="#{tooltip.followMouse}">
			<a4j:support event="onclick" reRender="tooltipID" />
		</h:selectBooleanCheckbox>

		<h:outputText value="Direction:" />
		<h:selectOneRadio value="#{tooltip.direction}">
			<f:selectItem itemLabel="top-right" itemValue="top-right" />
			<f:selectItem itemLabel="top-left" itemValue="top-left" />
			<f:selectItem itemLabel="bottom-right" itemValue="bottom-right" />
			<f:selectItem itemLabel="bottom-left" itemValue="bottom-left" />
			<a4j:support event="onclick" reRender="tooltipID" />
		</h:selectOneRadio>

		<h:outputText value="Style:"></h:outputText>
		<h:selectOneRadio value="#{tooltip.style}">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="border:3px; font:bold 14px;"
				itemValue="border:3px; font:bold 14px;" />
			<f:selectItem
				itemLabel="border:green 3px solid; background-color:yellow;"
				itemValue="border:green 3px solid; background-color:yellow;" />
			<f:selectItem
				itemLabel="border:green 2px solid; font-family:monospace;"
				itemValue="border:green 2px solid; font-family:monospace;" />
			<a4j:support event="onclick" reRender="tooltipID" />
		</h:selectOneRadio>

		<h:outputText value="Disambled:"></h:outputText>
		<h:selectBooleanCheckbox value="#{tooltip.disabled}">
			<a4j:support event="onclick" reRender="tooltipID" />
		</h:selectBooleanCheckbox>

		<h:outputText value="Rendered:"></h:outputText>
		<h:selectBooleanCheckbox value="#{tooltip.rendered}">
			<a4j:support event="onclick" reRender="tooltipID" />
		</h:selectBooleanCheckbox>
	</h:panelGrid>	
</f:subview>