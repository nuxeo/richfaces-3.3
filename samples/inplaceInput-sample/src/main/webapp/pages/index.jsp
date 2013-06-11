<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/inplaceInput" prefix="ii"%>
<html>
	<head>
		<title>InplaceInput Sample</title>
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
			 		
			 		<h:outputText value="showControls (default: false): " />
					<h:selectBooleanCheckbox value="#{bean.showControls}"/>
				
					<h:outputText value="selectOnEdit (default: false): " />
					<h:selectBooleanCheckbox value="#{bean.selectOnEdit}" />
					
					<h:outputText value="defaultLabel is: " />
					<h:inputText value="#{bean.defaultLabel}" />
					
					<h:outputText value="value is: " />
					<h:inputText value="#{bean.value}" />
										
					<h:outputText value="width (in 'px'): "/>
					<h:inputText value="#{bean.width}" />
					
					<h:outputText value="minInputWidth (in 'px'): " />
					<h:inputText value="#{bean.minInputWidth}" />
					
					<h:outputText value="maxInputWidth (in 'px'): " />
					<h:inputText value="#{bean.maxInputWidth}" />
					
					<h:outputText value="controlsVerticalPosition (default: center) is: " />
					<h:inputText value="#{bean.controlsPosition}" />
					
					<h:outputText value="controlsHorizontalPosition (default: left) is: " />
					<h:inputText value="#{bean.controlsHorizontalAlign}" />
					
					<h:outputText value="tabindex: " />
					<h:inputText value="#{bean.tabindex}" />
					
					<h:outputText value="edit event script: " />
					<h:inputText value="#{bean.editEvent}" />
					
					<h:outputText value="oneditactivation event script:" />
					<h:inputText value="#{bean.oneditactivation}" />
					
					<h:outputText value="onviewactivation event script:" />
					<h:inputText value="#{bean.onviewactivation}" />
					
					<h:outputText value="oneditactivated event script:" />
					<h:inputText value="#{bean.oneditactivated}" />
					
					<h:outputText value="onviewactivated event script:" />
					<h:inputText value="#{bean.onviewactivated}" />
			 	</h:panelGrid>
			 	
			 		
				<div style="width: 300px">	
		       	Fresh off his victory in the Florida primary, Sen. John McCain is poised to take another big prize. Former
					<ii:inplaceInput 
						valueChangeListener="#{bean.valueChange}"
					 	selectOnEdit="#{bean.selectOnEdit}"
					 	showControls="#{bean.showControls}"
						inputWidth="#{bean.width}"
					 	minInputWidth="#{bean.minInputWidth}"
					 	maxInputWidth="#{bean.maxInputWidth}"
					 	controlsVerticalPosition="#{bean.controlsPosition}"
					 	controlsHorizontalPosition="#{bean.controlsHorizontalAlign}"
					 	editEvent="#{bean.editEvent}"
					 	oneditactivation="#{bean.oneditactivation}"
					 	onviewactivation="#{bean.onviewactivation}"
					 	oneditactivated="#{bean.oneditactivated}"
					 	onviewactivated="#{bean.onviewactivated}"
					 	saveControlIcon="#{bean.saveControlIcon}"
					 	cancelControlIcon="#{bean.cancelControlIcon}"
					 	tabindex="#{bean.tabindex}"
					 	layout="inline"					
					 	onchange="window.status='onchange';" 	
					>
					</ii:inplaceInput>	
					 Mayor Rudy Giuliani plans to drop out and endorse McCain, two GOP sources said. That would give McCain added momentum heading into a debate Wednesday and next week's Super Tuesday contests
				</div>
				<h:commandButton action="none" value="submit"></h:commandButton>
			</h:form>
		</f:view>
	</body>	
</html>  
