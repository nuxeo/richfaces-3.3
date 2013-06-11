<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dataGridPropertySubviewID">

	<h:commandButton action="#{dataGrid.submit}" value="Submit" />
	<a4j:commandButton action="#{dataGrid.submitAjax}" value="Submit Ajax" reRender="panel" />
	<br />
	<h:commandButton value="add test" action="#{dataGrid.addHtmlDataGrid}"></h:commandButton>

	<h:panelGrid columns="2">
		<h:outputText value="rendered: " />
		<h:selectBooleanCheckbox onchange="submit();" label="rendered" value="#{dataGrid.rendered}" />
	
		<h:outputText value="columns: " />
		<h:inputText onchange="submit();" value="#{dataGrid.columns}" />
		
		<h:outputText value="elements: " />
		<h:inputText onchange="submit();" value="#{dataGrid.elements}" />
		
		<h:outputText value="border: " />
		<h:inputText onchange="submit();" value="#{dataGrid.border}" />

		<h:outputText value="dir: " />
		<h:selectOneRadio value="#{dataGrid.dir}" onchange="submit();">
			<f:selectItem itemValue="LTR" itemLabel="LTR" />
			<f:selectItem itemValue="RTL" itemLabel="RTL" />
		</h:selectOneRadio>
		
		<h:outputText value="first: " />
		<h:inputText onchange="submit();" value="#{dataGrid.first}" />
		
		<h:outputText value="cellspacing: " />
		<h:inputText onchange="submit();" value="#{dataGrid.cellspacing}" />
		
		<h:outputText value="cellpadding: " />
		<h:inputText onchange="submit();" value="#{dataGrid.cellpadding}" />
		
		<h:outputText value="width: " />
		<h:inputText onchange="submit();" value="#{dataGrid.width}" />
	</h:panelGrid>

</f:subview>