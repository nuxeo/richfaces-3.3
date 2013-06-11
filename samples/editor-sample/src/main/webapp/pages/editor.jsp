<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/editor" prefix="ed" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<html>
	<head>
		<title></title>
		<style>
			.myStyle{
				border:1px solid;
			
			}
		</style>
    </head>
	<body>
		<f:view >
			<h:form>
			<h:panelGrid columns="1">
					<h:selectOneRadio binding="#{skinBean.component}" />
		            <h:commandLink action="#{skinBean.change}" value="set skin" />
		            <h:outputText value="Current Skin: #{skinBean.skin}"/>
		        </h:panelGrid>
		    </h:form>    		
			<h:form id="formId">
				
				
				<ed:editor id="editorId"
						   value="#{editorBean.value}" 
						   width="600" 
						   height="400"						   
						   rendered="#{editorBean.rendered}"						   
						   binding="#{editorBean.editor}"						   
						   theme="#{editorBean.theme}" 
						   language="#{editorBean.language}"
						   configuration="editorFull"
						   skin="#{editorBean.skin}"
						   customPlugins="myplugins"
						   readonly="#{editorBean.readonly}"
						   viewMode="#{editorBean.viewMode}"
						   plugins="safari,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,myemotions"
						   >
					</ed:editor>
					
					<hr/>
					<h:panelGrid columns="2">
						<h:outputText value="Select theme:" />						   
						<h:selectOneRadio onchange="submit()" value="#{editorBean.theme}">
							<f:selectItem itemLabel="simple" itemValue="simple"/>
							<f:selectItem itemLabel="advanced" itemValue="advanced"/>
						</h:selectOneRadio>
						<h:outputText value="Select skin:" />						   
						<h:selectOneRadio onchange="submit()" value="#{editorBean.skin}">
							<f:selectItem itemLabel="default" itemValue="default"/>
							<f:selectItem itemLabel="o2k7" itemValue="o2k7"/>
							<f:selectItem itemLabel="richfaces" itemValue="richfaces"/>
						</h:selectOneRadio>	
						<h:outputText value="Select language:" />
						<h:selectOneRadio onchange="submit()" value="#{editorBean.language}">
							<f:selectItem itemLabel="english" itemValue="en"/>
							<f:selectItem itemLabel="russian" itemValue="ru"/>
							<f:selectItem itemLabel="french" itemValue="fr"/>
						</h:selectOneRadio>	
						<h:outputText value="Is readonly:" />
							<h:selectBooleanCheckbox value="#{editorBean.readonly}" onclick="submit()">
						</h:selectBooleanCheckbox>				
						<h:outputText value="View mode:" />
						<h:selectOneRadio onchange="submit()" value="#{editorBean.viewMode}">
							<f:selectItem itemLabel="vision" itemValue="vision"/>
							<f:selectItem itemLabel="source" itemValue="source"/>
						</h:selectOneRadio>
					</h:panelGrid>
					<hr/>								
				
				<h:outputText value="Editor value: #{editorBean.value}" />		
				<br/>
				<h:commandButton value="h:commandButton" action="#{editorBean.action1}"/>
				<a4j:commandButton value="a4j:commandButton" action="#{editorBean.action1}"
					reRender="editorId"/>
				<br/>						
				<h:commandButton value="h:commandButton2" action="#{editorBean.action2}"/>
				<a4j:commandButton value="a4j:commandButton2" action="#{editorBean.action2}"
					reRender="editorId"/>
					
				<br/>						
				<h:commandButton value="change rendered" action="#{editorBean.changeRendered}"/>
			</h:form>		          
		</f:view>
	</body>	
</html>  
