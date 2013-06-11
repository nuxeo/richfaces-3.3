<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="spacerPropertySubviewID">\
<h:commandButton value="add test" action="#{spacer.addHtmlSpacer}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="Width (px or %):"></h:outputText>
		<h:inputText value="#{spacer.width}">
			<a4j:support event="onchange" reRender="spacerId"></a4j:support>
		</h:inputText>

		<h:outputText value="Height (px or %):"></h:outputText>
		<h:inputText value="#{spacer.height}">
			<a4j:support event="onchange" reRender="spacerId"></a4j:support>
		</h:inputText>

		<h:outputText value="title:"></h:outputText>
		<h:inputText value="#{spacer.title}">
			<a4j:support event="onchange" reRender="spacerId"></a4j:support>
		</h:inputText>

		<h:outputText value="Rendered:"></h:outputText>
		<h:selectBooleanCheckbox value="#{spacer.rendered}" onclick="submit()">
		</h:selectBooleanCheckbox>

		<h:outputText value="Style" />
		<a4j:commandButton id="btn" action="#{spacer.doStyle}"
			value="#{spacer.btn}" reRender="spacerId, btn"></a4j:commandButton>
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getRendererType" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column>
			<h:outputText id="findID" value="#{rich:findComponent('spacerId').rendererType}"/>
		</rich:column>
	</h:panelGrid>
</f:subview>