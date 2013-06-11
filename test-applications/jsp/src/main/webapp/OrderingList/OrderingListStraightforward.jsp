<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="orderingListStraightforwardSubviewID">
	
		<h:panelGrid columns="3">
			<h:outputText value="Test1" />
			<a4j:commandButton action="#{orderingList.bTest1}" value="run" reRender="richGridID"></a4j:commandButton>
			<h:outputText value="#{msg.t1OrderingList}" />

			<h:outputText value="Test2" />
			<a4j:commandButton action="#{orderingList.bTest2}" value="run" reRender="richGridID"></a4j:commandButton>
			<h:outputText value="#{msg.t2OrderingList}" />

			<h:outputText value="Test3" />
			<a4j:commandButton action="#{orderingList.bTest3}" value="run" reRender="richGridID"></a4j:commandButton>
			<h:outputText value="#{msg.t3OrderingList}" />

			<h:outputText value="Test4" />
			<a4j:commandButton action="#{orderingList.bTest4}" value="run" reRender="richGridID"></a4j:commandButton>
			<h:outputText value="#{msg.t4OrderingList}" />

			<h:outputText value="Test5" />
			<a4j:commandButton action="#{orderingList.bTest5}" value="run" reRender="richGridID"></a4j:commandButton>
			<h:outputText value="#{msg.t5OrderingList}" />

			<a4j:commandButton value="Show selection (reRender)" reRender="infoPanelID"></a4j:commandButton>
			<h:column>
				<h:selectBooleanCheckbox value="#{orderingList.showSelect}" onclick="submit();" />
			</h:column>
			<h:column>
				<h:dataTable id="infoPanelID" value="#{orderingList.info}" var="info" rendered="#{orderingList.showSelect}" border="1">
					<h:column>
						<h:outputText value="#{info}" />
					</h:column>
				</h:dataTable>
			</h:column>

			<a4j:commandButton value="Show all data (reRender)" reRender="allInfoPanelID"></a4j:commandButton>
			<h:column>
				<h:selectBooleanCheckbox value="#{orderingList.showAllData}" onclick="submit();" />
			</h:column>
			<h:column>
				<h:dataTable id="allInfoPanelID" value="#{orderingList.list}" var="allInfo" rendered="#{orderingList.showAllData}" border="1">
					<h:column>
						<f:facet name="header">
							<h:outputText value="Number" />
						</f:facet>
						<h:outputText value="#{allInfo.int0}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="Input" />
						</f:facet>
						<h:outputText value="#{allInfo.str0}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="Button" />
						</f:facet>
						<h:outputText value="#{allInfo.str0} submit();" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="Input" />
						</f:facet>
						<h:outputText value="#{allInfo.str1}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="Link" />
						</f:facet>
						<h:outputText value="#{allInfo.str1} submit()" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="select" />
						</f:facet>
						<h:outputText value="#{allInfo.str2}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="Text" />
						</f:facet>
						<h:outputText value="#{allInfo.str3}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="graphicImage" />
						</f:facet>
						<h:outputText value="#{allInfo.str3}" />
					</h:column>
				</h:dataTable>
			</h:column>

			<h:panelGroup>
				<a4j:commandButton value="reRender" reRender="orderingListID"></a4j:commandButton>
				<a4j:commandButton immediate="true" reRender="orderingListID" value="immediate submit(); (a4j)"></a4j:commandButton>
				<h:commandButton value="submit();" />
				<h:commandButton immediate="true" value="immediate submit();" />
			</h:panelGroup>
		</h:panelGrid>

</f:subview>