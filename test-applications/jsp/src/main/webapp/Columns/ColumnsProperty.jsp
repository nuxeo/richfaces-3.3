<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="columnsPropertyID">
	<h:commandButton value="submit"></h:commandButton>
	<a4j:commandButton value="submit [a4j]" reRender="columnsID"></a4j:commandButton>
	<h:panelGrid columns="2">
		<f:facet name="header">
			<h:outputText value="columns"></h:outputText>
		</f:facet>
		<h:outputText value="Enter quantity of lines [data 1]" />
		<h:panelGroup>
			<h:inputText value="#{columns.length1}" />
			<h:commandButton action="#{columns.addNewItem1}" value="ok"></h:commandButton>			
		</h:panelGroup>

		<h:outputText value="Enter quantity of lines [data 2]" />
		<h:panelGroup>
			<h:inputText value="#{columns.length2}" />
			<h:commandButton action="#{columns.addNewItem2}" value="ok"></h:commandButton>
		</h:panelGroup>
		
		<h:outputText value="filterMethod"></h:outputText>
		<h:inputText value="#{columns.filterInput}"
			onchange="submit();" />

		<h:outputText value="columns (*):"></h:outputText>
		<h:inputText value="#{columns.columns}" onchange="submit();">
		</h:inputText>
		<%-- JSP comments paste here--%>
		<h:outputText value="rowspan:"></h:outputText>
		<h:inputText value="#{columns.rowspan}" onchange="submit();">
		</h:inputText>

		<h:outputText value="colspan:"></h:outputText>
		<h:inputText value="#{columns.colspan}" onchange="submit();">
		</h:inputText>

		<h:outputText value="begin:"></h:outputText>
		<h:inputText value="#{columns.begin}" onchange="submit();">
		</h:inputText>

		<h:outputText value="end:"></h:outputText>
		<h:inputText value="#{columns.end}" onchange="submit();">
		</h:inputText>

		<h:outputText value="width:"></h:outputText>
		<h:inputText value="#{columns.width}" onchange="submit();">
		</h:inputText>		
		
		<h:outputText value="breakBefore:"></h:outputText>
		<h:selectBooleanCheckbox value="#{columns.breakBefore}" onchange="submit();">
		</h:selectBooleanCheckbox>			
			
	</h:panelGrid>	
</f:subview>