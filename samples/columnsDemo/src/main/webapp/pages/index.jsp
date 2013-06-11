<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="dt"%>
<%@ taglib prefix="columns" uri="http://labs.jboss.com/jbossrichfaces/ui/columns" %>



<html>
	
	<body>
	<f:view>
	<h3>Test Jsp</h3>	
	<br/><br/>
		<h:form id="_form">
			<table>
				<tr>
					<td>Row Count:</td>
					<td>
						<h:inputText value="#{bean.rowsCount}">
							<a4j:support event="onchange" actionListener="#{bean.apply}" reRender="_data"></a4j:support>
						</h:inputText>
					</td>
				</tr>
				<tr>
					<td>Name:</td>
					<td>
						<h:selectBooleanCheckbox value="#{bean.name}">
							<a4j:support event="onclick" actionListener="#{bean.apply}" reRender="_data"></a4j:support>
						</h:selectBooleanCheckbox> 
					</td>
				</tr>
				<tr>
					<td>Type:</td>
					<td>
						<h:selectBooleanCheckbox value="#{bean.type}">
							<a4j:support event="onclick" actionListener="#{bean.apply}" reRender="_data"></a4j:support>
						</h:selectBooleanCheckbox> 
					</td>
				</tr>
				<tr>
					<td>Description:</td>
					<td>
						<h:selectBooleanCheckbox value="#{bean.description}">
							<a4j:support event="onclick" actionListener="#{bean.apply}" reRender="_data"></a4j:support>
						</h:selectBooleanCheckbox> 
					</td>
				</tr>
			</table>	
			
			<br/>
			
			<dt:dataTable id="_data" value="#{bean.data}" var="row">
				<dt:column sortBy="#{row[0]}">
					<f:facet name="header">
						<h:outputText value="#"></h:outputText>
					</f:facet>
					<h:outputText value="#{row[0]}"></h:outputText>
				</dt:column>
				<columns:columns value="#{bean.columns}" var="column" index="index" begin="2"
				sortBy="#{row[index]}" sortOrder="#{column.ordering}"
				filterBy="#{row[index]}" filterValue="#{column.filterValue}" filterEvent="onkeyup">
					<f:facet name="header">
						<h:outputText value="#{column.name}"></h:outputText>
					</f:facet>
					<h:outputText value="#{row[index]}" rendered="#{column.name != 'Description'}"></h:outputText>
					<h:inputText value="#{row[index]}" rendered="#{column.name eq 'Description'}"></h:inputText>
				</columns:columns>
			</dt:dataTable>
			<br/>
			<a4j:commandLink value="Ajax Submit" reRender="_data"></a4j:commandLink><br/>
			<h:commandLink value="Form Submit" action="jsp"></h:commandLink>
		
		</h:form>		
		<br/>	
		<h:form>
			<b>Filter Method Test:</b>
			<table>
				<tr>
					<td>Max Value:</td>
					<td>
						<h:inputText value="#{bean.maxValue}">
							<a4j:support event="onchange" reRender="_data"></a4j:support>
						</h:inputText>
					</td>
				</tr>
			</table>
			<dt:dataTable id="_data" value="#{bean.inrementData}" var="row">
				<columns:columns value="Integer" index="index" var="col"
				filterMethod="#{bean.filterMethod}">
					<f:facet name="header">
						<h:outputText value="#{col}"></h:outputText>
					</f:facet>
					<h:outputText value="#{row[index]}"></h:outputText>
				</columns:columns>
			</dt:dataTable>
		</h:form>
	
	</f:view>
	</body>	
</html>  
