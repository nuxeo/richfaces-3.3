<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dataOrderedListPropertySubviewID">
<h:commandButton value="add test" action="#{dataOrderedList.addHtmlDataOrderedList}"></h:commandButton>
		<h:panelGrid columns="2">
			<h:outputText value="title" />
			<h:inputText value="#{dataOrderedList.title}" >
				<a4j:support event="onchange" reRender="doListID"></a4j:support>
			</h:inputText>
			
			<h:outputText value="first" />
			<h:inputText value="#{dataOrderedList.first}" >
				<a4j:support event="onchange" reRender="doListID"></a4j:support>
			</h:inputText>
			
			<h:outputText value="rows" />
			<h:inputText value="#{dataOrderedList.rows}" >
				<a4j:support event="onchange" reRender="doListID"></a4j:support>
			</h:inputText>
			
			<h:outputText value="dir" />
			<h:selectOneRadio value="#{dataOrderedList.dir}">
				<f:selectItem itemValue="LTR" itemLabel="LTR"/>
				<f:selectItem itemValue="RTL" itemLabel="RTL"/>
				<a4j:support event="onchange" reRender="doListID"></a4j:support>
			</h:selectOneRadio>
			
			<h:outputText value="type" />
			<h:selectOneMenu value="#{dataOrderedList.type}">
				<f:selectItem itemValue="1" itemLabel="1"/>
				<f:selectItem itemValue="A" itemLabel="A"/>
				<f:selectItem itemValue="a" itemLabel="a"/>
				<f:selectItem itemValue="I" itemLabel="I"/>
				<f:selectItem itemValue="i" itemLabel="i"/>
				<f:selectItem itemValue="disc" itemLabel="disc"/>
				<f:selectItem itemValue="circle" itemLabel="circle"/>
				<f:selectItem itemValue="square" itemLabel="square"/>
				<a4j:support event="onchange" reRender="doListID"></a4j:support>
			</h:selectOneMenu>

			<h:outputText value="rendered" />
			<h:selectBooleanCheckbox value="#{dataOrderedList.rendered}" onchange="submit();"/>
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
				value="#{rich:findComponent('doListID').rowCount}" />
		</rich:column>
	</h:panelGrid>
</f:subview>