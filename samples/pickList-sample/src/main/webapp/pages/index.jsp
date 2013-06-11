<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/pickList" prefix="pickList"%>
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
			
				<h:panelGrid columns="8">
				
					<h:outputText value="set target list width: "></h:outputText>
					<h:inputText value="#{pickBean.targetListWidth}"></h:inputText>	
					
					<h:outputText value="set 'CopyAll' control label: "></h:outputText>
					<h:inputText value="#{pickBean.copyAllLabel}"></h:inputText>
					
					<h:outputText value="copyAll control visible"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.copyAllVisible}"></h:selectBooleanCheckbox>
					
					<h:outputText value="set move controls visible"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.moveControlsVisible}"></h:selectBooleanCheckbox>
					
					<h:outputText value="set source list width: "></h:outputText>
					<h:inputText value="#{pickBean.sourceListWidth}"></h:inputText>					
					
					<h:outputText value="set 'Copy' control label: "></h:outputText>
					<h:inputText value="#{pickBean.copyLabel}"></h:inputText>
					
					<h:outputText value="copy control visible"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.copyVisible}"></h:selectBooleanCheckbox>
					
					<h:outputText value="set fast move controls visible"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.fastMoveControlsVisible}"></h:selectBooleanCheckbox>
					
					<h:outputText value="set list heights: "></h:outputText>
					<h:inputText value="#{pickBean.listsHeight}"></h:inputText>

					<h:outputText value="set 'Remove' control label: "></h:outputText>
					<h:inputText value="#{pickBean.removeLabel}"></h:inputText>
					
					<h:outputText value="remove control visible"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.removeVisible}"></h:selectBooleanCheckbox>
					
					<h:outputText value="switch By Click"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.switchByClick}"></h:selectBooleanCheckbox>
					
					<h:outputText value="set disabled"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.disabled}"></h:selectBooleanCheckbox>

					<h:outputText value="set 'RemoveAll' control label: "></h:outputText>
					<h:inputText value="#{pickBean.removeAllLabel}"></h:inputText>								
					
					<h:outputText value="removeAll control visible"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.removeAllVisible}"></h:selectBooleanCheckbox>
					
					<h:outputText value="switch By DblClick"></h:outputText>
					<h:selectBooleanCheckbox value="#{pickBean.switchByDblClick}"></h:selectBooleanCheckbox>
					
				</h:panelGrid>
			
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				
			<pickList:pickList  valueChangeListener="#{pickBean.selectionChanged}"
				listsHeight="#{pickBean.listsHeight}"  
				sourceListWidth="#{pickBean.sourceListWidth}"
				targetListWidth="#{pickBean.targetListWidth}"
				copyAllControlLabel = "#{pickBean.copyAllLabel}"
                copyAllTitle="test copyAllTitle"
				copyControlLabel = "#{pickBean.copyLabel}"
                copyTitle="test copyTitle"
				removeControlLabel = "#{pickBean.removeLabel}"
				removeAllControlLabel ="#{pickBean.removeAllLabel}"
				value="#{pickBean.listValues}"
				showButtonsLabel="true"
				moveControlsVisible="#{pickBean.moveControlsVisible}"
				fastMoveControlsVisible="#{pickBean.fastMoveControlsVisible}"
				copyAllVisible="#{pickBean.copyAllVisible}"
				copyVisible="#{pickBean.copyVisible}"
				removeVisible="#{pickBean.removeVisible}"
				removeAllVisible="#{pickBean.removeAllVisible}"
				switchByClick="#{pickBean.switchByClick}"
				switchByDblClick="#{pickBean.switchByDblClick}"
				disabled="#{pickBean.disabled}"
				>
				<f:selectItems value="#{pickBean.testList}"/>
				
			</pickList:pickList>
			
			<h:commandButton value="Submit" action="none"/>
	        </h:form>
	         <h:outputText value="#{pickBean.selectedInfo}"/>
	        
		</f:view>
	</body>	
</html>  
