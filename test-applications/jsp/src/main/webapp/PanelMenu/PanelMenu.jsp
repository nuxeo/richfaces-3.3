<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="panelMenuSubviewID">
	<rich:panelMenu  binding="#{panelMenu.myPanelMenu}" id="panelMenuID" disabled="#{panelMenu.disabled}" width="#{panelMenu.width}" selectedChild="thisChild"
		expandSingle="#{panelMenu.expandSingle}" mode="#{panelMenu.mode}" value="PanelMenu" rendered="#{panelMenu.rendered}"
		iconCollapsedGroup="#{panelMenu.icon.collapsedGroup}" iconCollapsedTopGroup="#{panelMenu.icon.collapsedTopGroup}"
		iconDisabledGroup="#{panelMenu.icon.disabledGroup}" iconDisabledItem="#{panelMenu.icon.disabledItem}"
		iconExpandedGroup="#{panelMenu.icon.expandedGroup}" iconExpandedTopGroup="#{panelMenu.icon.expandedTopGroup}"
		iconItem="#{panelMenu.icon.item}" iconTopDisabledItem="#{panelMenu.icon.disabledItem}"
		iconTopDisableGroup="#{panelMenu.icon.disabledGroup}" iconTopItem="#{panelMenu.icon.topItem}"
		iconGroupPosition="#{panelMenu.iconGroupPosition}" iconGroupTopPosition="#{panelMenu.iconGroupTopPosition}"
		iconItemPosition="#{panelMenu.iconItemPosition}" iconItemTopPosition="#{panelMenu.iconItemTopPosition}" styleClass="sPanel"
		onclick="#{event.onclick}" ondblclick="#{event.ondblclick}" ongroupcollapse="#{event.ongroupcollapse}"
		ongroupexpand="#{event.ongroupexpand}" onitemhover="#{event.onitemhover}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}">

		<rich:panelMenuItem label="Item 1(Test event)" onclick="#{event.onclick}" ondblclick="#{event.ondblclick}"
			onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}"
			onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
			onbeforedomupdate="#{event.onbeforedomupdate}" oncomplete="#{event.oncomplete}" onmouseup="#{event.onmouseup}"></rich:panelMenuItem>

		<rich:panelMenuItem>
			<h:outputText value="select "></h:outputText>
			<h:selectOneMenu value="#{richBean.srcContainer}" onchange="submit();">
				<f:selectItems value="#{richBean.listContainer}" />
			</h:selectOneMenu>
		</rich:panelMenuItem>
		<rich:panelMenuItem>
			<jsp:include flush="true" page="${richBean.pathComponentContainer}" />
		</rich:panelMenuItem>
		<rich:panelMenuItem disabled="true" iconDisabled="/pics/ajax_stoped.gif">
			<h:outputText value="Disabled Item" />
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Item Image">
			<h:graphicImage value="/pics/item.png"></h:graphicImage>
		</rich:panelMenuItem>
		<rich:panelMenuItem>
			<h:outputText value="Item4" />
		</rich:panelMenuItem>
		<rich:panelMenuItem>
			<h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
		</rich:panelMenuItem>
		<rich:panelMenuItem label="CheckBox">
			<h:selectBooleanCheckbox value="false"></h:selectBooleanCheckbox>
		</rich:panelMenuItem>
		<rich:panelMenuItem>
			<h:outputText value="CheckBox 2"></h:outputText>
			<h:selectBooleanCheckbox value="false"></h:selectBooleanCheckbox>
		</rich:panelMenuItem>
		<rich:panelMenuItem label="Action" onmousedown="alert('OnMouseDown');"></rich:panelMenuItem>

		<rich:panelMenuGroup label="Group 1(expanded=true)" expanded="true" id="pmg">
			<rich:panelMenuItem label="Item 1" disabled="true"></rich:panelMenuItem>
			<rich:panelMenuItem label="Item 1 (action)" onmousedown="alert('OnMouseDown');"></rich:panelMenuItem>
			<rich:panelMenuItem label="Item 2"></rich:panelMenuItem>

			<rich:panelMenuGroup label="Group 1_1  (align)" align="#{panelMenu.align}">
				<rich:panelMenuItem label="Imem 1_1">
					<h:inputText value="#{panelMenu.inputText}"></h:inputText>
				</rich:panelMenuItem>

				<rich:panelMenuItem label="Item 1_2"></rich:panelMenuItem>

				<rich:panelMenuGroup label="Group 1_1_1">
					<rich:panelMenuItem label="Item 1 (action)" onmousedown="alert('OnMouseDown');"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item 2"></rich:panelMenuItem>
				</rich:panelMenuGroup>

				<rich:panelMenuGroup label="Group 1_1_2">
					<rich:panelMenuItem label="Item 1"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item 2"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item 3"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item 4"></rich:panelMenuItem>
				</rich:panelMenuGroup>
			</rich:panelMenuGroup>

			<rich:panelMenuGroup label="Group 1_2 (disabled, action)" disabled="true" onmousedown="alert('Disabled');">
				<rich:panelMenuItem label="Item 1_2_1"></rich:panelMenuItem>
			</rich:panelMenuGroup>

			<rich:panelMenuGroup label="Group 1_3">
				<rich:panelMenuItem label="Item 1_3_1"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item 1_3_1"></rich:panelMenuItem>

				<rich:panelMenuGroup label="Group disabled" disabled="true">
				</rich:panelMenuGroup>
			</rich:panelMenuGroup>

		</rich:panelMenuGroup>

		<rich:panelMenuGroup id="mg2" label="Group 2 ">
			<rich:panelMenuItem label="Item 2_1"></rich:panelMenuItem>

			<rich:panelMenuGroup label="Group 2_2">
				<rich:panelMenuItem label="Item 1"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item 2"></rich:panelMenuItem>
			</rich:panelMenuGroup>

			<rich:panelMenuItem label="Item 1"></rich:panelMenuItem>
			<rich:panelMenuItem label="Item 2"></rich:panelMenuItem>
		</rich:panelMenuGroup>

		<rich:panelMenuGroup label="Group 3">
			<rich:panelMenuItem label="Item 3_1">
				<f:verbatim>
					<br />
            text <br />
            text <br />
            text
        </f:verbatim>
			</rich:panelMenuItem>
			<rich:panelMenuItem label="Item 3_2">
				<h:graphicImage value="/pics/benq.jpg" width="150px" height="100px"></h:graphicImage>
			</rich:panelMenuItem>
			<rich:panelMenuItem label="Item 3_3"></rich:panelMenuItem>
		</rich:panelMenuGroup>
	</rich:panelMenu>

	<f:verbatim>
		<br />
		<br />
	</f:verbatim>

	<h:outputText id="info"
		value="Expand Mode: #{panelMenu.mode}, Disabled: #{!panelMenu.disabled}, Align: #{panelMenu.align}, Tab Index: #{panelMenu.tabIndex}"></h:outputText>
	<!-- triangleUp triangle triangleDown disc chevron chevronUp chevronDown grid -->
	<f:verbatim>
		<br />
	</f:verbatim>

	<rich:panelMenu id="panelMenuID2" expandMode="#{panelMenu.mode}" disabled="#{!panelMenu.disabled}" width="#{panelMenu.width}"
		selectedChild="thisChild" styleClass="body">
		<rich:panelMenuGroup label="Group 1 (tabIdex, my Image)" tabindex="#{panelMenu.tabIndex}" align="#{panelMenu.align}">
			<rich:panelMenuGroup label="Group 1_1 (tabIndex)" tabindex="#{panelMenu.tabIndex}">
				<rich:panelMenuGroup label="Group 1_1_1 (tabIndex)" tabindex="#{panelMenu.tabIndex}">
					<rich:panelMenuItem label="Item 1"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item 2"></rich:panelMenuItem>
				</rich:panelMenuGroup>
			</rich:panelMenuGroup>

			<rich:panelMenuGroup label="Group 1_2 (tabIndex)" tabindex="#{panelMenu.tabIndex}">
				<rich:panelMenuGroup label="Group 1_2_1 (tabIndex)" tabindex="#{panelMenu.tabIndex}">
					<rich:panelMenuItem label="Item (icon)" icon="#{icon.iconItem}"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item (iconDisabled)" iconDisabled="#{icon.iconHeader}"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item (icon)" disabled="true" icon="#{icon.iconItem}"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item (iconDisabled)" disabled="true" iconDisabled="#{icon.iconItem}"></rich:panelMenuItem>
				</rich:panelMenuGroup>
				<rich:panelMenuItem label="Item (icon)" icon="#{icon.iconItem}"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item (icon)" icon="#{icon.iconItem}"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item "></rich:panelMenuItem>
			</rich:panelMenuGroup>

			<rich:panelMenuGroup label="Group 1_3" align="#{panelMenu.align}" iconCollapsed="#{icon.iconCollapse}"
				iconExpanded="#{icon.iconExpand}" iconDisabled="#{icon.disabled}">
				<rich:panelMenuItem label="Item 1"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item 2"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item 3"></rich:panelMenuItem>
			</rich:panelMenuGroup>

			<!-- triangleUp triangle triangleDown disc chevron chevronUp chevronDown grid -->
			<rich:panelMenuItem label="Item (disc)" icon="disc"></rich:panelMenuItem>
			<rich:panelMenuItem label="Item (grid)" icon="grid"></rich:panelMenuItem>
			<rich:panelMenuGroup label="Group" iconCollapsed="triangleDown" iconExpanded="triangleUp" iconDisabled="triangle">
				<rich:panelMenuGroup label="Group" iconCollapsed="chevronDown" iconExpanded="chevronUp" iconDisabled="chevron">
					<rich:panelMenuItem label="Item (disc)" icon="disc"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item (grid)" iconDisabled="grid"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item (grid)" icon="grid"></rich:panelMenuItem>
					<rich:panelMenuItem label="Item (disc)" iconDisabled="disc"></rich:panelMenuItem>
				</rich:panelMenuGroup>
				<rich:panelMenuItem label="Item (icon)" icon="#{icon.iconItem}"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item (icon)" icon="#{icon.iconItem}"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item "></rich:panelMenuItem>
			</rich:panelMenuGroup>

			<rich:panelMenuGroup label="Group 1_3" iconCollapsed="chevronDown" iconExpanded="chevronUp" iconDisabled="chevron">
				<rich:panelMenuItem label="Item 1"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item 2"></rich:panelMenuItem>
				<rich:panelMenuItem label="Item 3"></rich:panelMenuItem>
			</rich:panelMenuGroup>
		</rich:panelMenuGroup>
	</rich:panelMenu>
<h:panelGrid columns="1">
<h:outputText value="JavaScript API" style="FONT-WEIGHT: bold;"></h:outputText>
<h:commandLink onclick="$('formID:panelMenuSubviewID:pmg').component.expand();return false;" value="expand()"></h:commandLink>
<h:commandLink onclick="$('formID:panelMenuSubviewID:pmg').component.collapse();return false;" value="collapse()"></h:commandLink>
</h:panelGrid>
<br />
<f:verbatim>
<h:outputText value="Component Control test" style="FONT-WEIGHT: bold;"></h:outputText>
<br />
<a href="#" id="expandID">expand</a>
<br />
<a href="#" id="collapseID">collapse</a>
</f:verbatim>
<rich:componentControl attachTo="expandID" event="onclick" for="pmg" operation="expand"></rich:componentControl>
<rich:componentControl attachTo="collapseID" event="onclick" for="pmg" operation="collapse"></rich:componentControl>
</f:subview>