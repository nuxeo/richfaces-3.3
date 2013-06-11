<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="suggestionboxPropertySubviewID">
<h:commandButton value="add test" action="#{sb.addHtmlSuggestionBox}"></h:commandButton>
	<h:panelGrid columns="2" cellpadding="5px">
		<h:outputText value="ajaxSingle"></h:outputText>
		<h:selectBooleanCheckbox value="#{sb.ajaxSingle}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="bgcolor"></h:outputText>
		<h:selectOneMenu value="#{sb.bgColor}">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="aqua" itemValue="aqua" />
			<f:selectItem itemLabel="blue" itemValue="blue" />
			<f:selectItem itemLabel="fuchsia" itemValue="fuchsia" />
			<f:selectItem itemLabel="gray" itemValue="gray" />
			<f:selectItem itemLabel="lime" itemValue="lime" />
			<f:selectItem itemLabel="maroon" itemValue="maroon" />
			<f:selectItem itemLabel="purple" itemValue="purple" />
			<f:selectItem itemLabel="red" itemValue="red" />
			<f:selectItem itemLabel="silver" itemValue="silver" />
			<f:selectItem itemLabel="teal" itemValue="teal" />
			<f:selectItem itemLabel="yellow" itemValue="yellow" />
			<f:selectItem itemLabel="white" itemValue="white" />
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:selectOneMenu>

		<h:commandButton id="bindingButtonID"
			actionListener="#{sb.checkBinding}" value="binding & param" />
		<h:outputText value="#{sb.bindLabel}" />

		<h:outputText value="border" />
		<h:inputText value="#{sb.border}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="bypassUpdates"></h:outputText>
		<h:selectBooleanCheckbox value="#{sb.bypassUpdates}"
			onchange="submit();" />

		<h:outputText value="cellpadding" />
		<h:inputText value="#{sb.cellpadding}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="cellspacing" />
		<h:inputText value="#{sb.cellspacing}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="dir"></h:outputText>
		<h:selectOneMenu value="#{sb.dir}">
			<f:selectItem itemLabel="RTL" itemValue="RTL" />
			<f:selectItem itemLabel="LTR" itemValue="LTR" />
		</h:selectOneMenu>

		<h:outputText value="first" />
		<h:inputText value="#{sb.first}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="frame"></h:outputText>
		<h:selectOneMenu value="#{sb.frame}">
			<f:selectItem itemLabel="void" itemValue="void" />
			<f:selectItem itemLabel="above" itemValue="above" />
			<f:selectItem itemLabel="below" itemValue="below" />
			<f:selectItem itemLabel="hsides" itemValue="hsides" />
			<f:selectItem itemLabel="lhs" itemValue="lhs" />
			<f:selectItem itemLabel="rhs" itemValue="rhs" />
			<f:selectItem itemLabel="vsides" itemValue="vsides" />
			<f:selectItem itemLabel="box" itemValue="box" />
			<f:selectItem itemLabel="border " itemValue="border " />
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="frequency" />
		<h:inputText value="#{sb.frequency}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="height" />
		<h:inputText value="#{sb.height}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="ignoreDupResponses"></h:outputText>
		<h:selectBooleanCheckbox value="#{sb.ignoreDupResponses}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="immediate"></h:outputText>
		<h:selectBooleanCheckbox value="#{sb.immediate}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="minChars" />
		<h:inputText value="#{sb.minchars}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="nothingLabel" />
		<h:inputText value="#{sb.nothingLabel}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="usingSuggestObjects"></h:outputText>
		<h:selectBooleanCheckbox value="#{sb.usingSuggestObjects}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="z-index"></h:outputText>
		<h:selectOneRadio value="#{sb.zindex}">
			<f:selectItem itemLabel="1" itemValue="1" />
			<f:selectItem itemLabel="3" itemValue="3" />
			<a4j:support event="onclick" reRender="suggestionBoxId"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="requestDelay"></h:outputText>
		<h:inputText value="#{sb.requestDelay}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="value" />
		<h:inputText value="#{sb.value}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="Width" />
		<h:inputText value="#{sb.width}">
			<a4j:support event="onchange" reRender="suggestionBoxId"></a4j:support>
		</h:inputText>

		<h:outputText value="tokens:"></h:outputText>
		<h:inputText value="#{sb.tokens}"></h:inputText>

		<h:outputText value="Shadow Opacity"></h:outputText>
		<h:selectOneRadio value="#{sb.shadowOpacity}">
			<f:selectItem itemLabel="1" itemValue="1" />
			<f:selectItem itemLabel="3" itemValue="2" />
			<f:selectItem itemLabel="5" itemValue="3" />
			<f:selectItem itemLabel="7" itemValue="4" />
			<f:selectItem itemLabel="9" itemValue="5" />
			<f:selectItem itemLabel="11" itemValue="6" />
			<f:selectItem itemLabel="13" itemValue="7" />
			<f:selectItem itemLabel="15" itemValue="8" />
			<a4j:support event="onclick" reRender="suggestionBoxId"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="Shadow Depth"></h:outputText>
		<h:selectOneRadio value="#{sb.shadowDepth}">
			<f:selectItem itemLabel="3" itemValue="3" />
			<f:selectItem itemLabel="4" itemValue="4" />
			<f:selectItem itemLabel="5" itemValue="5" />
			<f:selectItem itemLabel="6" itemValue="6" />
			<f:selectItem itemLabel="7" itemValue="6" />
			<a4j:support event="onclick" reRender="suggestionBoxId"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="selfRendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{sb.selfRendered}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="rendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{sb.rendered}" onchange="submit();"></h:selectBooleanCheckbox>
	</h:panelGrid>
	<br />
	<br />
	<%--
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">		
			<a4j:commandLink value="getSubmitedValue" reRender="findID"></a4j:commandLink>
		
			<h:outputText id="findID" value="#{rich:findComponent('suggestionBoxId').submitedValue}"/>		
	</h:panelGrid>
	--%>
</f:subview>