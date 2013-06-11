<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="data" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view >
		<h:panelGrid columns="4" border="1">
			<data:dataOrderedList id="master" var="master" value="#{data.mounths}"
			styleClass="table"  rowClasses="rowa,rowb,rowc rowcc"
			type="i">
			      <h:outputText value="#{master.mounth}" />
			      <h:outputText value="#{detail.qty}" />
			<data:dataList id="detail" var="detail" value="#{master.detail}"
			type="square">
			      <h:outputText value="#{detail.name}" />
			      <h:outputText value="#{detail.qty}" />
			</data:dataList>
			</data:dataOrderedList>
			
			<data:dataGrid id="master1" var="master" value="#{data.numbers}" columns="4" 
			    rowClasses="rowa,rowb,rowc rowcc" 
			    columnClasses="cola, colb ,rowc rowcc">
			      <h:outputText value="#{master}" />
			      <h:outputText value="st" />
			</data:dataGrid>

			<data:dataGrid id="master11" var="master" value="#{data.numbers}" columns="3" 
			    rowClasses="rowa,rowb,rowc rowcc" 
			    columnClasses="cola, colb ,rowc rowcc">
			    <f:facet name="caption"><h:outputText value="caption" /></f:facet>
			    <f:facet name="header"><h:outputText value="table head" /></f:facet>
			    <f:facet name="footer"><h:outputText value="table foot" /></f:facet>
			      <h:outputText value="#{master}" />
			      <h:outputText value="st" />
			</data:dataGrid>

			<data:dataDefinitionList id="master2" var="master" value="#{data.mounths}" 
			    rowClasses="rowa,rowb,rowc rowcc" 
			    columnClasses="cola, colb ,rowc rowcc">
			    <f:facet name="term" >
			      <h:outputText value="#{master.mounth}" />
			    </f:facet>
			      <h:outputText value="#{master.total}" />
			</data:dataDefinitionList>
			<data:dataList id="miss" var="detail" value="#{data.nonexist}"
			type="square">
			      <h:outputText value="#{nonexist.name}" />
			      <h:outputText value="#{detail.mounth}" />
			</data:dataList>
      </h:panelGrid>
		</f:view>
	</body>	
</html>  
