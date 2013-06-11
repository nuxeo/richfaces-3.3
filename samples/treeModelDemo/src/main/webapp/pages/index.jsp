<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/treeModel" prefix="model" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tree" prefix="tree" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/drag-drop" prefix="dnd" %>

<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			<h:outputLink value="#{facesContext.externalContext.requestContextPath}/pages/rf-1081.jsf">RF-1081 (DnD with tree adaptors) Demo</h:outputLink>
			<h:form>
				<a4j:outputPanel ajaxRendered="true">
					<h:messages />
				</a4j:outputPanel>
			
				<h:inputText required="true" value="val"/>
				
				<tree:tree adviseNodeOpened="#{treeModelBean.adviseNodeOpened}" switchType="client">
					<model:treeNodesAdaptor id="project" nodes="#{loaderBean.projects}" var="project">
						<tree:treeNode>
					    	<a4j:commandLink action="#{project.click}" ajaxSingle="true" value="AJAX Single " /><br />
					    	<h:commandLink action="#{project.click}" value="Project: #{project.name}" />
					    </tree:treeNode>
	
						<model:treeNodesAdaptor id="srcDir" var="srcDir" nodes="#{project.srcDirs}">
							<tree:treeNode>
						    	<a4j:commandLink action="#{srcDir.click}" ajaxSingle="true" value="AJAX Single " /><br />
								<h:commandLink action="#{srcDir.click}" value="Source directory: #{srcDir.name}" />
							</tree:treeNode>
	
							<model:treeNodesAdaptor id="pkg" var="pkg" nodes="#{srcDir.packages}">
								<tree:treeNode>
							    	<a4j:commandLink action="#{pkg.click}" ajaxSingle="true" value="AJAX Single " /><br />
									<h:commandLink action="#{pkg.click}" value="Package: #{pkg.name}" />
								</tree:treeNode>
	
								<model:treeNodesAdaptor id="class" var="class" nodes="#{pkg.classes}">
									<tree:treeNode>
								    	<a4j:commandLink action="#{class.click}" ajaxSingle="true" value="AJAX Single " /><br />
										<h:commandLink action="#{class.click}" value="Class: #{class.name}" />
									</tree:treeNode>
								</model:treeNodesAdaptor>
							</model:treeNodesAdaptor>

							<model:treeNodesAdaptor id="pkg1" var="pkg" nodes="#{srcDir.packages}">
								<tree:treeNode>
							    	<a4j:commandLink action="#{pkg.click}" ajaxSingle="true" value="AJAX Single " /><br />
									<h:commandLink action="#{pkg.click}" value="Package1: #{pkg.name}" />
								</tree:treeNode>
	
								<model:treeNodesAdaptor id="class1" var="class" nodes="#{pkg.classes}">
									<tree:treeNode>
								    	<a4j:commandLink action="#{class.click}" ajaxSingle="true" value="AJAX Single " /><br />
										<h:commandLink action="#{class.click}" value="Class1: #{class.name}" />
									</tree:treeNode>
								</model:treeNodesAdaptor>
							</model:treeNodesAdaptor>
						</model:treeNodesAdaptor>
						
						<model:recursiveTreeNodesAdaptor id="dir" var="dir"
							roots="#{project.dirs}" nodes="#{dir.directories}" binding="#{treeModelBean.binding}">
							<tree:treeNode>
						    	<a4j:commandLink action="#{dir.click}" ajaxSingle="true" value="AJAX Single " /><br />
								<h:commandLink action="#{dir.click}" value="Directory: #{dir.name}" />
							</tree:treeNode>
							
							<model:treeNodesAdaptor id="file" var="file" nodes="#{dir.files}">
								<tree:treeNode>
							    	<a4j:commandLink action="#{file.click}" ajaxSingle="true" value="AJAX Single " />
							    	<br />
									<h:commandLink action="#{file.click}" value="File: #{file.name}" />
								</tree:treeNode>
							</model:treeNodesAdaptor>
							
							<model:treeNodesAdaptor id="file1" var="file" nodes="#{dir.files}">
								<tree:treeNode>
							    	<a4j:commandLink action="#{file.click}" ajaxSingle="true" value="AJAX Single " />
							    	<br />
									<h:commandLink action="#{file.click}" value="File1: #{file.name}" />
								</tree:treeNode>
							</model:treeNodesAdaptor>

							<model:recursiveTreeNodesAdaptor id="archiveEntry" var="archiveEntry"
								roots="#{dir.files}" nodes="#{archiveEntry.archiveEntries}" 
								includedRoot="#{archiveEntry.class.simpleName == 'ArchiveFile'}"
								includedNode="#{archiveEntry.class.simpleName == 'ArchiveEntry'}">
							
								<tree:treeNode id="archiveEntryNode">
							    	<a4j:commandLink action="#{archiveEntry.click}" ajaxSingle="true" value="AJAX Single " />
							    	<br />
									<h:commandLink action="#{archiveEntry.click}" value="Archive entry: #{archiveEntry.name}" />
								</tree:treeNode>

							</model:recursiveTreeNodesAdaptor>

						</model:recursiveTreeNodesAdaptor>
	
						<%--model:treeNodes var="file" nodes="#{proj.dir.files}">
							<tree:treeNode>
								<h:outputText value="File: #{file.name}" />
							</tree:treeNode>
						</model:treeNodes--%>
					</model:treeNodesAdaptor>
				</tree:tree>

				<tree:tree adviseNodeOpened="#{treeModelBean.adviseNodeOpened}" switchType="client">
					<model:recursiveTreeNodesAdaptor roots="#{treeModelBean.simpleResursiveNodes}" var="node" nodes="#{node.children}">
						<tree:treeNode>
							<h:commandLink value="#{node.text}" action="#{node.remove}" />
						</tree:treeNode>
					</model:recursiveTreeNodesAdaptor>
				</tree:tree>
				
				<h3> treeNode ondropend and oncomplete never executed using DnD</h3>
				
				<a4j:outputPanel id="treeWrapper" layout="block">
                <tree:tree id="portTreePaneId" style="width:200px" 
                           switchType="ajax" ajaxSubmitSelection="true" 
                           dragIndicator=":form:indicator" >
                    <model:treeNodesAdaptor nodes="#{loaderBean.projects}" var="profile" >
                        <tree:treeNode acceptedTypes="pidDrag" 
                                       dropValue="#{profile.name}" 
				                       typeMapping="{pidDrag:pidDrag}" 
				                       oncomplete="out('complete');"
				                       dragType="pidDrag" 
                                       dropListener="#{treeModelBean.processDrop}"
                                       ondrop="out('ondrop');"
				                       ondropover="out('ondropover');"
				                       ondropend="out('ondropend');"
				                       ondropout="out('ondropout');"
				                       
				                       ondragenter="out('ondragenter')"
				                       ondragend="out('ondragend')"
				                       ondragexit="out('ondragexit')"
				                       ondragstart="out('ondragstart')"
				                       >
                            <a4j:outputPanel layout="block">
                                <h:outputText value="#{profile.name}" />
                            </a4j:outputPanel>      
                        </tree:treeNode>
         
                        <model:treeNodesAdaptor nodes="#{profile.srcDirs}" var="pid" >
                            <tree:treeNode dragType="pidDrag" dragValue="#{pid.name}">
                                <dnd:dndParam name="label" type="drag" value="dragged pid" />
                                <a4j:outputPanel layout="block">
                                    <h:outputText value="#{pid.name}" />
                                </a4j:outputPanel>
                            </tree:treeNode>
                        </model:treeNodesAdaptor>
                    </model:treeNodesAdaptor>
                </tree:tree>    
                
                </a4j:outputPanel>
				
			</h:form>
		</f:view>
		
		<textarea id="debugOut" rows="5" cols="120"></textarea>
		<script type="text/javascript">
		  function out(str){
			  debugOut = document.getElementById("debugOut")
			  debugOut.value += str + "\n"
		  }
		</script>
	</body>	
</html>  
