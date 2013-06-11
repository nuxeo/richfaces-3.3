<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
	<rich:dataDefinitionList value="#{dataDefinitionList.arrDefault}" var="def">
		<h:outputText value="#{def}" />
	</rich:dataDefinitionList>
