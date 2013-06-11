<!-- DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
            "http://www.w3.org/TR/html4/loose.dtd"-->

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/drag-drop" prefix="dnd" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tree" prefix="rich"%>
<html>
	<head>
		<title></title>
		<style type="text/css">
			.inputs {
				font-size : 11px;
				font-family : verdana;
			}
            .customNode {
                color : #00FFFF;
                cursor: crosshair;
            }
            
            .colored {
            	background-color: #FEFEDE;
            }

            .rich-tree-node {
				white-space: normal;            
            }
        </style>

		<style>
		.treeIcon32 .dr-tree-h-ic-div {
			margin-left : 15px;
			padding-left : 17px;
		}
		
		.treeIcon32 .dr-tree-h-ic {
			width: 32px;
			padding: 0px;
		}
		
		.treeIcon32 .dr-tree-h-ic-img{
			width : 32px;
			height : 32px;
		} 
		</style>
        <script type="text/javascript">
        	function blinkElement(elt) {
				while (elt.tagName.toLowerCase() != 'table') {
					elt = elt.parentNode;
				}
				
				elt.style.borderColor= '#5555FF'; 
				elt.style.borderStyle= 'dotted';
				elt.style.borderWidth = '3px';
				setTimeout( function() { this.style.borderStyle = 'none'; }.bind(elt), 300);
        	}
        </script>
	</head>
	<body>
		<f:view>
			<h:outputText id="counter" value="#{bean.counter2}" />
		
			<a4j:outputPanel ajaxRendered="true">
				<h:messages />
			</a4j:outputPanel>

			<h:form>

			<h:inputText required="true" value="value" />

			<dnd:dragIndicator id="treeIndicator">
				<f:facet name="single">
					<f:verbatim>{marker} <br /> {treeParam} <br /> {nodeParam}</f:verbatim>
				</f:facet>
			</dnd:dragIndicator>

			<h:selectOneRadio binding="#{skinBean.component}" />
			<h:commandLink action="#{skinBean.change}" value="set skin" />
			<br />
			<h:outputText value="Change tree switchType:" />
				<h:selectOneRadio value="#{bean.switchType}" onclick="submit()">
					<f:selectItem itemLabel="client" itemValue="client" />
					<f:selectItem itemLabel="server" itemValue="server" />
					<f:selectItem itemLabel="ajax" itemValue="ajax" />
			</h:selectOneRadio>
			<h:outputText value="SwitchType is: #{bean.switchType}" />
			<br />
			<h:outputText value="Drag switch:" />
				<h:selectOneRadio value="#{bean.dragOn}" onclick="submit()">
					<f:selectItem itemLabel="on" itemValue="#{true}" />
					<f:selectItem itemLabel="off" itemValue="#{false}" />
			</h:selectOneRadio>

			<h:outputText value="Render facets:" />
				<h:selectOneRadio value="#{bean.renderFacets}" onclick="submit()">
					<f:selectItem itemLabel="true" itemValue="#{true}" />
					<f:selectItem itemLabel="false" itemValue="#{false}" />
			</h:selectOneRadio>
			
			<h:outputText value="Tree icon size:" />
			<h:selectOneRadio value="#{bean.styleClass}" onclick="submit()">
				<f:selectItem itemLabel="16x16 (default)" itemValue="treeIcon16" />
				<f:selectItem itemLabel="32x32" itemValue="treeIcon32" />
			</h:selectOneRadio>		

			<h:outputText value="DnD value:" />
			<h:selectOneRadio value="#{bean.dndValueMode}" onclick="submit()">
				<f:selectItem itemLabel="default" itemValue="#{1}" />
				<f:selectItem itemLabel="literal" itemValue="#{2}" />
				<f:selectItem itemLabel="el" itemValue="#{3}" />
			</h:selectOneRadio>		

			<h:outputText value="Drag is: #{bean.dragOn ? 'on' : 'off'}" />
			<br />
			<h:commandButton value="#{bean.commandButtonCaption}"
			actionListener="#{bean.changeIcons}" />

				<rich:tree id="tree" switchType="#{bean.switchType}"
					value="#{bean.data}" var="data"
					nodeFace="#{data.name != 'param-value' ? 'input' : 'text'}"
					changeExpandListener="#{bean.onExpand}"
					nodeSelectListener="#{bean.onSelect}" binding="#{bean.tree}"
					onselected="window.status='selectedNode: '+event.selectedNode;"
					onexpand="window.status='expandedNode: '+event.expandedNode"
					oncollapse="window.status='collapsedNode: '+event.collapsedNode"
					ajaxSubmitSelection="true"
					preserveModel="none"
					dragIndicator="treeIndicator"
					immediate="false"

					acceptedTypes="file1"
					dragType="#{bean.dragOn ? 'file1' : ''}"

					iconCollapsed="#{bean.iconCollapsed}"
					iconExpanded="#{bean.iconExpanded}"
					iconLeaf="#{bean.iconLeaf}"
					icon="#{bean.icon}"
                    dropListener="#{bean.processDrop}"
                    styleClass="#{bean.styleClass}"
                    reRender=":counter"
                    >

					<f:facet name="icon">
						<h:outputText value="icon" rendered="#{bean.renderFacets}"/>
					</f:facet>

					<f:facet name="iconLeaf">
						<h:outputText value="leaf" rendered="#{bean.renderFacets}"/>
					</f:facet>

					<f:facet name="iconExpanded">
						<h:outputText value="expanded" rendered="#{bean.renderFacets}"/>
					</f:facet>

					<f:facet name="iconCollapsed">
						<h:outputText value="collapsed" rendered="#{bean.renderFacets}"/>
					</f:facet>

					<dnd:dndParam name="treeParam" value="Tree Parameter" />
					<dnd:dndParam name="accept" value="accept" />

					<rich:treeNode ondrop="blinkElement(this.getElement())" type="input" dropListener="#{bean.processDrop}" oncollapse="Element.removeClassName(event['treeItem'].getElement(), 'colored')" onexpand="Element.addClassName(event['treeItem'].getElement(), 'colored')">
						<h:outputText value="#{data} : " />
						<h:outputText value="#{bean.counter1} : " />
						<h:selectBooleanCheckbox value="#{bean.reRenderValue}" />
						<h:inputText value="#{data.name}" required="true" styleClass="inputs">
						</h:inputText>

						<dnd:dndParam name="nodeParam" value="Node Parameter" />
						<h:outputText value="&#160;" escape="false" />
						<a4j:commandLink ajaxSingle="true" value="Immediate Link" actionListener="#{bean.action}" onclick="Event.stop(event)"/>
					</rich:treeNode>
					<rich:treeNode type="text" nodeClass="customNode" acceptedTypes="file2" onselected="return false;">
						<h:outputText value="#{data}" />
					</rich:treeNode>
				</rich:tree>

				<a4j:commandButton value="Re-render tree" reRender="tree" />
				<a4j:commandButton value="Increase counter" action="#{bean.incCounter1}" />
	
				<h:outputText value="Enter path to expand, eg. [webApp_:id__1 , webApp_:id__1:7 ]:" />
				<h:inputText value="#{bean.pathToExpand}">
					<a4j:support event="onchange" reRender="tree" action="#{bean.expandNode}"/>
				</h:inputText>
	
				<f:verbatim>
					<br />
					<br />
				</f:verbatim>
	
				<h:outputText value="Swing Tree:" />				
	
				<rich:tree id="swingTree" switchType="client" value="#{bean.swingTreeNodes}" var="data"
							preserveModel="none">

					<rich:treeNode>
						<h:outputText value="#{data.data}" />
						<h:outputText value="&#160;" escape="false" />
						<a4j:commandLink ajaxSingle="true" value="Immediate Link" actionListener="#{bean.action}" onclick="Event.stop(event)"/>
					</rich:treeNode>
				</rich:tree>

				<f:verbatim>
					<br />
					<br />
				</f:verbatim>

				<rich:tree  id="testTree"
				  var="_data"
				  switchType="ajax"
				  ajaxSubmitSelection="true"
				  preserveModel="none"
				  value="#{bean.data1}"
				  nodeSelectListener="#{bean.onSelectInc}"
				  nodeFace="node">
				  <rich:treeNode type="node">
				    <h:outputText value="#{_data}" />
				  </rich:treeNode>
				</rich:tree>	


			</h:form>

			<a4j:status startText="...start..." />

			<a4j:log hotkey="O" />

		</f:view>
	</body>
</html>
