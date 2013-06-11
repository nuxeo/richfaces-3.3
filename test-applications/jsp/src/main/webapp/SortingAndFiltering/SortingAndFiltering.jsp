<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<f:subview id="sortingAndFilteringSubviewID">

	<h:panelGrid columns="9" border="1" style="font-size:12px">
		<h:outputText value="#1"></h:outputText>
		<h:outputText value="#2"></h:outputText>
		<h:outputText value="#3"></h:outputText>
		<h:outputText value="#4"></h:outputText>
		<h:outputText value="#5"></h:outputText>
		<h:outputText value="#6"></h:outputText>
		<h:outputText value="#7"></h:outputText>
		<h:outputText value="#8"></h:outputText>
		<h:outputText value="#9"></h:outputText>
		<h:panelGrid columns="1" title="1">
			<h:outputText value="sortBy"></h:outputText>
			<h:outputText value="selfSorted='#{sortingAndFiltering.selfSorted}'"></h:outputText>
			<h:outputText value="sortOrder='#{sortingAndFiltering.sortOrder}'"></h:outputText>
			<h:outputText value="filterBy"></h:outputText>
		</h:panelGrid>
		<h:panelGrid columns="1" title="2">
			<h:outputText value="sortBy"></h:outputText>
			<h:outputText value="selfSorted='#{sortingAndFiltering.selfSorted}'"></h:outputText>
			<h:outputText value="filterBy"></h:outputText>
			<h:outputText value="filterEvent='ondblclick'" />
		</h:panelGrid>
		<h:panelGrid columns="1" title="3">
			<h:outputText value="sortBy"></h:outputText>
			<h:outputText value="sortOrder='#{sortingAndFiltering.sortOrder}'"></h:outputText>
			<h:outputText value="filterMethod" />
		</h:panelGrid>
		<h:panelGrid columns="1" title="4">
			<h:outputText value="selfSorted='#{sortingAndFiltering.selfSorted}'"></h:outputText>
			<h:outputText value="filterBy"></h:outputText>
		</h:panelGrid>
		<h:panelGrid columns="1" title="5">
			<h:outputText value="sortOrder='#{sortingAndFiltering.sortOrder}'"></h:outputText>
			<h:outputText value="comparator: sort by length" />
			<h:outputText value="filterBy"></h:outputText>
		</h:panelGrid>
		<h:panelGrid columns="1" title="6">
			<h:outputText value=" filterExpression='data > filterValue"></h:outputText>
		</h:panelGrid>
		<h:panelGrid columns="1" title="7">
			<h:outputText value="sortBy"></h:outputText>
			<h:outputText value="selfSorted='#{sortingAndFiltering.selfSorted}'"></h:outputText>
		</h:panelGrid>
		<h:panelGrid columns="1" title="8">
			<h:outputText value="filterBy" />
		</h:panelGrid>
		<h:panelGrid columns="1" title="9">
			<h:outputText value="sortBy"></h:outputText>
		</h:panelGrid>
	</h:panelGrid>
	<h:panelGroup id="selectionTable">
					<table>
					  <tr>
					    <td>Selected Issue</td>
					    <td>
							<h:outputText value="#{sortingAndFiltering.selectedItem.int0}"></h:outputText>
						</td>
					    <td>
							<h:outputText value="#{sortingAndFiltering.selectedItem.str0}"></h:outputText>
						</td>
					    <td>
							<h:outputText value="#{sortingAndFiltering.selectedItem.str1}"></h:outputText>
						</td>
					    <td>
							<h:outputText value="#{sortingAndFiltering.selectedItem.str2}"></h:outputText>
						</td>
					  </tr>
					</table>
				</h:panelGroup>
	<rich:dataTable id="dataTableSAFID" value="#{sortingAndFiltering.dataModel}"
		var="data" sortMode="#{sortingAndFiltering.sortMode}" rendered="#{sortingAndFiltering.rendered}">
		<f:facet name="caption"><h:outputText value="sortMode: #{sortingAndFiltering.sortMode}"></h:outputText></f:facet>
		<rich:column sortBy="#{data.int0}"
			selfSorted="#{sortingAndFiltering.selfSorted}"
			sortOrder="#{sortingAndFiltering.sortOrder}" filterBy="#{data.int0}">
			
			<f:facet name="header">
				<h:outputText value="#1" />
			</f:facet>
			<a4j:commandLink value="Select" action="#{sortingAndFiltering.select}" reRender="dataTableSAFID"></a4j:commandLink>
			<f:facet name="footer">
				<h:outputText value="#1" />
			</f:facet>
		</rich:column>
		
		<rich:column filterBy="#{data.int1}" sortBy="#{data.int1}"
			selfSorted="#{sortingAndFiltering.selfSorted}"
			filterEvent="ondblclick">
			<f:facet name="header">
				<h:outputText value="#2" />
			</f:facet>
			<h:outputText value="#{data.int1}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#2" />
			</f:facet>
		</rich:column>
		
		<rich:column sortBy="#{data.str0}"
			sortOrder="#{sortingAndFiltering.sortOrder}" filterBy="#{data.str0}">
			<f:facet name="header">
				<h:outputText value="#3" />
			</f:facet>
			<h:outputText value="#{data.str0}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#3" />
			</f:facet>
		</rich:column>
		<rich:column filterBy="#{data.int2}"
			selfSorted="#{sortingAndFiltering.selfSorted}">
			<f:facet name="header">
				<h:outputText value="#4" />
			</f:facet>
			<h:outputText value="#{data.int2}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#4" />
			</f:facet>
		</rich:column>	
		<rich:column filterBy="#{data.str1}"
			sortOrder="#{sortingAndFiltering.sortOrder}"
			comparator="#{sortingAndFiltering.comparator}">
			<f:facet name="header">
				<h:outputText value="#5" />
			</f:facet>
			<h:outputText value="#{data.str1}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#5" />
			</f:facet>
		</rich:column>
		<rich:column
			filterExpression="#{data.int3 > sortingAndFiltering.filterValue}">
			<f:facet name="header">
				<h:outputText value="#6" />
			</f:facet>
			<h:outputText value="#{data.int3}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#6" />
			</f:facet>
		</rich:column>
		<rich:column sortBy="#{data.str2}"
			selfSorted="#{sortingAndFiltering.selfSorted}">
			<f:facet name="header">
				<h:outputText value="#7" />
			</f:facet>
			<h:graphicImage value="#{data.str2}"></h:graphicImage>
			<h:outputText value="#{data.str2}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#7" />
			</f:facet>
		</rich:column>
		<rich:column filterBy="#{data.str3}">
			<f:facet name="header">
				<h:outputText value="#8" />
			</f:facet>
			<h:commandButton value="#{data.str3}"></h:commandButton>
			<f:facet name="footer">
				<h:outputText value="#8" />
			</f:facet>
		</rich:column>
		<rich:column sortBy="#{data.bool0}">
			<f:facet name="header">
				<h:outputText value="#9" />
			</f:facet>
			<h:selectBooleanCheckbox value="#{data.bool0}"></h:selectBooleanCheckbox>
			<f:facet name="footer">
				<h:outputText value="#9" />
			</f:facet>
		</rich:column>		
	</rich:dataTable>

	<rich:dataTable id="dataTableFilterValueID"
		value="#{sortingAndFiltering.data}" var="data">
		<rich:column filterValue="/pics/error.gif" filterBy="#{data.str2}">
			<f:facet name="header">
				<h:outputText value="#7(filterValue='/pics/error.gif)'" />
			</f:facet>
			<h:graphicImage value="#{data.str2}"></h:graphicImage>
			<h:outputText value="   [#{data.str2}]"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#7" />
			</f:facet>
		</rich:column>
	</rich:dataTable>

	<h:panelGrid columns="2">
		<h:outputText value="filterMethod (#3):"></h:outputText>
		<h:inputText value="#{sortingAndFiltering.filterInput}"
			onchange="submit();" />

		<h:outputText value="filterValue (#6):"></h:outputText>
		<h:inputText value="#{sortingAndFiltering.filterValue}"
			onchange="submit();" />

		<h:outputText value="sortMode" />
		<h:selectOneRadio value="#{sortingAndFiltering.sortMode}"
			onchange="submit();">
			<f:selectItem itemLabel="single" itemValue="single" />
			<f:selectItem itemLabel="multi" itemValue="multi" />
		</h:selectOneRadio>

		<h:outputText value="selfSorted" />
		<h:selectBooleanCheckbox value="#{sortingAndFiltering.selfSorted}"
			onchange="submit();" />

		<h:outputText value="rendered" />
		<h:selectBooleanCheckbox value="#{sortingAndFiltering.rendered}"
			onchange="submit();" />
		
		<h:outputText value="sortOrder" />
		<h:selectOneRadio value="#{sortingAndFiltering.currentSortOrder}"
			onchange="submit();">
			<f:selectItem itemLabel="DESCENDING" itemValue="DESCENDING" />
			<f:selectItem itemLabel="UNSORTED" itemValue="UNSORTED" />
			<f:selectItem itemLabel="ASCENDING" itemValue="ASCENDING" />
		</h:selectOneRadio>
	</h:panelGrid>
</f:subview>