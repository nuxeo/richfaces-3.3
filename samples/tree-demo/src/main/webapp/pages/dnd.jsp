<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/drag-drop" prefix="dnd" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tree" prefix="rich"%>
<html>
<head>
	<title>Tree Drag & Drop sample</title>
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
</head>
<body>
<f:view>
	<h:form id="DnDTreeForm">
		<dnd:dragIndicator id="treeIndicator">
			<f:facet name="single">
				<f:verbatim>{marker} {nodeParam}({treeParam})</f:verbatim>
			</f:facet>
		</dnd:dragIndicator>
	
		<h:panelGrid columns="2" columnClasses="LeftTreePane,RightTreePane">
		
			<h:panelGroup id="leftContainer" layout="block" styleClass="TreeContainer">
				<h:outputText escape="false"
					value="Selected Node: <b>#{treeDndBean.leftSelectedNodeTitle}</b>"
					id="selectedNodeL" />

				<rich:tree id="leftTree" style="width:300px"
					nodeSelectListener="#{treeDndBean.processLSelection}"
					reRender="selectedNodeL" ajaxSubmitSelection="false"
					switchType="client" value="#{treeDndBean.treeNodeLeft}"
					
					changeExpandListener="#{treeDndBean.onExpand}"
					binding="#{treeDndBean.leftTree}"
					onselected="window.status='selectedNode: '+event.selectedNode;"
					onexpand="window.status='expandedNode: '+event.expandedNode"
					oncollapse="window.status='collapsedNode: '+event.collapsedNode"					
					
					dropListener="#{treeDndBean.onDrop}"
					dragListener="#{treeDndBean.onDrag}"
					
					dragIndicator="treeIndicator"
					acceptedTypes="treeNode"
					dragType="treeNode"
					rowKeyVar="key"			
					var="item">
					
					<dnd:dndParam name="treeParam" value="leftTree" />					
				</rich:tree>
				
			</h:panelGroup>

			<h:panelGroup id="rightContainer" layout="block" styleClass="TreeContainer">
				<h:outputText escape="false"
					value="Selected Node: <b>#{treeDndBean.rightSelectedNodeTitle}</b>"
					id="selectedNodeR" />

				<rich:tree id="rightTree" style="width:300px"
					nodeSelectListener="#{treeDndBean.processRSelection}"
					reRender="selectedNodeR" ajaxSubmitSelection="false"
					switchType="client" value="#{treeDndBean.treeNodeRight}"
					
					changeExpandListener="#{treeDndBean.onExpand}"
					binding="#{treeDndBean.rightTree}"
					onselected="window.status='selectedNode: '+event.selectedNode;"
					onexpand="window.status='expandedNode: '+event.expandedNode"
					oncollapse="window.status='collapsedNode: '+event.collapsedNode"					
					rowKeyVar="key"
					
					dropListener="#{treeDndBean.onDrop}"
					dragListener="#{treeDndBean.onDrag}"
					
					dragIndicator="treeIndicator"
					acceptedTypes="treeNode"
					dragType="treeNode"
					
					var="item">
					
					<dnd:dndParam name="treeParam" value="rightTree" />					
				</rich:tree>				
			</h:panelGroup>
		
		</h:panelGrid>
	</h:form>

	<a4j:log hotkey="O" />

</f:view>
</body>
</html>
