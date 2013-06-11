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
				
			<ed:editor binding="#{miracleBean.component}" 
						   plugins="safari,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,myemotions">
			</ed:editor>

			<h:panelGrid columns="2" binding="#{miracleBean.containerComponent}" />

					
			</h:form>		          
		</f:view>
	</body>	
</html>  
