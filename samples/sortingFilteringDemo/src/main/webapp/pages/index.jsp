<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="dt" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<html>
	<head>
		<title></title>
		<style type="">
		.rich-sort-icon {
			border: 1px solid;
		}
		</style>
	</head>
	<body>
		<f:view>
			<h:form>
				<h:outputText value="SortMode:"></h:outputText>
				<h:selectOneRadio value="#{bean.sortMode}">
					<f:selectItem itemLabel="single" itemValue="single"/>
					<f:selectItem itemLabel="multi" itemValue="multi"/>
					<a4j:support event="onchange" reRender="dataTable" action="#{bean.clearSortPriority}"></a4j:support>
				</h:selectOneRadio>
				<h:panelGroup id="selectionTable">
					<table >
					  <tr>
					    <td>Selected Issue</td>
					    <td>
							<h:outputText value="#{bean.selectedIssue.key.value}"></h:outputText>
						</td>
					    <td>
							<h:outputText value="#{bean.selectedIssue.summary}"></h:outputText>
						</td>
					    <td>
							<h:outputText value="#{bean.selectedIssue.assignee.name}"></h:outputText>
						</td>
					    <td>
							<h:outputText value="#{bean.selectedIssue.reporter.name}"></h:outputText>
						</td>
					  </tr>
					</table>
				</h:panelGroup>
				<dt:dataTable id="dataTable" value="#{bean.model}" var="issue" sortMode="#{bean.sortMode}" sortPriority="#{bean.sortPriority}">
					<dt:column>
						<a4j:commandLink value="Select" action="#{bean.select}" reRender="selectionTable"></a4j:commandLink>
					</dt:column>
					<dt:column filterBy="#{issue.key.value}" filterValue="CH-" sortBy="#{issue.key}" width="60px"
						sortOrder="#{bean.sortOrder[0]}" filterEvent="onkeyup">
						<f:facet name="header">
							<h:outputText value="Key"></h:outputText>
						</f:facet>
						<h:outputText value="#{issue.key.value}"></h:outputText>
					</dt:column>
					<dt:column sortBy="#{issue.summary}" width="400px" sortOrder="#{bean.sortOrder[1]}">
						<f:facet name="header">
							<h:outputText value="Summary"></h:outputText>
						</f:facet>
						<h:outputText value="#{issue.summary}"></h:outputText>
					</dt:column>
					<dt:column width="150px" filterBy="#{issue.assignee.name}" comparator="#{comparators.assigneeComparator}"
						sortOrder="#{bean.sortOrder[2]}">
						<f:facet name="header">
							<h:outputText value="Assignee"></h:outputText>
						</f:facet>
						<h:outputText value="#{issue.assignee.name}"></h:outputText>
					</dt:column>
					<dt:column filterBy="#{issue.reporter.name}" width="150px" comparator="#{comparators.reporterComparator}"
						sortOrder="#{bean.sortOrder[3]}">
						<f:facet name="header">
							<h:outputText value="Reporter"></h:outputText>
						</f:facet>
						<h:inputText value="#{issue.reporter.name}"></h:inputText>
					</dt:column>
				</dt:dataTable>
				<h:commandButton value="test"></h:commandButton>
			</h:form>
		</f:view>
	</body>	
</html>  
