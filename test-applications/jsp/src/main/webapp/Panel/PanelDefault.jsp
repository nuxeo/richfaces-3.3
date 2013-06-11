<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<rich:panel styleClass="top">
	<f:facet name="header">
		<h:outputText value="default panel" />
	</f:facet>

	<h:outputText value="This is default panel. content here" />
</rich:panel>