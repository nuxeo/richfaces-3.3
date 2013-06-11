<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="data" %>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view >
		<h:form>
		<h:selectOneRadio binding="#{skinBean.component}" />
		<h:commandLink action="#{skinBean.change}" value="set skin" />
		 <h:panelGrid columns="2">
			<h:selectBooleanCheckbox id="c2rendered" value="#{data.c2rendered}" >
			   <a4j:support event="onchange" reRender="master"></a4j:support>
			</h:selectBooleanCheckbox>
			<h:outputLabel value="Show 2-d column" for="c2rendered"></h:outputLabel>
			<h:selectBooleanCheckbox id="c3rendered" value="#{data.c3rendered}" >
			   <a4j:support event="onchange" reRender="master"></a4j:support>
			</h:selectBooleanCheckbox>
			<h:outputLabel value="Show 3-d column" for="c3rendered"></h:outputLabel>
			</h:panelGrid>
			<data:dataTable id="master" var="master" value="#{data.mounths}" 
			styleClass="table" captionClass="caption" rowClasses="rowa,rowb,rowc rowcc"
			headerClass="header" footerClass="footer">
			    <a4j:support event="onRowClick" action="#{master.check}" reRender="checked">
			    </a4j:support>
			    <f:facet name="caption"><h:outputText value="caption" /></f:facet>
			    <f:facet name="header">
			    <data:columnGroup columnClasses="cola, colb ,rowc rowcc">
			    <data:column rowspan="2" colspan="#{data.c1span}" >
			    <h:outputText value="2-row head" />
			    </data:column>
			    <data:column rowspan="2" colspan="#{data.c2span}" rendered="#{data.c2rendered}">
			    <h:outputText value="2-row head" />
			    </data:column>
			    <h:column  rendered="#{data.c3rendered}" >
			    <h:outputText value="head in UIColumn" >
					   <a4j:support ajaxSingle="true" event="onclick" action="#{data.toggleColumn}" reRender="master,c3rendered"></a4j:support>
			    </h:outputText>
			    </h:column>
			    <data:column breakBefore="true"  rendered="#{data.c3rendered}">
			    <h:outputText value="2-d row head" />
			    </data:column>
			    </data:columnGroup>
			    </f:facet>
			    <f:facet name="footer"><h:outputText value="table foot" /></f:facet>
			    <data:column id="mounth" styleClass="column" colspan="#{data.c1span}" 
			    headerClass="cheader" footerClass="cfooter" >
			    <f:facet name="header"><h:outputText value="mounth" /></f:facet>
			    <f:facet name="footer"><h:outputText value="-//-" /></f:facet>
			      <h:outputText value="#{master.mounth}" />
			    </data:column>
			    <data:column id="cell2" styleClass="column" colspan="#{data.c2span}" 
			    headerClass="dheader" footerClass="dfooter" rendered="#{data.c2rendered}">
			    <f:facet name="header"><h:outputText value="cell 2 head" /></f:facet>
			    <f:facet name="footer"><h:outputText value="-//-" /></f:facet>
			      <h:outputText value="cell 2" />
			    </data:column>
			    <h:column id="cell3" rendered="#{data.c3rendered}" >
			    <f:facet name="header">
			        <h:outputText id="c3head" value="cell 3 head" >
					   <a4j:support ajaxSingle="true" event="onclick" action="#{data.toggleColumn}" reRender="master,c3rendered"></a4j:support>
					</h:outputText>
			    </f:facet>
			    <f:facet name="footer"><h:outputText value="-//-" /></f:facet>
			      <h:outputText value="cell 3" />
			    </h:column>
			    <data:column breakBefore="true" colspan="2">
			      <h:selectBooleanCheckbox id="checked" value="#{master.checked}" 
			      valueChangeListener="#{master.checkChanged}"/>
			      <h:outputLabel value="Click to toggle" for="checked"></h:outputLabel>
			    </data:column>
			    <data:column id="total" styleClass="total" colspan="1">
			      <h:outputText value="#{master.total}" />
			    </data:column>
			</data:dataTable>
		</h:form>
			<a4j:log hotkey="D"/>
		</f:view>
	</body>	
</html>  
