<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://jboss.com/products/seam/taglib" prefix="s"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="r"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a"%>

<html>
<head><title>JBPM sample</title></head>
<body>
<f:view>
	<h:form id="forma">
	<h:panelGrid columns="2">
	<h:outputText value="System.currentTimeMillis()"/>
	<h:outputText id="time" value="#{flow.time}"/>
	<a:commandButton value="add calling backing bean action" 
	action="#{flow.fillList}" reRender="tablica"/>
	<a:commandButton value="add calling pageflow action" 
	action="fillList" reRender="tablica"/>
	</h:panelGrid>
	
	
		<r:dataTable id="tablica" width="100%"
					rows="10"
					onRowMouseOver="this.style.backgroundColor='#F1F1F1'"
					onRowMouseOut="this.style.backgroundColor=''"
					cellpadding="0" cellspacing="0" var="v" rowKeyVar="rb"
					value="#{flow.lista}">

					<r:column>
					<h:outputText value="#{v}"/>
					</r:column>

				</r:dataTable>

				<r:datascroller id="scrollerTablica" for="tablica" maxPages="20"/>

	</h:form>
	notice how time changes when calling pageflow action
</f:view>
</body>
</html>
