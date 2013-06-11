<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="panelMenuPropertySubviewID">
<h:commandButton value="add test" action="#{panelMenu.addHtmlPanelMenu}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="Width"></h:outputText>
		<h:inputText value="#{panelMenu.width}">
			<a4j:support event="onchange"
				reRender="panelMenuID,panelMenuID2,info"></a4j:support>
		</h:inputText>

		<h:outputText value="Tab Index"></h:outputText>
		<h:inputText value="#{panelMenu.tabIndex}">
			<a4j:support event="onchange"
				reRender="panelMenuID,panelMenuID2,info"></a4j:support>
		</h:inputText>

		<h:outputText value="Expand Single"></h:outputText>
		<h:selectBooleanCheckbox value="#{panelMenu.expandSingle}">
			<a4j:support event="onchange" reRender="panelMenuID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Mode"></h:outputText>
		<h:selectOneRadio value="#{panelMenu.mode}" id="Mode1ID"
			onchange="submit();">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
			<f:selectItem itemLabel="server" itemValue="server" />
		</h:selectOneRadio>

		<h:outputText value="Expand mode"></h:outputText>
		<h:selectOneRadio value="#{panelMenu.expandMode}" onchange="submit();">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
			<f:selectItem itemLabel="server" itemValue="server" />
		</h:selectOneRadio>

		<h:outputText value="Disabled"></h:outputText>
		<h:selectBooleanCheckbox value="#{panelMenu.disabled}">
			<a4j:support event="onchange"
				reRender="panelMenuID,panelMenuID2,info"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Rendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{panelMenu.rendered}"
			onchange="submit();"></h:selectBooleanCheckbox>
	</h:panelGrid>

	<h:panelGrid columns="4">
		<h:outputText value="Icon"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.icon}" onchange="submit();">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
		</h:selectOneMenu>

		<h:outputText value="icon Item"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.item}" onchange="submit();">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item " itemValue="#{icon.iconItem}" />
		</h:selectOneMenu>

		<h:outputText value="icon Disabled"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.disabled}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Disabled Item"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.disabledItem}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Top Item"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.topItem}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Top Disabled Item"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.topDisabledItem}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Expanded Group"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.expandedGroup}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon CollapsedGroup"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.collapsedGroup}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Disabled Group"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.disabledGroup}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Expanded Top Group"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.expandedTopGroup}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Collapsed Top Group"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.collapsedTopGroup}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Top Disable Group"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.topDisableGroup}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Expanded"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.expanded}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Collapsed"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.icon.collapsed}">
			<f:selectItem itemLabel="none" itemValue="#{icon.none}" />
			<f:selectItem itemLabel="Ajax Process"
				itemValue="#{icon.iconAjaxProcess}" />
			<f:selectItem itemLabel="Ajax Stoped"
				itemValue="#{icon.iconAjaxStoped}" />
			<f:selectItem itemLabel="Collapse" itemValue="#{icon.iconCollapse}" />
			<f:selectItem itemLabel="Expand" itemValue="#{icon.iconExpand}" />
			<f:selectItem itemLabel="File Manager"
				itemValue="#{icon.iconFileManager}" />
			<f:selectItem itemLabel="File Manager Reject"
				itemValue="#{icon.iconFileManagerReject}" />
			<f:selectItem itemLabel="Header" itemValue="#{icon.iconHeader}" />
			<f:selectItem itemLabel="Item" itemValue="#{icon.iconItem}" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>
	</h:panelGrid>
	<br />
	<h:panelGrid columns="4">
		<h:outputText value="icon Group Position"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.iconGroupPosition}">
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="right" itemValue="right" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Group Top Position"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.iconGroupTopPosition}">
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="right" itemValue="right" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Item Position"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.iconItemPosition}">
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="right" itemValue="right" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>

		<h:outputText value="icon Item Top Position"></h:outputText>
		<h:selectOneMenu value="#{panelMenu.iconItemTopPosition}">
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="right" itemValue="right" />
			<a4j:support event="onclick" reRender="panelMenuID"></a4j:support>
		</h:selectOneMenu>
	</h:panelGrid>
	<h:panelGrid columns="3">
		<h:column></h:column>
		<h:outputText value="JavaScript API"></h:outputText>
		<h:column></h:column>
		<a4j:commandLink
			onclick="PanelMenu.doExpand($('formID:panelMenuSubviewID:panelMenuID'));return false;"
			value="doExpand"></a4j:commandLink>
		<a4j:commandLink
			onclick="PanelMenu.doCollapse($('formID:panelMenuSubviewID:panelMenuID'));return false;"
			value="doCollapse"></a4j:commandLink>
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getValue" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('panelMenuID').value}" />
		</rich:column>
	</h:panelGrid>
</f:subview>