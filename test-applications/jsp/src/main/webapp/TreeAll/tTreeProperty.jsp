<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="tTreePropertySubviewID">
	<a4j:outputPanel id="tTreeNAPanelID">
		<h:panelGrid columns="1"
			rendered="#{pVisability.tTreePropertySubviewID}">
			<h:outputText value="Tree with treeNodesAdaptor" style="color: red" />
			<rich:tree>
				<rich:treeNodesAdaptor nodes="#{tTreeNA.treeNA}" var="project">
					<rich:treeNode>
						<h:outputText value="#{project.name}" />
					</rich:treeNode>
					<rich:treeNodesAdaptor nodes="#{project.srcDirs}" var="dir">
						<rich:treeNode>
							<h:outputText value="#{dir.name}" />
						</rich:treeNode>
						<rich:treeNodesAdaptor nodes="#{dir.packages}" var="package">
							<rich:treeNode>
								<h:outputText value="#{package.name}" />
							</rich:treeNode>
						</rich:treeNodesAdaptor>
					</rich:treeNodesAdaptor>
				</rich:treeNodesAdaptor>
			</rich:tree>
		</h:panelGrid>
	</a4j:outputPanel>
</f:subview>
