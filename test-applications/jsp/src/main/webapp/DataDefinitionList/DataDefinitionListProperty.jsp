<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dataDefinitionListPropertySubviewID">

	<h:commandButton value="add test" action="#{dataDefinitionList.addHtmlDataDefinitionList}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="title" />
		<h:inputText value="#{dataDefinitionList.title}">
			<a4j:support event="onchange" reRender="ddListID"></a4j:support>
		</h:inputText>

		<h:outputText value="first" />
		<h:inputText value="#{dataDefinitionList.first}">
			<a4j:support event="onchange" reRender="ddListID"></a4j:support>
		</h:inputText>

		<h:outputText value="rows" />
		<h:inputText value="#{dataDefinitionList.rows}">
			<a4j:support event="onchange" reRender="ddListID"></a4j:support>
		</h:inputText>

		<h:outputText value="dir" />
		<h:selectOneRadio value="#{dataDefinitionList.dir}">
			<f:selectItem itemValue="LTR" itemLabel="LTR" />
			<f:selectItem itemValue="RTL" itemLabel="RTL" />
			<a4j:support event="onchange" reRender="ddListID"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="rendered" />
		<h:selectBooleanCheckbox value="#{dataDefinitionList.rendered}"
			onchange="submit();" />
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getRowCount" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText
				value="#{rich:findComponent('ddListID').rowCount}" />
		</rich:column>
	</h:panelGrid>
</f:subview>