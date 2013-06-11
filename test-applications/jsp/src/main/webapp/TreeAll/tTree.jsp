<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<f:subview id="tTreeSubviewID">
	<h:panelGrid columns="2" border="1">
		<f:facet name="header">
			<h:outputText value="Select tree to show: " />
		</f:facet>
		<h:outputText value="default Tree: " />
		<h:selectBooleanCheckbox value="#{pVisability.tTreeSubviewID}">
			<a4j:support event="onchange" reRender="dTreePanelID"></a4j:support>
		</h:selectBooleanCheckbox>
		<h:outputText value="Tree with treeNodesAdaptor: " />
		<h:selectBooleanCheckbox value="#{pVisability.tTreePropertySubviewID}">
			<a4j:support event="onchange" reRender="tTreeNAPanelID"></a4j:support>
		</h:selectBooleanCheckbox>
		<h:outputText value="Tree with recursiveTreeNodesAdaptor: " />
		<h:selectBooleanCheckbox
			value="#{pVisability.tTreeStraightforwardSubviewID}">
			<a4j:support event="onchange" reRender="tTreeRNAPanelID"></a4j:support>
		</h:selectBooleanCheckbox>
		<h:outputText value="Tree with Drag and Drop functionality: " />
		<h:selectBooleanCheckbox value="#{pVisability.tTreeDefaultSubviewID}">
			<a4j:support event="onchange" reRender="tTreeDNDPanelID"></a4j:support>
		</h:selectBooleanCheckbox>
	</h:panelGrid>
	<rich:spacer height="10" />

	<a4j:outputPanel id="dTreePanelID">
		<h:panelGrid columns="1" rendered="#{pVisability.tTreeSubviewID}">
			<h:outputText value="default Tree" style="color: red" />
			<rich:tree id="dTree" switchType="#{tTree.switchType}"
				value="#{tTree.data}" var="defTree" binding="#{tTree.tree}"
				ajaxSubmitSelection="#{tTree.ajaxSubmitSelection}"
				immediate="#{tTree.immediate}" rendered="#{tTree.rendered}"
				reRender="reRenderID" ignoreDupResponses="true"
				nodeFace="#{defTree.name != 'param-value' ? 'text' : 'input'}"
				showConnectingLines="#{tTree.showConnectingLines}" focus="focusID"
				nodeSelectListener="#{tTree.nodeSelectListener}"
				toggleOnClick="#{tTree.toggleOnClick}"
				changeExpandListener="#{tTree.changeExpandListener}"
				adviseNodeOpened="#{tTree.adviseNodeOpened}"
				adviseNodeSelected="#{tTree.adviseNodeSelected}"
				onbeforedomupdate="#{event.onbeforedomupdate}"
				onclick="#{event.onclick}" oncollapse="#{event.oncollapse}"
				oncomplete="#{event.oncomplete}" ondblclick="#{event.ondblclick}"
				ondragend="#{event.ondragend}" ondragenter="#{event.ondragenter}"
				ondragexit="#{event.ondragexit}" ondragstart="#{event.ondragstart}"
				ondrop="#{event.ondrop}" ondropend="#{event.ondropend}"
				ondropout="#{event.ondropout}" ondropover="#{event.ondropover}"
				onexpand="#{event.onexpand}" onkeydown="#{event.onkeydown}"
				onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
				onmousedown="#{event.onmousedown}"
				onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
				onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"
				onselected="#{event.onselected}" oncontextmenu="#{event.oncontextmenu}"
				disableKeyboardNavigation="#{tTree.disableKeyboardNavigation}">

				<rich:treeNode type="input">
					<h:outputText value="#{defTree} : " />
					<h:inputText value="#{defTree.name}" />
				</rich:treeNode>

				<rich:treeNode type="text">
					<h:outputText value="#{defTree}" />
				</rich:treeNode>
			</rich:tree>
			<hr />
			<h:commandButton id="focusID" action="#{tTree.add}" value="add test" />
			<h:panelGrid columns="2">
				<f:facet name="header">
					<h:outputText value="Tree Properties" />
				</f:facet>

				<h:outputText value="reRender:" />
				<h:panelGroup>
					<a4j:commandButton value="reset"
						action="#{tTree.resetReRenderCheck}" reRender="reRenderID"></a4j:commandButton>
					<h:outputText id="reRenderID" value="#{tTree.reRenderCheck}" />
				</h:panelGroup>

				<h:outputText value="Change tree switchType:" />
				<h:selectOneRadio value="#{tTree.switchType}" onclick="submit();">
					<f:selectItem itemLabel="client" itemValue="client" />
					<f:selectItem itemLabel="server" itemValue="server" />
					<f:selectItem itemLabel="ajax" itemValue="ajax" />
				</h:selectOneRadio>

				<h:outputText value="rendered:" />
				<h:selectBooleanCheckbox value="#{tTree.rendered}"
					onchange="submit();" />
					
				<h:outputText value="disableKeyboardNavigation:" />
				<h:selectBooleanCheckbox value="#{tTree.disableKeyboardNavigation}"
					onchange="submit();" />

				<h:outputText value="showConnectingLines:" />
				<h:selectBooleanCheckbox value="#{tTree.showConnectingLines}"
					onchange="submit();" />

				<h:outputText value="toggleOnClick:" />
				<h:selectBooleanCheckbox value="#{tTree.toggleOnClick}"
					onchange="submit();" />

				<h:outputText value="ajaxSubmitSelection:" />
				<h:selectBooleanCheckbox value="#{tTree.ajaxSubmitSelection}"
					onchange="submit();" />

				<h:outputText value="immediate:" />
				<h:selectBooleanCheckbox value="#{tTree.immediate}"
					onchange="submit();" />

				<h:outputText value="use custom icons:" />
				<h:selectBooleanCheckbox value="#{tTree.useCustomIcons}">
					<a4j:support action="#{tTree.addCustomIcons}" event="onchange"
						reRender="dTreePanelID"></a4j:support>
				</h:selectBooleanCheckbox>

				<h:outputText value="adviseNodeOpened:" />
				<h:selectOneRadio value="#{tTree.anOpened}" onclick="submit();">
					<f:selectItem itemLabel="null (default)" itemValue="null" />
					<f:selectItem itemLabel="false (do not open)" itemValue="FALSE" />
					<f:selectItem itemLabel="true (open)" itemValue="TRUE" />
				</h:selectOneRadio>

				<h:outputText value="adviseNodeSelected:" />
				<h:selectOneRadio value="#{tTree.anSelected}" onclick="submit();">
					<f:selectItem itemLabel="null (default)" itemValue="null" />
					<f:selectItem itemLabel="false (do not select)" itemValue="FALSE" />
					<f:selectItem itemLabel="true (select)" itemValue="TRUE" />
				</h:selectOneRadio>
			</h:panelGrid>
			<rich:separator height="10" />
		</h:panelGrid>
	</a4j:outputPanel>
</f:subview>
