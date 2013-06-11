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
				<rich:tree value="#{rf4351.data}" var="node" switchType="ajax" rowKeyConverter="org.richfaces.TreeRowKeyConverter">
					<rich:treeNode ajaxSingle="true">
						<h:outputText value="#{node}"/><a4j:commandLink ajaxSingle="true" value="link" />
					</rich:treeNode>
				</rich:tree>
				
				No converter:
				<rich:tree value="#{rf4351.data}" var="node" switchType="ajax">
					<rich:treeNode ajaxSingle="true">
						<h:outputText value="#{node}"/>
					</rich:treeNode>
				</rich:tree>
				<a4j:log popup="false" />
			</h:form>
		</f:view>
	</body>
</html>
