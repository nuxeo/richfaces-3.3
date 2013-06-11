<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="datascrollerPropertySubviewID">

	<h:commandButton value="add test" action="#{dataScroller.addHtmlDatascroller}"></h:commandButton>
	<h:commandButton action="#{dataScroller.CutArray}" value="CutArray" />
	<h:commandButton action="#{dataScroller.RestoreArray}"
		value="RestoreArray" />

	<h:panelGrid columns="2">
		<h:outputText value="lastPageMode: " />
		<h:selectOneRadio value="#{dataScroller.lastPageMode}">
			<f:selectItem itemLabel="full" itemValue="full" />
			<f:selectItem itemLabel="short" itemValue="short" />
			<a4j:support event="onclick" reRender="dataTableId"></a4j:support>
		</h:selectOneRadio>
	
		<h:outputText value="maxPages" />
		<h:inputText value="#{dataScroller.maxPages}">
			<a4j:support event="onchange" reRender="dataTableId"></a4j:support>
		</h:inputText>
		
		<h:outputText value="page" />
		<h:inputText value="#{dataScroller.page}">
			<a4j:support event="onchange" reRender="dataTableId"></a4j:support>
		</h:inputText>		

		<h:outputText value="Rendered:" />
		<h:selectBooleanCheckbox value="#{dataScroller.render}"
			onclick="submit();" />

		<h:outputText value="renderIfSinglePage:" />
		<h:selectBooleanCheckbox value="#{dataScroller.renderIfSinglePage}"
			onclick="submit();" />

		<h:outputText value="limitToList:" />
		<h:selectBooleanCheckbox value="#{dataScroller.limitToList}"
			onclick="submit();" />

		<h:outputText value="fastControls" />
		<h:selectOneRadio value="#{dataScroller.fastControls}">
			<f:selectItem itemLabel="auto" itemValue="auto" />
			<f:selectItem itemLabel="show" itemValue="show" />
			<f:selectItem itemLabel="hide" itemValue="hide" />
			<a4j:support event="onclick" reRender="dataTableId"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="align" />
		<h:selectOneRadio value="#{dataScroller.align}">
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="right" itemValue="right" />
			<f:selectItem itemLabel="center" itemValue="center" />
			<a4j:support event="onclick" reRender="dataTableId"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="ajaxSingle" />
		<h:selectBooleanCheckbox value="#{dataScroller.ajaxSingle}"></h:selectBooleanCheckbox>

		<h:outputText value="boundaryControls" />
		<h:selectOneRadio value="#{dataScroller.boundaryControls}">
			<f:selectItem itemLabel="auto" itemValue="auto" />
			<f:selectItem itemLabel="show" itemValue="show" />
			<f:selectItem itemLabel="hide" itemValue="hide" />
			<a4j:support event="onclick" reRender="dataTableId"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="fastStep"></h:outputText>
		<h:inputText value="#{dataScroller.fastStep}" />

		<h:outputText value="value"></h:outputText>
		<h:outputText value="#{dataScroller.value}"></h:outputText>

		<h:outputText value="stepControls"></h:outputText>
		<h:selectOneRadio value="#{dataScroller.stepControls}">
			<f:selectItem itemLabel="auto" itemValue="auto" />
			<f:selectItem itemLabel="show" itemValue="show" />
			<f:selectItem itemLabel="hide" itemValue="hide" />
			<a4j:support event="onclick" reRender="dataTableId,dsID"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="sortMode"></h:outputText>
		<h:selectOneRadio value="#{dataScroller.sortMode}">
			<f:selectItem itemLabel="single" itemValue="single" />
			<f:selectItem itemLabel="multi" itemValue="multi" />
			<a4j:support event="onchange" reRender="dataTableId,dsID"></a4j:support>
		</h:selectOneRadio>
		
		<h:outputText value="selfSorted"></h:outputText>
		<h:selectBooleanCheckbox value="#{dataScroller.selfSorted}">
			<a4j:support event="onchange" reRender="dataTableId,dsID"></a4j:support>
		</h:selectBooleanCheckbox>
	</h:panelGrid>	
</f:subview>