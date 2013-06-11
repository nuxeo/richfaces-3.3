<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/scrollableDataTable" prefix="sg"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="dt"%>
<html>
     <head>
    	<style type="text/css">
    		.inputStyle{
    			font-family:arial;
				font-size:8pt;
				font-size-adjust:none;
				font-stretch:normal;
				font-style:normal;
				font-variant:normal;
				font-weight:normal;
				line-height:normal;
    		}
    		
    		.rich-sdt-row {
    			height: 30px;
    		}
    		
    		.col {
    			color: red;
    		}
    		.sdt_col { 
				font-family: Tahoma; 
				font-size: 11px; 
				color: #000000; 
				text-decoration: none; 
				text-align: center; 
				vertical-align: middle; 
				//padding-left: 4px; 
				//padding-right: 4px; 
			}
    		
    		
    		
    	</style>
     </head>
    
    
     <body>
   	 <f:view>
   	 <h:form>
   	 	<h:selectOneRadio binding="#{skinBean.component}" />
        <h:commandLink action="#{skinBean.change}" value="set skin" />
   	 </h:form>
     <h:form>
		
	     <sg:scrollableDataTable value="#{jiraService.channel.issues}" 
	     					 var="issues" 
	     					 first="0"
	     					 rows="30" 
	     					 frozenColCount="3"
	     					 width="800px"
	     					 columnClasses="sdt_col"
	     					 height="500px" hideWhenScrolling="false"> 
	     	
	         	
	      	<dt:column width="10px" sortable="false">
     		
     			<f:facet name="header">
     				<h:outputText value="Index"></h:outputText>
     			</f:facet>
     			
     			<h:outputText value="#{issues.index}"></h:outputText>
     			
     			<f:facet name="footer">
     				<h:outputText value="footer0"></h:outputText>
     			</f:facet>
     		</dt:column>
     		
     		<dt:column width="200px" sortExpression="#{issues.key.value}">
     		
     			<f:facet name="header">
	   				<h:outputText value="Key"></h:outputText>
     			</f:facet>
     			
				<h:outputText value="#{issues.key.value}"></h:outputText>
				
     			<f:facet name="footer">
     				<h:outputText value="footer1"></h:outputText>
     			</f:facet>
     		</dt:column>
     		
     		
     		<dt:column width="200px" sortExpression="#{issues.summary}">
     			
     			<f:facet name="header">
	   				<h:outputText value="Summary"></h:outputText>
     			</f:facet>
				
				<h:outputText value="#{issues.summary}"></h:outputText>
       			
       			<f:facet name="footer">
     				<h:outputText value="footer2"></h:outputText>
     			</f:facet>
    		</dt:column>
    		
    		<dt:column width="200px">
    			
    			<f:facet name="header">
					<h:outputText value="Assignee"></h:outputText>
     			</f:facet>
				
				<h:inputText value="#{issues.assignee}" converter="#{jiraUserConverter}" styleClass="inputStyle"></h:inputText>
     			
     			<f:facet name="footer">
     				<h:outputText value="footer3"></h:outputText>
     			</f:facet>
	    	</dt:column>
	    	
    	 	<dt:column width="200px">
     			
     			<f:facet name="header">
     				<h:outputText value="Status"></h:outputText>
     			</f:facet>
     			
     			<h:selectOneMenu id="select_status" value="#{issues.status}" converter="#{StatusConverter}" styleClass="inputStyle">
     				<f:selectItem itemValue="#{status_open}" itemLabel="Open"/>			
     				<f:selectItem itemValue="#{status_closed}" itemLabel="Closed"/>			
     				<f:selectItem itemValue="#{status_resolved}" itemLabel="Resolved"/>			
    				<f:selectItem itemValue="#{status_inprogress}" itemLabel="In Progress"/>			
    				<f:selectItem itemValue="#{status_reopened}" itemLabel="Reopened"/>		
				</h:selectOneMenu> 
				
				<f:facet name="footer">
     				<h:outputText value="footer4"></h:outputText>
	     		</f:facet>
	     	</dt:column>
	     	
    	 	<dt:column width="200px">
     			
     			<f:facet name="header">
	    			<h:outputText value="Reporter"></h:outputText>
     			</f:facet>
     			
				<h:outputText value="#{issues.reporter.username}"></h:outputText>
				
     			<f:facet name="footer">
     				<h:outputText value="footer5"></h:outputText>
     			</f:facet>
	     	</dt:column>
	     	
    	 	<dt:column width="200px">
     			
     			<f:facet name="header">
	    			<h:outputText value="Priority"></h:outputText>
     			</f:facet>
     			
     			<h:selectOneMenu id="select_priority" value="#{issues.priority}" converter="#{PriorityConverter}" styleClass="inputStyle">
     				<f:selectItem itemValue="#{priority_blocker}" itemLabel="Blocker"/>			
     				<f:selectItem itemValue="#{priority_critical}" itemLabel="Critical"/>			
     				<f:selectItem itemValue="#{priority_major}" itemLabel="Major"/>			
    				<f:selectItem itemValue="#{priority_minor}" itemLabel="Minor"/>			
    				<f:selectItem itemValue="#{priority_cosmetic}" itemLabel="Cosmetic"/>		
				</h:selectOneMenu> 
     			
     			<f:facet name="footer">
     				<h:outputText value="footer6"></h:outputText>
     			</f:facet>
	    	</dt:column>
	    	
	       	<dt:column width="200px">
    	 		
    	 		<f:facet name="header">
	    			<h:outputText value="Resolution"></h:outputText>
     			</f:facet>
				
				<h:inputText value="#{issues.resolution}" styleClass="inputStyle"></h:inputText>
     			
     			<f:facet name="footer">
     				<h:outputText value="footer8"></h:outputText>
     			</f:facet>
     		</dt:column>
     	</sg:scrollableDataTable>
     	<h:commandButton value="submit"></h:commandButton>
   	   	<h:messages showDetail="true"/>
     </h:form>
     
     <h:outputLink target="_blank" value="resource:///org/ajax4jsf/javascript/jsshell.html">Console</h:outputLink>
     
     
     </f:view>
    </body>
</html>	
