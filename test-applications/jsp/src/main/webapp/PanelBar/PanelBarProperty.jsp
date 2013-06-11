<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="panelBarPropertySubviewID">
<h:commandButton value="add test" action="#{panelBar.addHtmlPanelBar}"></h:commandButton>
	<h:panelGrid columns="2" cellspacing="10px">
		<h:outputText value="Label: "></h:outputText>
		<h:inputText valueChangeListener="#{panelBar.makeLabels}">
			<a4j:support event="onchange" reRender="pBId"></a4j:support>
		</h:inputText>

		<h:outputText value="Width: "></h:outputText>
		<h:inputText value="#{panelBar.width}">
			<a4j:support event="onchange" reRender="pBId"></a4j:support>
		</h:inputText>

		<h:outputText value="Height: "></h:outputText>
		<h:inputText value="#{panelBar.height}">
			<a4j:support event="onchange" reRender="pBId"></a4j:support>
		</h:inputText>

		<h:outputText value="Rendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{panelBar.rendered}">
			<a4j:support event="onchange" reRender="pBId" />
		</h:selectBooleanCheckbox>

		<h:outputText value="Switch Styles:" />
		<h:commandButton action="#{panelBar.doStyles}"
			value="#{panelBar.btnLabel}" />

		<h:outputText value="Binding test:" />
		<a4j:commandButton value="Go"
			actionListener="#{panelBar.actionListener}"></a4j:commandButton>
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getSelectedPanel" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('pBId').selectedPanel}" />
		</rich:column>
	</h:panelGrid>
</f:subview>