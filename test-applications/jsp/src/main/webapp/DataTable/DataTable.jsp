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
<f:subview id="DataTableSubviewID">
	<rich:dragIndicator id="treeIndicator">
			<f:facet name="single">
				<f:verbatim>{marker} {nodeParam}({treeParam})</f:verbatim>
			</f:facet>
	</rich:dragIndicator>
	
	<rich:dataTable id="dataTableID" binding="#{dataTable.htmlDataTable}" var="dataTableID" value="#{dataTable.mounths}" rowKeyVar="key" 
		captionClass="#{style.captionClass}" rowClasses="#{style.rowClasses}" headerClass="#{style.headerClass}" footerClass="#{style.footerClass}" styleClass="#{style.styleClass}" captionStyle="#{style.captionStyle}" columnClasses="#{style.columnClasses}"
		onRowClick="#{event.onRowClick}" rendered="#{dataTable.rendered}"		
		align="#{dataTable.align}" bgcolor="red" border="#{dataTable.border}" columnsWidth="#{dataTable.columnsWidth}"
		width="#{dataTable.width}" title="DataTableTite" onRowDblClick="#{event.onRowDblClick}" onRowMouseDown="#{event.onRowMouseDown}"
		onRowMouseMove="#{event.onRowMouseMove}" onRowMouseOut="#{event.onRowMouseOut}" onRowMouseOver="#{event.onRowMouseOver}"
		onRowMouseUp="#{event.onRowMouseUp}" onclick="#{event.onclick}" ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}">
		<a4j:support event="onRowDblClick" action="#{controller.rfAction}"></a4j:support>
		<f:facet name="caption">
			<h:outputText value="caption facet" />
		</f:facet>
		<f:facet name="header">
			<rich:columnGroup>
				<rich:column rowspan="2" rendered="#{dataTable.r2rendered}">
					<h:outputText value="2-row head" />
				</rich:column>
				<h:column rendered="#{dataTable.r2rendered}">
					<h:outputText value="head in UIColumn" />
				</h:column>
				<rich:column breakBefore="true">
					<h:outputText value="2-d row head" />
				</rich:column>
			</rich:columnGroup>
		</f:facet>
		<f:facet name="footer">
			<h:outputText value="table foot" />
		</f:facet>
		<rich:columnGroup columnClasses="#{style.columnClassesA}" rowClasses="#{style.rowClassesA}" style="#{style.styleA}" styleClass="#{style.styleClassA}">
			<rich:column id="mounth" >
				<f:facet name="header">
					<h:outputText value="mounth" />
				</f:facet>
				<f:facet name="footer">
					<h:outputText value="-//-" />
				</f:facet>
				<h:outputText value="#{dataTableID.mounth}" />
			</rich:column>
			<rich:column rendered="#{dataTable.r2rendered}">
				<f:facet name="header">
					<h:outputText value="mounth" />
				</f:facet>
				<f:facet name="footer">
					<h:outputText value="-//-" />
				</f:facet>
				<h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
				<h:outputText value="#{dataTableID.town}" />
			</rich:column>
		</rich:columnGroup>
		<rich:column styleClass="#{style.styleClassA}" headerClass="#{style.headerClassA}" footerClass="#{style.footerClassA}" style="#{style.styleA}" rendered="#{dataTable.r2rendered}">
			<rich:tree id="rightTree" style="width:300px" 
					nodeSelectListener="#{treeDndBean.processRSelection}"
					reRender="selectedNodeR,rightContainer" ajaxSubmitSelection="true"
					switchType="client" value="#{treeDndBean.treeNodeRight}"
					changeExpandListener="#{treeDndBean.onExpand}"
					binding="#{treeDndBean.rightTree}"
					onselected="window.status='selectedNode: '+event.selectedNode;"
					onexpand="window.status='expandedNode: '+event.expandedNode"
					oncollapse="window.status='collapsedNode: '+event.collapsedNode"
					rowKeyVar="RTreeKey" dropListener="#{treeDndBean.onDrop}"
					dragListener="#{treeDndBean.onDrag}" dragIndicator="treeIndicator"
					acceptedTypes="treeNode" dragType="treeNode" var="item">
					<rich:dndParam name="treeParam" value="rightTree" />
				</rich:tree>
				<div>column with the 1st tree</div>
		</rich:column>
		<rich:column></rich:column>
		<rich:subTable id="detail" var="detail" value="#{dataTableID.detail}"  rowKeyVar="subRowKey"
			columnClasses="#{style.columnClassesA}" footerClass="#{style.footerClassA}" headerClass="#{style.headerClassA}" rowClasses="#{style.rowClassesA}" onclick="#{event.onclick}"
			ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
			onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
			onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}" onRowClick="#{event.onRowClick}"
			onRowDblClick="#{event.onRowDblClick}" onRowMouseDown="#{event.onRowMouseDown}" onRowMouseMove="#{event.onRowMouseMove}"
			onRowMouseOut="#{event.onRowMouseOut}" onRowMouseOver="#{event.onRowMouseOver}" onRowMouseUp="#{event.onRowMouseUp}">
			<rich:column id="name">
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
					acceptedTypes="treeNode" dragType="treeNode" rowKeyVar="LTreeKey"
					var="item" >

					<rich:dndParam name="treeParam" value="leftTree" />					
				</rich:tree>	
				<div>column with the 2nd tree</div>			
			</rich:column>
			<rich:column id="qty" rendered="#{dataTable.r2rendered}">
				<h:outputText value="#{detail.qty}" />
			</rich:column>
		</rich:subTable>
		<rich:column id="total" footerClass="#{style.footerClassA}" headerClass="#{style.headerClassA}" style="#{style.styleA}" styleClass="#{style.styleClassA}" colspan="2">
			<h:outputText value="#{dataTableID.total}" />
		</rich:column>
	</rich:dataTable>		
</f:subview>
