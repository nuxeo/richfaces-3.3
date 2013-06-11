<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="PanelPropertySubviewID">
<h:commandButton value="add test" action="#{panel.addHtmlPanel}"></h:commandButton>
	<h:panelGrid columns="2" cellpadding="10px">
		<h:outputText value="Title"></h:outputText>
		<h:inputText valueChangeListener="#{panel.makeTitle}">
			<a4j:support event="onchange" reRender="t1,t2,t3,o1,o2"></a4j:support>
		</h:inputText>

		<h:outputText value="Width: "></h:outputText>
		<h:inputText value="#{panel.width}">
			<a4j:support event="onchange" reRender="panelId,p1"></a4j:support>
		</h:inputText>

		<h:outputText value="Height:  "></h:outputText>
		<h:inputText value="#{panel.height}">
			<a4j:support event="onchange" reRender="panelId,p1"></a4j:support>
		</h:inputText>

		<h:outputText value="Rendered:"></h:outputText>
		<h:selectBooleanCheckbox value="#{panel.rendered}" onclick="submit()"></h:selectBooleanCheckbox>
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getRendererType" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('panelId').rendererType}" />
		</rich:column>
	</h:panelGrid>
</f:subview>