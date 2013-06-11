<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="togglePanelPropertySubviewID">
<h:commandButton value="add test" action="#{togglePanel.addHtmlTogglePanel}"></h:commandButton>
	<h:panelGrid columns="2" cellpadding="5px" cellspacing="5px">
		<h:outputText value="InitialState:"></h:outputText>
		<h:selectOneRadio value="#{togglePanel.initialState}">
			<f:selectItem itemLabel="Asus" itemValue="asus" />
			<f:selectItem itemLabel="Benq" itemValue="benq" />
			<f:selectItem itemLabel="toshiba" itemValue="toshiba" />
			<a4j:support event="onchange" reRender="tooggleTest:panel2"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="StateOrder:"></h:outputText>
		<h:selectOneRadio value="#{togglePanel.stateOrder}">
			<f:selectItem itemLabel="Asus,Benq,Toshiba"
				itemValue="asus,benq,toshiba" />
			<f:selectItem itemLabel="Toshiba, Asus, Benq"
				itemValue="toshiba,asus,benq" />
			<a4j:support event="onchange" reRender="tooggleTest:panel2"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="switchType:"></h:outputText>
		<h:selectOneRadio value="#{togglePanel.switchType}">
			<f:selectItem itemLabel="client" itemValue="client" />
			<f:selectItem itemLabel="server" itemValue="server" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
			<a4j:support event="onclick" reRender="panel1,panel2"></a4j:support>
		</h:selectOneRadio>
		
		<h:outputText value="immediate:"></h:outputText>
		<h:selectBooleanCheckbox value="#{togglePanel.immediate}" onchange="submit();"/>
	</h:panelGrid>
	<br />
	<br />
	<%--
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getStateOrder" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('panel2').stateOrder}" />
		</rich:column>
	</h:panelGrid>
	--%>
</f:subview>