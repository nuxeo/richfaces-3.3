<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="orderingListPropertySubviewID">
<h:commandButton value="add test" action="#{orderingList.addHtmlOrderingList}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="Enter quantity of lines" />
		<h:panelGroup>
			<h:inputText value="#{orderingList.lenght}" />
			<a4j:commandButton action="#{orderingList.addNewItem}"
				reRender="orderingListID" value="ok" />
		</h:panelGroup>

		<h:outputText value="controlsType" />
		<h:selectOneRadio value="#{orderingList.controlsType}">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="button" itemValue="button" />
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="captionLabel" />
		<h:inputText value="#{orderingList.captionLabel}">
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:inputText>

		<h:outputText value="listHeight:" />
		<h:inputText value="#{orderingList.listHeight}">
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:inputText>

		<h:outputText value="listWidth" />
		<h:inputText value="#{orderingList.listWidth}">
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:inputText>

		<h:outputText value="controlsVerticalAlign" />
		<h:selectOneRadio value="#{orderingList.controlsVerticalAlign}">
			<f:selectItem itemLabel="top" itemValue="top" />
			<f:selectItem itemLabel="center" itemValue="center" />
			<f:selectItem itemLabel="bottom" itemValue="bottom" />
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="controlsHorizontalAlign" />
		<h:selectOneRadio value="#{orderingList.controlsHorizontalAlign}">
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="right" itemValue="right" />
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="upControlLabel" />
		<h:inputText value="#{orderingList.upControlLabel}">
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:inputText>

		<h:outputText value="bottomControlLabel" />
		<h:inputText value="#{orderingList.bottomControlLabel}">
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:inputText>

		<h:outputText value="topControlLabel" />
		<h:inputText value="#{orderingList.topControlLabel}">
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:inputText>

		<h:outputText value="downControlLabel" />
		<h:inputText value="#{orderingList.downControlLabel}">
			<a4j:support event="onchange" reRender="orderingListID"></a4j:support>
		</h:inputText>

		<h:outputText value="showButtonLabels" />
		<h:selectBooleanCheckbox value="#{orderingList.showButtonLabels}">
			<a4j:support event="onclick" reRender="orderingListID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="fastOrderControlsVisible" />
		<h:selectBooleanCheckbox
			value="#{orderingList.fastOrderControlsVisible}">
			<a4j:support event="onclick" reRender="orderingListID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="orderControlsVisible" />
		<h:selectBooleanCheckbox value="#{orderingList.orderControlsVisible}">
			<a4j:support event="onclick" reRender="orderingListID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="rendered" />
		<h:selectBooleanCheckbox value="#{orderingList.rendered}"
			onclick="submit();">
		</h:selectBooleanCheckbox>
	</h:panelGrid>
	
</f:subview>