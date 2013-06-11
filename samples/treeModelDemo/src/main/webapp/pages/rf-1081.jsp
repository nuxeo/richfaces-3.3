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
			
				<dnd:dragIndicator id="indicator" />

				<tree:tree dragIndicator="indicator" switchType="client" id="tree" reRender="tree" dropListener="#{loaderBean.treeDrop}" style="width: 400px;">
					<dnd:dndParam name="label" value="Tree" />

					<model:treeNodesAdaptor id="project" nodes="#{loaderBean.projects}" var="project">
						<tree:treeNode dragType="project" acceptedTypes="srcDir,dir">
					    	<h:outputText value="Project: #{project.name}" />
					    </tree:treeNode>
	
						<model:treeNodesAdaptor id="srcDir" var="srcDir" nodes="#{project.srcDirs}">
							<tree:treeNode dragType="srcDir" acceptedTypes="pkg">
								<h:outputText value="Source directory: #{srcDir.name}" />
							</tree:treeNode>
	
							<model:treeNodesAdaptor id="pkg" var="pkg" nodes="#{srcDir.packages}">
								<tree:treeNode dragType="pkg" acceptedTypes="class">
									<h:outputText value="Package: #{pkg.name}" />
								</tree:treeNode>
	
								<model:treeNodesAdaptor id="class" var="class" nodes="#{pkg.classes}">
									<tree:treeNode dragType="class">
										<h:outputText value="Class: #{class.name}" />
									</tree:treeNode>
								</model:treeNodesAdaptor>
							</model:treeNodesAdaptor>

						</model:treeNodesAdaptor>
						
						<model:recursiveTreeNodesAdaptor id="dir" var="dir"
							roots="#{project.dirs}" nodes="#{dir.directories}">
							<tree:treeNode dragType="dir" acceptedTypes="dir,file">
								<h:outputText value="Directory: #{dir.name}" />
							</tree:treeNode>
							
							<model:treeNodesAdaptor id="file" var="file" nodes="#{dir.files}">
								<tree:treeNode dragType="file">
									<h:outputText value="File: #{file.name}" />
								</tree:treeNode>
							</model:treeNodesAdaptor>

						</model:recursiveTreeNodesAdaptor>
					</model:treeNodesAdaptor>
				</tree:tree>

				<h:panelGroup id="trash" layout="block" style="border: 1px dotted navy; text-align: center; position: absolute; left: 450px; top: 25px; padding: 25px; background-color: white;">
					<h:outputText value="Trash" />
					<dnd:dropSupport  dropListener="#{loaderBean.trashDrop}" acceptedTypes="project,srcDir,pkg,class,dir,file">
						<dnd:dndParam name="label" value="Trash" />
					</dnd:dropSupport>
				</h:panelGroup>

			</h:form>
		</f:view>
	</body>	
</html>  
