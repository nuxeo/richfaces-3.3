<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="contextMenuPropertySubviewID">
<h:commandButton value="add test" action="#{contextMenu.addHtmlContextMenu}"></h:commandButton>
	<a4j:commandButton value="reRender" reRender="cmInfoID"></a4j:commandButton>
	<a4j:commandButton action="submit();" immediate="true"
		value="immediate submit(); (a4j)"></a4j:commandButton>
	<h:commandButton action="submit();" value="submit();" />
	<h:commandButton action="submit();" immediate="true"
		value="immediate submit();" />

	<h:panelGrid columns="2" style="top">
		<h:outputText value="event:" />
		<h:selectOneMenu value="#{contextMenu.event}" onchange="submit();">
			<f:selectItem itemLabel="oncontextmenu" itemValue="oncontextmenu" />
			<f:selectItem itemLabel="onclick" itemValue="onclick" />
			<f:selectItem itemLabel="onmousemove" itemValue="onmousemove" />
		</h:selectOneMenu>

		<h:outputText value="popupWidth:" />
		<h:inputText value="#{contextMenu.popupWidth}">
			<a4j:support event="onchange" reRender="contextMenuID"></a4j:support>
		</h:inputText>

		<h:outputText value="hideDelay:" />
		<h:inputText value="#{contextMenu.hideDelay}">
			<a4j:support event="onchange" reRender="contextMenuID"></a4j:support>
		</h:inputText>

		<h:outputText value="showDelay" />
		<h:inputText value="#{contextMenu.showDelay}">
			<a4j:support event="onchange" reRender="contextMenuID"></a4j:support>
		</h:inputText>

		<h:outputText value="submitMode:" />
		<h:selectOneRadio value="#{contextMenu.submitMode}"
			onchange="submit();">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="server" itemValue="server" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
		</h:selectOneRadio>

		<h:outputText value="attached" />
		<h:selectBooleanCheckbox value="#{contextMenu.attached}"
			onchange="submit();" />

		<h:outputText value="disableDefaultMenu:" />
		<h:selectBooleanCheckbox value="#{contextMenu.disableDefaultMenu}"
			onchange="submit();">
			<a4j:support event="onchange" reRender="contextMenuID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="rendered" />
		<h:selectBooleanCheckbox value="#{contextMenu.rendered}"
			onchange="submit();">
		</h:selectBooleanCheckbox>
	</h:panelGrid>
	<br />
	<h:outputText value="JavaScript API" style="FONT-WEIGHT: bold;" />
	<h:panelGrid columns="2">
		<a4j:commandLink
			onclick="$('formID:contextMenuSubviewID:contextMenuDefaultID').component.show(event)"
			value="show(event)"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:contextMenuSubviewID:contextMenuDefaultID').component.hide()"
			value="hide()"></a4j:commandLink>
	</h:panelGrid>
	<br />
	<f:verbatim>
	<h:outputText value="Component controll test" style="FONT-WEIGHT: bold;"></h:outputText>
	<br />
	<a href="#" id="showID">show(event)</a>
	<br />
	<a href="#" id="hideID">hide()</a>
	</f:verbatim>
	<rich:componentControl attachTo="showID" event="onclick" for="contextMenuDefaultID" operation="show"></rich:componentControl>
	<rich:componentControl attachTo="hideID" event="onclick" for="contextMenuDefaultID" operation="hide"></rich:componentControl>
	<br/>
	<br/>
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
	<rich:column>
	<a4j:commandLink value="getChildCount" reRender="findID"></a4j:commandLink>
	</rich:column>
	<rich:column id="findID">
	<h:outputText value="#{rich:findComponent('contextMenuDefaultID').childCount}" />	
	</rich:column>
	</h:panelGrid>	
</f:subview>