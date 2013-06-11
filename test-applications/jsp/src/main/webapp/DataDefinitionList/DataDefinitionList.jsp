<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dataDefinitionListSubviewID">
		<rich:dataDefinitionList id="ddListID" binding="#{dataDefinitionList.htmlDataDefinitionList}" value="#{dataDefinitionList.arr}" var="arr" first="#{dataDefinitionList.first}"
			rendered="#{dataDefinitionList.rendered}" title="#{dataDefinitionList.title}" dir="#{dataDefinitionList.dir}"
			rows="#{dataDefinitionList.rows}" style="#{style.style}" styleClass="#{style.styleClass}" columnClasses="#{style.columnClasses}" rowClasses="#{style.rowClasses}">
			<f:facet name="header">
				<h:outputText value="Africa(header):" />
			</f:facet>
			<h:outputText value="#{arr.str0} " />
			<h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
			<f:facet name="footer">
				<h:outputText value="Africa(footer);" />
			</f:facet>
		</rich:dataDefinitionList>
</f:subview>
