<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="toolBarPropertySubviewID">
	<h:commandButton value="add test" action="#{toolBar.addHtmlToolBar}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="Image location:"></h:outputText>
		<h:selectOneRadio value="#{toolBar.location}">
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="right" itemValue="right" />
			<a4j:support event="onclick" reRender="toolBarId"></a4j:support>
		</h:selectOneRadio>


		<h:outputText value="itemSeparator:"></h:outputText>
		<h:selectOneRadio value="#{toolBar.itemSeparator}">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="line" itemValue="line" />
			<f:selectItem itemLabel="square" itemValue="square" />
			<f:selectItem itemLabel="disc" itemValue="disc" />
			<f:selectItem itemLabel="grid" itemValue="grid" />
			<a4j:support event="onclick" reRender="toolBarId"></a4j:support>
		</h:selectOneRadio>
		
		<h:outputText value="Width:"></h:outputText>
		<h:inputText value="#{toolBar.width}">
			<a4j:support event="onchange" reRender="toolBarId" />
		</h:inputText>
		
		<h:outputText value="Height:"></h:outputText>
		<h:inputText value="#{toolBar.height}">
			<a4j:support event="onchange" reRender="toolBarId" />
		</h:inputText>

		<h:outputText value="Switch Styles (check contentClass and separatorClass)" />
		<a4j:commandButton id="slBtn" value="#{toolBar.btnLabel}"
			action="#{toolBar.doStyles}" reRender="toolBarId,slBtn">
		</a4j:commandButton>
		
		<h:outputText value="rendered" />
		<h:selectBooleanCheckbox value="#{toolBar.rendered}" onclick="submit()"/>
	
		<h:commandButton actionListener="#{toolBar.checkBinding}" value="binding" />
		<h:outputText value="#{toolBar.bindLabel}" />
		
	</h:panelGrid>
	
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getItemSeparator" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('toolBarId').itemSeparator}" />
		</rich:column>
	</h:panelGrid>

</f:subview>