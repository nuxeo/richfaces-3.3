<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="MessagePropertySubviewID">
	<h:commandButton value="add test" action="#{message.addHtmlMessages}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="1." />
		<h:selectOneMenu id="select1" value="#{message.select1}">
			<f:selectItem itemValue="error" itemLabel="error" />
			<f:selectItem itemValue="fatal" itemLabel="fatal" />
			<f:selectItem itemValue="warn" itemLabel="warning" />
			<f:selectItem itemValue="info" itemLabel="info" />
			<f:selectItem itemValue="passed" itemLabel="passed" />
			<f:validator validatorId="MessageValidator" />
		</h:selectOneMenu>

		<h:outputText value="2." />
		<h:selectOneMenu id="select2" value="#{message.select2}">
			<f:selectItem itemValue="error" itemLabel="error" />
			<f:selectItem itemValue="fatal" itemLabel="fatal" />
			<f:selectItem itemValue="warn" itemLabel="warning" />
			<f:selectItem itemValue="info" itemLabel="info" />
			<f:selectItem itemValue="passed" itemLabel="passed" />
			<f:validator validatorId="MessageValidator" />
		</h:selectOneMenu>

		<h:outputText value="3." />
		<h:selectOneMenu id="select3" value="#{message.select3}">
			<f:selectItem itemValue="error" itemLabel="error" />
			<f:selectItem itemValue="fatal" itemLabel="fatal" />
			<f:selectItem itemValue="warn" itemLabel="warning" />
			<f:selectItem itemValue="info" itemLabel="info" />
			<f:selectItem itemValue="passed" itemLabel="passed" />
			<f:validator validatorId="MessageValidator" />
		</h:selectOneMenu>

		<h:outputText value="4." />
		<h:selectOneMenu id="select4" value="#{message.select4}">
			<f:selectItem itemValue="error" itemLabel="error" />
			<f:selectItem itemValue="fatal" itemLabel="fatal" />
			<f:selectItem itemValue="warn" itemLabel="warning" />
			<f:selectItem itemValue="info" itemLabel="info" />
			<f:selectItem itemValue="passed" itemLabel="passed" />
			<f:validator validatorId="MessageValidator" />
		</h:selectOneMenu>

		<h:outputText value="5." />
		<h:selectOneMenu id="select5" value="#{message.select5}">
			<f:selectItem itemValue="error" itemLabel="error" />
			<f:selectItem itemValue="fatal" itemLabel="fatal" />
			<f:selectItem itemValue="warn" itemLabel="warning" />
			<f:selectItem itemValue="info" itemLabel="info" />
			<f:selectItem itemValue="passed" itemLabel="passed" />
			<f:validator validatorId="MessageValidator" />
		</h:selectOneMenu>

		<h:outputText value="Rich Message Demo:" />
		<h:selectOneMenu value="#{message.msgs}">
			<f:selectItem itemValue="no" itemLabel="no" />
			<f:selectItem itemValue="select1" itemLabel="1" />
			<f:selectItem itemValue="select2" itemLabel="2" />
			<f:selectItem itemValue="select3" itemLabel="3" />
			<f:selectItem itemValue="select4" itemLabel="4" />
			<f:selectItem itemValue="select5" itemLabel="5" />
		</h:selectOneMenu>

		<h:outputText value="Title:" />
		<h:inputText value="#{message.title}" />

		<h:outputText value="Show Detail:" />
		<h:selectBooleanCheckbox value="#{message.showDetail}" />

		<h:outputText value="ShowSummary" />
		<h:selectBooleanCheckbox value="#{message.showSummary}" />

		<h:outputText value="Tooltip" />
		<h:selectBooleanCheckbox value="#{message.tooltip}" />

		<h:outputText value="Layout:" />
		<h:selectOneMenu value="#{message.layout}">
			<f:selectItem itemValue="table" itemLabel="table" />
			<f:selectItem itemValue="list" itemLabel="list" />
		</h:selectOneMenu>

		<h:commandButton value="submit" />
		<a4j:commandButton value="submit ajax" />
	</h:panelGrid>
	<%--br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getFor(message)" reRender="findID1"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID1">
			<h:outputText value="#{rich:findComponent('messageID').for}" />
		</rich:column>
			<rich:column>
			<a4j:commandLink value="getRendererType(messages)" reRender="findID2"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID2">
			<h:outputText value="#{rich:findComponent('messagesID').rendererType}" />
		</rich:column>
	</h:panelGrid--%>
</f:subview>