<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tree" prefix="rich"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			<a4j:status startText="...start..." stopText="stopped" />
			
			<h:form>
				ajaxChildActivationEncodeBehavior: 
				<h:selectOneMenu value="#{bean.ajaxChildActivationEncodeBehavior}">
					<f:selectItem itemLabel="default" itemValue=""/>
					<f:selectItem itemLabel="none" itemValue="none"/>
					<f:selectItem itemLabel="node" itemValue="node"/>
					<f:selectItem itemLabel="subtree" itemValue="subtree"/>
				</h:selectOneMenu><br />
				
				ajaxNodeSelectionEncodeBehavior:
				<h:selectOneMenu value="#{bean.ajaxNodeSelectionEncodeBehavior}">
					<f:selectItem itemLabel="default" itemValue=""/>
					<f:selectItem itemLabel="none" itemValue="none"/>
					<f:selectItem itemLabel="node" itemValue="node"/>
					<f:selectItem itemLabel="subtree" itemValue="subtree"/>
				</h:selectOneMenu><br />
				<h:commandButton value="Apply" />
			</h:form>
			
			<h:form>
				<rich:tree ajaxNodeSelectionEncodeBehavior="#{bean.ajaxNodeSelectionEncodeBehavior}"
				ajaxChildActivationEncodeBehavior="#{bean.ajaxChildActivationEncodeBehavior}"
				ajaxSubmitSelection="true" binding="#{rf4196.tree}" value="#{bean.data}" var="item" switchType="ajax">
					<rich:treeNode>
						<h:outputText escape="false" value="#{item} <b style='color: red;'>#{bean.requestCounter}</b> Re-render: " />
						<a4j:commandLink value="Default" /> /
						<a4j:commandLink value="Node" action="#{rf4196.rerenderNode}" /> / 				
						<a4j:commandLink value="Subtree" action="#{rf4196.rerenderSubtree}" />				
					</rich:treeNode>
				</rich:tree>
				
				<a4j:commandLink value="Re-render tree root node" action="#{rf4196.rerenderRoot}" />
			</h:form>
		</f:view>
	</body>
</html>
