<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<style type="text/css">
.LeftTreePane {
	
}

.RightTreePane {
	
}

.TreeContainer {
	overflow: auto;
	height: 400px;
	border: 3px inset gray;
}
</style>
<script type="text/javascript">
		// <![CDATA[
        	function blinkElement(elt) {
				while (elt.tagName.toLowerCase() != 'table') {
					elt = elt.parentNode;
				}
				
				elt.style.borderColor= '#5555FF'; 
				elt.style.borderStyle= 'dotted';
				elt.style.borderWidth = '3px';
				setTimeout( function() { this.style.borderStyle = 'none'; }.bind(elt), 300);
        	}
    	// ]]>    
	</script>
<f:subview id="treeSubviewID">
	<a4j:outputPanel ajaxRendered="true">
		<h:messages />
	</a4j:outputPanel>

	<rich:tree id="tree" switchType="#{treeBean.switchType}"
		value="#{treeBean.data}" var="dataTree" selectedClass="#{style.selectedClass}"
		styleClass="#{style.styleClass}" style="#{style.style}"
		highlightedClass="#{style.highlightedClass}"
		nodeFace="#{dataTree.name != 'param-value' ? 'input' : 'text'}"
		changeExpandListener="#{treeBean.onExpand}"
		nodeSelectListener="#{treeBean.onSelect}" binding="#{treeBean.tree}"
		onselected="window.status='selectedNode: '+event.selectedNode;"
		onexpand="window.status='expandedNode: '+event.expandedNode"
		oncollapse="window.status='collapsedNode: '+ event.collapsedNode"
		ajaxSubmitSelection="true" reRender="outputText, selectOneListbox"
		preserveModel="none" dragIndicator="treeIndicator" immediate="false"
		acceptedTypes="file1" dragType="#{treeBean.dragOn ? 'file1' : ''}"
		icon="#{treeBean.icon}" dropListener="#{treeBean.processDrop}"
		onclick="#{event.onclick}" ondblclick="#{event.ondblclick}"
		ondragend="#{event.ondragend}" ondragenter="#{event.ondragenter}"
		ondragexit="#{event.ondragexit}" ondragstart="#{event.ondragstart}"
		ondrop="#{event.ondrop}" ondropend="#{event.ondropend}"
		onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}"
		onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"
		onbeforedomupdate="#{event.onbeforedomupdate}"
		oncomplete="#{event.oncomplete}" ondropout="#{event.ondropout}"
		ondropover="#{event.ondropover}" adviseNodeOpened="#{treeBean.adviseNodeOpened}"
		adviseNodeSelected="#{treeBean.adviseNodeSelected}">
		<!-- 
			<f:facet name="icon">
				<h:outputText value="icon" rendered="#{treeBean.renderFacets}" />
			</f:facet>
			<f:facet name="iconLeaf">
				<h:outputText value="leaf" rendered="#{treeBean.renderFacets}" />
			</f:facet>
			<f:facet name="iconExpanded">
				<h:outputText value="expanded" rendered="#{treeBean.renderFacets}" />
			</f:facet>
			<f:facet name="iconCollapsed">
				<h:outputText value="collapsed" rendered="#{treeBean.renderFacets}" />
			</f:facet>
 -->
		<rich:dndParam name="treeParam" value="Tree Parameter" />
		<rich:dndParam name="accept" value="accept" />

		<rich:treeNode type="input" dropListener="#{treeBean.processDrop}"
			oncollapse="Element.removeClassName(event['treeItem'].getElement(), 'colored')"
			onexpand="Element.addClassName(event['treeItem'].getElement(), 'colored')"
			onbeforedomupdate="#{event.onbeforedomupdate}"
			onclick="#{event.onclick}" oncomplete="#{event.oncomplete}"
			oncontextmenu="#{event.oncontextmenu}"
			ondblclick="#{event.ondblclick}" ondragend="#{event.ondragend}"
			ondragenter="#{event.ondragenter}" ondragexit="#{event.ondragexit}"
			ondragstart="#{event.ondragstart}" ondrop="#{event.ondrop}"
			ondropend="#{event.ondropend}" ondropout="#{event.ondropout}"
			ondropover="#{event.ondropover}" onkeydown="#{event.onkeydown}"
			onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
			onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}"
			onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
			onmouseup="#{event.onmouseup}" onselected="#{event.onselected}">
			<h:outputText value="#{dataTree} : " />
			<h:inputText value="#{dataTree.name}" required="true" styleClass="inputs">
			</h:inputText>

			<rich:dndParam name="nodeParam" value="Node Parameter" />

		</rich:treeNode>
		<rich:treeNode type="text" nodeClass="customNode"
			acceptedTypes="file2" onselected="return false;">
			<h:outputText value="#{dataTree}" />
		</rich:treeNode>
	</rich:tree>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:outputText value="Enter path to expand:" />
	<h:inputText value="#{treeBean.pathToExpand}">
		<a4j:support event="onchange" reRender="tree"
			action="#{treeBean.expandNode}" />
	</h:inputText>

	<rich:separator></rich:separator>

	<h:panelGrid columns="2">
		<h:outputText value="Change tree switchType:" />
		<h:selectOneRadio value="#{treeBean.switchType}" onclick="submit()">
			<f:selectItem itemLabel="client" itemValue="client" />
			<f:selectItem itemLabel="server" itemValue="server" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
		</h:selectOneRadio>

		<h:outputText value="Drag switch:" />
		<h:selectBooleanCheckbox value="#{treeBean.dragOn}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="Render facets:" />
		<h:selectBooleanCheckbox value="#{treeBean.renderFacets}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:commandButton value="#{treeBean.commandButtonCaption}"
			actionListener="#{treeBean.changeIcons}" />
	</h:panelGrid>

	<f:verbatim>
		<br />
		<br />
	</f:verbatim>

	<rich:tree id="testTree" var="_data" switchType="ajax"
		ajaxSubmitSelection="true" preserveModel="none" value="#{treeBean.data1}"
		nodeSelectListener="#{treeBean.onSelectInc}" nodeFace="node">
		<rich:treeNode type="node">
			<h:outputText value="#{_data}" />
		</rich:treeNode>
	</rich:tree>
	<rich:separator></rich:separator>
	<h:outputText value="Tree without treeNode:"></h:outputText>
	<rich:separator></rich:separator>

	<rich:tree switchType="client" style="width:300px"
		value="#{pathwayBean.pathwayTree}" var="item" nodeFace="#{item.type}">
		<rich:treeNode type="library">
			<h:outputText value="#{item.type}" />
		</rich:treeNode>
		<rich:treeNode type="pathway">
			<h:outputText value="#{item.name}" />
		</rich:treeNode>
		<rich:treeNode type="organism">
			<h:outputText value="#{item.name}" />
		</rich:treeNode>
	</rich:tree>

	<rich:spacer></rich:spacer>
	<!-- Drag and Drop API for rich:tree component -->
	<div style="FONT-WEIGHT: bold;">Drag & Drop example</div>
	<br />
	<h:form>
		<rich:dragIndicator id="treeIndicator">
			<f:facet name="single">
				<f:verbatim>{marker} {nodeParam}({treeParam})</f:verbatim>
			</f:facet>
		</rich:dragIndicator>

		<h:panelGrid columns="2" columnClasses="LeftTreePane,RightTreePane">

			<h:panelGroup id="leftContainer" layout="block"
				styleClass="TreeContainer">
				<h:outputText escape="false"
					value="Selected Node: <b>#{treeDndBean.leftSelectedNodeTitle}</b>"
					id="selectedNodeL" />

				<rich:tree id="leftTree" style="width:300px"
					nodeSelectListener="#{treeDndBean.processLSelection}"
					reRender="selectedNodeL" ajaxSubmitSelection="true"
					switchType="client" value="#{treeDndBean.treeNodeLeft}"
					changeExpandListener="#{treeDndBean.onExpand}"
					binding="#{treeDndBean.leftTree}"
					onselected="window.status='selectedNode: '+event.selectedNode;"
					onexpand="window.status='expandedNode: '+event.expandedNode"
					oncollapse="window.status='collapsedNode: '+event.collapsedNode"
					dropListener="#{treeDndBean.onDrop}"
					dragListener="#{treeDndBean.onDrag}" dragIndicator="treeIndicator"
					acceptedTypes="treeNode" dragType="treeNode" rowKeyVar="key"
					var="item" >

					<rich:dndParam name="treeParam" value="leftTree" />					
				</rich:tree>

			</h:panelGroup>

			<h:panelGroup id="rightContainer" layout="block"
				styleClass="TreeContainer">
				<h:outputText escape="false"
					value="Selected Node: <b>#{treeDndBean.rightSelectedNodeTitle}</b>"
					id="selectedNodeR" />

				<rich:tree id="rightTree" style="width:300px"
					nodeSelectListener="#{treeDndBean.processRSelection}"
					reRender="selectedNodeR,rightContainer" ajaxSubmitSelection="true"
					switchType="client" value="#{treeDndBean.treeNodeRight}"
					changeExpandListener="#{treeDndBean.onExpand}"
					binding="#{treeDndBean.rightTree}"
					onselected="window.status='selectedNode: '+event.selectedNode;"
					onexpand="window.status='expandedNode: '+event.expandedNode"
					oncollapse="window.status='collapsedNode: '+event.collapsedNode"
					rowKeyVar="key" dropListener="#{treeDndBean.onDrop}"
					dragListener="#{treeDndBean.onDrag}" dragIndicator="treeIndicator"
					acceptedTypes="treeNode" dragType="treeNode" var="item">
					<rich:dndParam name="treeParam" value="rightTree" />
				</rich:tree>
			</h:panelGroup>

		</h:panelGrid>
	</h:form>
</f:subview>
