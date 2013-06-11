<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/combobox" prefix="cmb"%>
<html>
	<head>
		<title></title>
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
					<h:outputText value="is disable (default: false):" />
					<h:selectBooleanCheckbox value="#{bean.disabled}" />

					<h:outputText value="enableManualInput (default: false):" />
					<h:selectBooleanCheckbox value="#{bean.enableManualInput}" />
					
					<h:outputText value="selectFirstOnUpdate (default: true):" />
					<h:selectBooleanCheckbox value="#{bean.selectFirstOnUpdate}" />
					
					<h:outputText value="filterNewValues (default: true):" />
					<h:selectBooleanCheckbox value="#{bean.filterNewValues}" />
					
					<h:outputText value="directInputSuggestions (default: true):" />
					<h:selectBooleanCheckbox value="#{bean.directInputSuggestions}" />
					
					<h:outputText value="defaultMessage is:" />
					<h:inputText value="#{bean.defaultMessage}" />
					
					<h:outputText value="width (in 'px'):" />
					<h:inputText value="#{bean.width}" />
					
					<h:outputText value="listWidth (in 'px'):" />
					<h:inputText value="#{bean.listWidth}" />
					
					<h:outputText value="listHeight (in 'px'):" />
					<h:inputText value="#{bean.listHeight}" />
					
					<h:outputText value="inputSize:" />
					<h:inputText value="#{bean.inputSize}" />
					
					<h:outputText value="onchange event script:" />
					<h:inputText value="#{bean.onchangeScript}" />
					
					<h:outputText value="onlistcall event script:" />
					<h:inputText value="#{bean.onlistcallScript}" />
					
					<h:outputText value="onitemselected event script:" />
					<h:inputText value="#{bean.onitemselectedScript}" />
				
				</h:panelGrid>
				
														
				<cmb:comboBox 
					disabled="#{bean.disabled}" 
					inputClass="inputClass" 
					buttonDisabledClass="buttonDisabledClass" 
					buttonClass="buttonClass" 
					listClass="listClass" 
					value="#{bean.state}" 
					valueChangeListener="#{bean.selectionChanged}"  
					suggestionValues="#{bean.suggestions}"
					width = "#{bean.width}" 
					listWidth="#{bean.listWidth}"
					listHeight="#{bean.listHeight}"
					enableManualInput="#{bean.enableManualInput}"
					selectFirstOnUpdate="#{bean.selectFirstOnUpdate}"
					filterNewValues="#{bean.filterNewValues}"
					directInputSuggestions="#{bean.directInputSuggestions}"
					defaultLabel="#{bean.defaultMessage}"
					onchange="#{bean.onchangeScript}"
					onlistcall="#{bean.onlistcallScript}">
					<f:selectItems  value="#{bean.selectItems}"/>
					<f:selectItem itemValue="Oregon"/>			  
					<f:selectItem itemValue="Pennsylvania Oregonfdsf fdsfdsfds dsffdsfdsfdsf fdsfdsfwdsfsfsd fdsfdsfdsfds dfsfdsfdsfds"/>
					<f:selectItem itemValue="Rhode Island"/>
					<f:selectItem itemValue="South Carolina"/>
					<f:selectItem itemValue="J&'<uneau"/>
					
				</cmb:comboBox>
				
				<br/>
				<h:commandButton action="none" value="submit"></h:commandButton>
				<br>
				<h:outputText value="Selected state is: #{bean.state}"></h:outputText>
			</h:form>
		</f:view>
	</body>	
</html>  
