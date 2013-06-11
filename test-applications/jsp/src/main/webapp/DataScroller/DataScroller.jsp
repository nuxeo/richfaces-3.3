<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="DataScrollerSubviewID">

	<rich:dataTable id="dataTableId" value="#{dataScroller.dataTable}"
		var="dT" cellpadding="5px" rows="5" border="1"
		reRender="dsID,dataTableId" sortMode="#{dataScroller.sortMode}">
		<f:facet name="header">
			<rich:datascroller for="dataTableId"
				ajaxSingle="#{dataScroller.ajaxSingle}" reRender="dataTableId"
				boundaryControls="#{dataScroller.boundaryControls}"
				binding="#{dataScroller.htmlDatascroller}" data="datascrData"
				fastStep="#{dataScroller.fastStep}" id="dsID"
				page="#{dataScroller.page}" pagesVar="pages" pageIndexVar="index"
				value="#{dataScroller.value}" status="a4jStatusID"
				stepControls="#{dataScroller.stepControls}"
				eventsQueue="eventsQueue" inactiveStyle="#{style.inactiveStyle}"
				inactiveStyleClass="#{style.inactiveStyleClass}"
				selectedStyle="#{style.selectedStyle}"
				selectedStyleClass="#{style.selectedStyleClass}"
				style="#{style.style}" styleClass="#{style.styleClass}"
				tableStyle="#{style.tableStyle}"
				tableStyleClass="#{style.tableStyleClass}"
				fastControls="#{dataScroller.fastControls}"
				action="#{dataScroller.act}"
				actionListener="#{dataScroller.actListener}" align="center"
				rendered="#{dataScroller.render}"
				limitToList="#{dataScroller.limitToList}"
				renderIfSinglePage="#{dataScroller.renderIfSinglePage}"
				maxPages="#{dataScroller.maxPages}"
				scrollerListener="#{dataScroller.ScrollerListener}"
				onclick="#{event.onclick}" oncomplete="#{event.oncomplete}"
				ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}"
				onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
				onmousedown="#{event.onmousedown}"
				onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
				onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"
				lastPageMode="#{dataScroller.lastPageMode}">

			</rich:datascroller>
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
	<h:panelGrid id="dataScrollerActionID" columns="1">
		<a4j:commandButton value="Show action" reRender="dataScrollerActionID"
			style=" width : 95px;"></a4j:commandButton>
		<h:outputText value="#{dataScroller.action}" />
		<h:outputText value="#{dataScroller.actionListener}" />
	</h:panelGrid>
</f:subview>
