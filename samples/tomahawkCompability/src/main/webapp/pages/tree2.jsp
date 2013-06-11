<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<head>
<title>Response page</title>
</head>
<body>
<f:view>
	<a4j:form id="menuForm" ajaxSubmit="true" requestDelay="100"
		reRender="treePanel" ignoreDupResponses="true">
		<a4j:log hotkey="M" popup="true" level="ALL" />
		<a4j:outputPanel ajaxRendered="true" layout="inline" id="treePanel">
			<t:tree2 binding="#{treeBacker.tree}" id="serverTree"
				showRootNode="true" showNav="true" value="#{treeBacker.treeModel}"
				var="node" varNodeToggler="t" clientSideToggle="false">
				<f:facet name="desktop">
					<h:panelGroup>
						<t:graphicImage value="/resources/images/desktop.png" border="0" />
						<a4j:commandLink rendered="#{node.urlNode}"
							styleClass="#{t.nodeSelected ? 'nodeSelected':'node'}"
							actionListener="#{t.setNodeSelected}">
							<h:outputLink value="#{node.url}" target="#{node.target}">
								<h:outputText value="#{node.description}"
									styleClass="nodeFolder" />
							</h:outputLink>
						</a4j:commandLink>
					</h:panelGroup>
				</f:facet>
			</t:tree2>
		</a4j:outputPanel>
	</a4j:form>
</f:view>
</body>
</html>
