<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/treeModel" prefix="model" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tree" prefix="tree" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/drag-drop" prefix="dnd" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			<h:form>
				<a4j:outputPanel ajaxRendered="true">
					<h:messages />
				</a4j:outputPanel>
			
				<tree:tree switchType="ajax" id="tree" rowKeyConverter="org.richfaces.TreeAdaptorIntegerRowKeyConverter">
					<model:treeNodesAdaptor nodes="#{rf4351Bean.list}" var="listElement">
						<tree:treeNode ajaxSingle="true">
							<h:outputText value="Node" />
						</tree:treeNode>
						<model:treeNodesAdaptor nodes="#{listElement}" var="s">
							<tree:treeNode ajaxSingle="true">
								<h:outputText value="#{s}" />
							</tree:treeNode>
						</model:treeNodesAdaptor>
					</model:treeNodesAdaptor>

				</tree:tree>
			</h:form>
		</f:view>
	</body>	
</html>  
