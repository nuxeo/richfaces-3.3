<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dataTablePropertySubviewID">
		<h:commandButton value="add test" action="#{dataTable.addHtmlDataTable}"></h:commandButton>
	<h:panelGrid columns="2" style="top">
		<h:outputText value="Align:"></h:outputText>
		<h:selectOneMenu value="#{dataTable.align}">
			<f:selectItem itemLabel="center" itemValue="center" />
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="right " itemValue="right" />
			<a4j:support event="onclick" reRender="dataTableID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="Border: "></h:outputText>
		<h:inputText value="#{dataTable.border}">
			<a4j:support event="onchange" reRender="dataTableID"></a4j:support>
		</h:inputText>

		<h:outputText value="Columns Width: "></h:outputText>
		<h:inputText value="#{dataTable.columnsWidth}">
			<a4j:support event="onchange" reRender="dataTableID"></a4j:support>
		</h:inputText>

		<h:outputText value="Width: "></h:outputText>
		<h:inputText value="#{dataTable.width}">
			<a4j:support event="onchange" reRender="dataTableID"></a4j:support>
		</h:inputText>

		<h:outputText value="rendered:" />
		<h:selectBooleanCheckbox value="#{dataTable.rendered}"
			onclick="submit();" />

		<h:outputText value=" row 2 rendered" />
		<h:selectBooleanCheckbox value="#{dataTable.r2rendered}"
			onclick="submit();" />

		<h:outputText value="Selected Node: "></h:outputText>
		<h:outputText escape="false"
			value="#{treeDndBean.leftSelectedNodeTitle}" id="selectedNodeL" />

		<h:outputText value="Selected Node: "></h:outputText>
		<h:outputText escape="false"
			value="#{treeDndBean.rightSelectedNodeTitle}" id="selectedNodeR" />
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getRowCount" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('dataTableID').rowCount}" />
		</rich:column>
	</h:panelGrid>
</f:subview>