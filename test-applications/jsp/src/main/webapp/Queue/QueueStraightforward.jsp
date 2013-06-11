<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<f:subview id="queueStraightforwardSubviewID">
	<h:panelGrid columns="3" id="panelID" border="1"
		style="text-align:center;">

		<h:outputText value="Component name" style="FONT-WEIGHT: bold;" />
		<h:outputText value="Component" style="FONT-WEIGHT: bold;" />
		<h:outputText value="Select eventsQueue" style="FONT-WEIGHT: bold;" />

		<h:outputText value="h:inputText" />
		<h:inputText value="#{queueComponent.inputValue}" id="inputID">
			<a4j:support id="inputSupport" event="onkeyup"
				eventsQueue="#{queueComponent.inputQueue}"></a4j:support>
		</h:inputText>

		<h:selectOneMenu value="#{queueComponent.inputQueue}"
			onchange="submit();">
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneMenu>

		<h:outputText value="rich:calendar" />
		<rich:calendar id="calendarID" value="#{queueComponent.calendarValue}"
			mode="ajax" reRender="panelID" eventsQueue="#{queueComponent.calendarQueue}">
		</rich:calendar>

		<h:selectOneMenu value="#{queueComponent.calendarQueue}"
			onchange="submit();">
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneMenu>

		<h:outputText value="h:selectBooleanCheckbox" />
		<h:selectBooleanCheckbox value="#{queueComponent.checkboxValue}"
			id="checkboxID">
			<a4j:support id="checkboxSupport" event="onchange"
				eventsQueue="#{queueComponent.checkboxQueue}"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:selectOneMenu value="#{queueComponent.checkboxQueue}"
			onchange="submit();">
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneMenu>

		<h:outputText value="rich:dataScroller" />
		<rich:datascroller for="dataTableId" reRender="dataTableId"
			boundaryControls="#{dataScroller.boundaryControls}"
			fastStep="#{dataScroller.fastStep}" id="dsID"
			page="#{dataScroller.page}"
			value="#{queueComponent.dataScrollerValue}"
			stepControls="#{dataScroller.stepControls}"
			eventsQueue="#{queueComponent.dataScrollerQueue}"
			fastControls="#{dataScroller.fastControls}"
			action="#{dataScroller.act}"
			actionListener="#{dataScroller.actListener}" maxPages="10"
			scrollerListener="#{dataScroller.ScrollerListener}">
		</rich:datascroller>

		<h:selectOneMenu value="#{queueComponent.dataScrollerQueue}"
			onchange="submit();">
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneMenu>

		<h:outputText value="h:selectOneRadio" />
		<h:selectOneRadio value="#{queueComponent.radioValue}">
			<a4j:support id="radioSupport" event="onblur"
				eventsQueue="#{queueComponent.radioQueue}"></a4j:support>
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneRadio>

		<h:selectOneMenu value="#{queueComponent.radioQueue}"
			onchange="submit();">
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneMenu>

		<h:outputText value="rich:dataFilterSlider" />
		<rich:dataFilterSlider sliderListener="#{dfs.doSlide}"
			action="#{dfs.act}" forValRef="dataScroller.dataTable"
			actionListener="#{dfs.actListener}" for="dataTableId"
			filterBy="getInt0" storeResults="true" startRange="0" endRange="999"
			increment="9" manualInput="true" width="400px" trailer="true"
			handleValue="999" id="dfsID" eventsQueue="#{queueComponent.dfsQueue}"></rich:dataFilterSlider>

		<h:selectOneMenu value="#{queueComponent.dfsQueue}"
			onchange="submit();">
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneMenu>

		<h:outputText value="h:selectOneMenu" />
		<h:selectOneMenu value="#{queueComponent.selectMenuValue}">
			<f:selectItem itemLabel="apple" itemValue="apple" />
			<f:selectItem itemLabel="kiwi" itemValue="kiwi" />
			<f:selectItem itemLabel="pineapple" itemValue="pineapple" />
			<a4j:support id="selectMenuSupport" event="onchange"
				eventsQueue="#{queueComponent.selectMenuQueue}"></a4j:support>
		</h:selectOneMenu>

		<h:selectOneMenu value="#{queueComponent.selectMenuQueue}"
			onchange="submit();">
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneMenu>

		<h:outputText value="rich:suggestionbox" />
		<h:panelGroup>
			<h:inputText value="#{queueComponent.suggestionValue}" id="text">
				<f:validateLength minimum="0" maximum="30" />
			</h:inputText>
			<rich:suggestionbox id="suggestionBoxId"			
				bypassUpdates="#{sb.bypassUpdates}"
				cellpadding="#{sb.cellpadding}" cellspacing="#{sb.cellspacing}"				
				eventsQueue="#{queueComponent.suggestionQueue}" fetchValue="#{result.text}"
				first="#{sb.first}" for="text"
				height="#{sb.height}"
				ignoreDupResponses="#{sb.ignoreDupResponses}"
				limitToList="false"
				minChars="#{sb.minchars}" nothingLabel="#{sb.nothingLabel}"				
				usingSuggestObjects="#{sb.usingSuggestObjects}"
				requestDelay="#{sb.requestDelay}"
				selfRendered="#{sb.selfRendered}" var="result"
				suggestionAction="#{sb.autocomplete}" width="#{sb.width}"				
				tokens="#{sb.tokens}">
				<h:column>
					<h:outputText value="#{result.city}" />
				</h:column>
				<h:column>
					<h:outputText value="#{result.contry}" />
				</h:column>
				<h:column>
					<h:outputText value="#{result.flag}" />
				</h:column>
				<h:column>
					<a4j:htmlCommandLink actionListener="#{sb.selectValue}"
						value="Click me!" />
				</h:column>
			</rich:suggestionbox>
		</h:panelGroup>

		<h:selectOneMenu value="#{queueComponent.suggestionQueue}"
			onchange="submit();">
			<f:selectItems value="#{queueComponent.queues}" />
		</h:selectOneMenu>

	</h:panelGrid>

	<rich:separator width="100%" height="10px"></rich:separator>
	<rich:dataTable id="dataTableId" value="#{dataScroller.dataTable}"
		var="dT" cellpadding="5px" rows="5" border="1"
		reRender="dsID,dataTableId" sortMode="#{dataScroller.sortMode}">
		<f:facet name="header">
			<h:outputText value="Header" />
		</f:facet>
		<f:facet name="footer">

		</f:facet>
		<rich:column sortBy="#{dT.str0}" filterBy="#{dT.str0}"
			filterEvent="onkeyup" selfSorted="#{dataScroller.selfSorted}"
			filterValue="#{dataScroller.filterValue}">
			<h:outputText value="#{dT.str0}" />
		</rich:column>
		<rich:column sortBy="#{dT.int0}" filterBy="#{dT.int0}"
			filterEvent="onkeyup" selfSorted="#{dataScroller.selfSorted}">
			<h:outputText value="#{dT.int0} " />
		</rich:column>
	</rich:dataTable>


</f:subview>