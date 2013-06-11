<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/editor" prefix="ed"%>
<html>
	<head>
		<title>Richfaces Editor Sample</title>
    </head>
	<body>
		<f:view >		
			<h:form id="formId">
				
				<ed:editor id="editorId"
						   value="#{editorBean.value}" 
						   width="320" 
						   height="270"						   
						   rendered="#{editorBean.rendered}"						   
						   binding="#{editorBean.editor}"						   
						   theme="advanced" 
						   useSeamText="true"
						   language="ru"
						   plugins="safari,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template"
						   configuration="editorParameters"
						   />								
				
				<h:commandButton value="h:commandButton"/>
				<a4j:commandButton value="a4j:commandButton" reRender="editorId, seamtext"/>
				<br/>						
				<br/>						
				<h:commandButton value="change rendered" action="#{editorBean.changeRendered}"/>
				<h:panelGrid columns="1" id="seamtext">
					<f:facet name="header">
						<h:outputText value="Generated Seam Text"/>
					</f:facet>
					<h:inputTextarea readonly="true" cols="80" rows="20" value="#{editorBean.value}"/>
				</h:panelGrid>
			</h:form>		          
		</f:view>
	</body>	
</html>  
