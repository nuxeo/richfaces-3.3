<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dropDownMenuPropertySubviewID">
	<h:commandButton value="add test" action="#{dDMenu.htmlDropDownMenu}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="HideDelay (ms):" />
		<h:inputText value="#{dDMenu.hideDelay}">
			<a4j:support event="onchange" reRender="ddmId" />
		</h:inputText>

		<h:outputText value="ShowDelay (ms):" />
		<h:inputText value="#{dDMenu.showDelay}">
			<a4j:support event="onchange" reRender="ddmId" />
		</h:inputText>

		<h:outputText value="PopupWidth (px):" />
		<h:inputText value="#{dDMenu.popupWidth}">
			<a4j:support event="onchange" reRender="ddmId" />
		</h:inputText>

		<h:outputText value="horizontalOffset (px):" />
		<h:inputText value="#{dDMenu.horizontalOffset}">
			<a4j:support event="onchange" reRender="ddmId" />
		</h:inputText>

		<h:outputText value="verticalOffset (px):" />
		<h:inputText value="#{dDMenu.verticalOffset}">
			<a4j:support event="onchange" reRender="ddmId" />
		</h:inputText>

		<h:outputText value="Mode:" />
		<h:selectOneRadio value="#{dDMenu.mode}">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
			<f:selectItem itemLabel="server" itemValue="server" />
			<a4j:support event="onclick" reRender="ddmId" />
		</h:selectOneRadio>

		<h:outputText value="Direction:" />
		<h:selectOneRadio value="#{dDMenu.direction}">
			<f:selectItem itemLabel="top-right" itemValue="top-right" />
			<f:selectItem itemLabel="top-left" itemValue="top-left" />
			<f:selectItem itemLabel="bottom-right" itemValue="bottom-right" />
			<f:selectItem itemLabel="bottom-left" itemValue="bottom-left" />
			<f:selectItem itemLabel="auto" itemValue="auto" />
			<a4j:support event="onclick" reRender="ddmId" />
		</h:selectOneRadio>

		<h:outputText value="GroupDirection:" />
		<h:selectOneRadio value="#{dDMenu.groupDirection}">
			<f:selectItem itemLabel="right" itemValue="right" />
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="left-down" itemValue="left-down" />
			<f:selectItem itemLabel="left-up" itemValue="left-up" />
			<f:selectItem itemLabel="right-down" itemValue="right-down" />
			<f:selectItem itemLabel="right-up" itemValue="right-up" />
			<f:selectItem itemLabel="auto" itemValue="auto" />
			<a4j:support event="onclick" reRender="ddmId" />
		</h:selectOneRadio>

		<h:outputText value="JointPoint:" />
		<h:selectOneRadio value="#{dDMenu.jointPoint}">
			<f:selectItem itemLabel="top-right" itemValue="tr" />
			<f:selectItem itemLabel="top-left" itemValue="tl" />
			<f:selectItem itemLabel="bottom-right" itemValue="br" />
			<f:selectItem itemLabel="bottom-left" itemValue="bl" />
			<f:selectItem itemLabel="auto" itemValue="auto" />
			<a4j:support event="onclick" reRender="ddmId" />
		</h:selectOneRadio>

		<h:outputText value="Menu appearance event:" />
		<h:selectOneRadio value="#{dDMenu.event}" onclick="submit()">
			<f:selectItem itemLabel="onclick" itemValue="onclick" />
			<f:selectItem itemLabel="onmouseover" itemValue="onmouseover" />
			<a4j:support event="onclick" reRender="ddmId" />
		</h:selectOneRadio>

		<h:outputText value="Rendered:" />
		<h:selectBooleanCheckbox value="#{dDMenu.rendered}" onclick="submit()">
		</h:selectBooleanCheckbox>

		<h:outputText value="Disable some ddmenu:"></h:outputText>
		<h:selectBooleanCheckbox value="#{dDMenu.disabledDDM}">
			<a4j:support reRender="ddmId" event="onclick" />
		</h:selectBooleanCheckbox>

		<h:outputText value="Disable some items:" />
		<h:selectBooleanCheckbox value="#{dDMenu.disabled}">
			<a4j:support reRender="ddmId" event="onclick" />
		</h:selectBooleanCheckbox>
		<h:commandButton action="#{dDMenu.changeIcons}" value="ChangeIcons" />
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getSubmitMode" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('ddmId').submitMode}" />
		</rich:column>
	</h:panelGrid>
</f:subview>