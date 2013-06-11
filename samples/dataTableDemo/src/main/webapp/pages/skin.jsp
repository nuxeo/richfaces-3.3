<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="data" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view >
		<h:form>
		<h:selectOneRadio binding="#{skinBean.component}" />
		<h:commandLink action="#{skinBean.change}" value="set skin" />
		</h:form>

			<data:dataTable id="master" var="master" value="#{data.mounths}" 
			styleClass="table" captionClass="caption" rowClasses="rowa,rowb,rowc rowcc"
			headerClass="header" footerClass="footer">
			    <f:facet name="caption"><h:outputText value="caption" /></f:facet>
			    <f:facet name="header">
			    <data:columnGroup columnClasses="cola, colb ,rowc rowcc">
			    <data:column rowspan="2">
			    <h:outputText value="2-row head" />
			    </data:column>
			    <data:column rowspan="2">
			    <h:outputText value="2-row head" />
			    </data:column>
			    <h:column >
			    <h:outputText value="head in UIColumn" />
			    </h:column>
			    <data:column breakBefore="true">
			    <h:outputText value="2-d row head" />
			    </data:column>
			    </data:columnGroup>
			    </f:facet>
			    <f:facet name="footer"><h:outputText value="table foot" /></f:facet>
			    <data:column id="mounth" styleClass="column" colspan="1" 
			    headerClass="cheader" footerClass="cfooter" >
			    <f:facet name="header"><h:outputText value="mounth" /></f:facet>
			    <f:facet name="footer"><h:outputText value="-//-" /></f:facet>
			      <h:outputText value="#{master.mounth}" />
			    </data:column>
			    <data:column id="cell2" styleClass="column" colspan="1" 
			    headerClass="dheader" footerClass="dfooter" >
			    <f:facet name="header"><h:outputText value="cell 2 head" /></f:facet>
			    <f:facet name="footer"><h:outputText value="-//-" /></f:facet>
			      <h:outputText value="cell 2" />
			    </data:column>
			    <data:column id="cell3" styleClass="column" colspan="1" 
			    headerClass="eheader" footerClass="efooter" >
			    <f:facet name="header"><h:outputText value="cell 3 head" /></f:facet>
			    <f:facet name="footer"><h:outputText value="-//-" /></f:facet>
			      <h:outputText value="cell 3" />
			    </data:column>
			<data:subTable id="detail" var="detail" value="#{master.detail}">
			    <data:column id="name" colspan="2">
			    <f:facet name="header"><h:outputText value="property" /></f:facet>
			    <f:facet name="footer"><h:outputText value="-/-/-" /></f:facet>
			      <h:outputText value="#{detail.name}" />
			    </data:column>
			    <data:column id="qty">
			    <f:facet name="header"><h:outputText value="qty" /></f:facet>
			    <f:facet name="footer"><h:outputText value="total" /></f:facet>
			      <h:outputText value="#{detail.qty}" />
			    </data:column>
			</data:subTable>
			    <data:column id="total" styleClass="total" colspan="3">
			      <h:outputText value="#{master.total}" />
			    </data:column>
			</data:dataTable>
			
		</f:view>
	</body>	
</html>  
