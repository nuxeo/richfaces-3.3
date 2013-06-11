<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/inplaceSelect" prefix="is"%>
<html>
      <head>
		<title>InplaceSelect Sample</title>
		<style>
			.tab_example {width : 300px; border-top:1px solid #c0c0c0; border-left:1px solid #c0c0c0;}
			.tab_example td{width : 150px;  border-bottom:1px solid #c0c0c0; border-right:1px solid #c0c0c0;}
		</style>
	  </head>
	  <body>
	  	<f:view>
	  		<h:form>
				<h:selectOneRadio binding="#{skinBean.component}" />
				<h:commandLink action="#{skinBean.change}" value="set skin" />
				<h:outputText value="Current skin: #{skinBean.skin}"/><br />
			</h:form>
	  		<h:form>
	  			<h:panelGrid columns="2">
	  				<h:outputText value="editEvent is: "></h:outputText>
	  				<h:inputText value="#{bean.editEvent}"></h:inputText>
	  				
	  				<h:outputText value="maxSelectWidth is: "></h:outputText>
	  				<h:inputText value="#{bean.maxSelectWidth}"></h:inputText>
	  				
	  				<h:outputText value="minSelectWidth is: "></h:outputText>
	  				<h:inputText value="#{bean.minSelectWidth}"></h:inputText>
	  				
	  				<h:outputText value="selectWidth is: "></h:outputText>
	  				<h:inputText value="#{bean.selectWidth}"></h:inputText>
	  				
	  				<h:outputText value="defaultLabel is: "></h:outputText>
	  				<h:inputText value="#{bean.defaultLabel}"></h:inputText>
	  				
	  				<h:outputText value="controlsVerticalPosition is: "></h:outputText>
	  				<h:inputText value="#{bean.controlsPosition}"></h:inputText>
	  				
	  				<h:outputText value="controlsHorizontalPosition is: "></h:outputText>
	  				<h:inputText value="#{bean.controlsHorizontalAlign}"></h:inputText>
	  				
	  				<h:outputText value="value is: "></h:outputText>
	  				<h:inputText value="#{bean.value}"></h:inputText>
	  				
	  				<h:outputText value="listWidth is: "></h:outputText>
	  				<h:inputText value="#{bean.listWidth}"></h:inputText>
	  				
	  				<h:outputText value="listHeight is: "></h:outputText>
	  				<h:inputText value="#{bean.listHeight}"></h:inputText>
	  				
	  				<h:outputText value="selectOnEdit (default: false): " />
					<h:selectBooleanCheckbox value="#{bean.selectOnEdit}" />
	  				
	  				<h:outputText value="showControls (default: false): " />
					<h:selectBooleanCheckbox value="#{bean.showControls}" />
					
	  				<h:outputText value="editOnTab (default: true): " />
					<h:selectBooleanCheckbox value="#{bean.editOnTab}" />
					
					<h:outputText value="openOnEdit (default: false): " />
					<h:selectBooleanCheckbox value="#{bean.openOnEdit}" />
	  			  			  			
	  			</h:panelGrid>
	  			  		
	  			
	  			Fresh off his victory in the Florida primary, Sen. John McCain is poised to take another big prize. Former
	  			    <is:inplaceSelect 
						id="inplace"
	  			    	editEvent="onclick"
	  			    	maxSelectWidth="#{bean.maxSelectWidth}"
	  			    	minSelectWidth="#{bean.minSelectWidth}"
	  			    	defaultLabel="click"
	  			    	controlsVerticalPosition="#{bean.controlsPosition}"
	  			    	controlsHorizontalPosition="#{bean.controlsHorizontalAlign}"
	  			    	listWidth="#{bean.listWidth}"
	  			    	listHeight="#{bean.listHeight}"
	  			    	showControls="#{bean.showControls}"
	  			    	openOnEdit="true"
						layout="inline"
						value="#{bean.value}"
						saveControlIcon="images/ico_cancel.gif"
						cancelControlIcon="images/ico_ok.gif">
						<f:selectItem itemLabel="option 1" itemValue="Kansas City"/>
						<f:selectItem itemLabel="option 2" itemValue="Las Vegas"/>
						<f:selectItem itemLabel="option 3" itemValue="Oklahoma City"/>
						<f:selectItem itemLabel="option 4" itemValue="New Jersey"/>
						<f:selectItem itemLabel="option 5" itemValue="Detroit"/>
						<f:selectItem itemLabel="option 6" itemValue="Toronto"/>
						<f:selectItem itemLabel="option 7" itemValue="Cleveland"/>
						<f:selectItem itemLabel="option 8" itemValue="Indianapolis"/>
						<f:selectItem itemLabel="A&'<laska" itemValue="A&'<laska"/>
						
					</is:inplaceSelect>
					
					
					<h:messages></h:messages> 
					
				 Mayor Rudy Giuliani plans to drop out and endorse McCain, two GOP sources said. That would give McCain added momentum heading into a debate Wednesday and next week's Super Tuesday contests
				
				<br/>
				<h:outputText id="text" value="value is: #{bean.value}"></h:outputText> <br/>
				<a4j:commandButton reRender="inplace, text" value="Ajax"></a4j:commandButton>
				<h:commandButton value="Server"></h:commandButton>
			</h:form>
		</f:view>
	  </body>	
</html>  
