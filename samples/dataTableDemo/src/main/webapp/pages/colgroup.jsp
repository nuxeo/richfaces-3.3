<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="data"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<html>
<head>
<title></title>
</head>
<body>
<f:view>
	<h:form>
		<h:panelGrid columns="1" border="1">
			<data:dataTable id="master" var="master" value="#{data.mounths}"
			    rowKeyVar="key"
				styleClass="table" captionClass="caption"
				rowClasses="rowa,rowb,rowc rowcc" headerClass="header"
				footerClass="footer" onRowClick="alert('row #{key}')">
				<f:facet name="caption">
					<h:outputText value="caption" />
				</f:facet>
				<f:facet name="header">
					<data:columnGroup columnClasses="cola, colb ,rowc rowcc">
						<data:column rowspan="2">
							<h:outputText value="2-row head" />
						</data:column>
						<h:column>
							<h:outputText value="head in UIColumn" />
						</h:column>
						<data:column breakBefore="true">
							<h:outputText value="2-d row head" />
						</data:column>
					</data:columnGroup>
				</f:facet>
				<f:facet name="footer">
					<h:outputText value="table foot" />
				</f:facet>
				<data:columnGroup >
				<data:column id="mounth" styleClass="column" rowspan="2"
					headerClass="cheader" footerClass="cfooter">
					<f:facet name="header">
						<h:outputText value="mounth" />
					</f:facet>
					<f:facet name="footer">
						<h:outputText value="-//-" />
					</f:facet>
					<h:outputText value="#{master.mounth}" />
				</data:column>
				<data:column styleClass="column" >
					headerClass="cheader" footerClass="cfooter">
					<f:facet name="header">
						<h:outputText value="mounth" />
					</f:facet>
					<f:facet name="footer">
						<h:outputText value="-//-" />
					</f:facet>
					<h:outputText value="#{master.mounth}" />
				</data:column>
				</data:columnGroup>
				<data:column styleClass="column" 
					headerClass="cheader" footerClass="cfooter">
					<h:outputText value="#{master.mounth}" />
				</data:column>
				<data:subTable id="detail" var="detail" value="#{master.detail}">
					<data:column id="name">
						<h:outputText value="#{detail.name}" />
					</data:column>
					<data:column id="qty">
						<h:outputText value="#{detail.qty}" />
					</data:column>
				</data:subTable>
				<data:column id="total" styleClass="total" colspan="2">
					<h:outputText value="#{master.total}" />
				</data:column>
			</data:dataTable>

		</h:panelGrid>
	</h:form>
	<a4j:log hotkey="D" />
</f:view>
</body>
</html>
