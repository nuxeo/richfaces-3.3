<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dataOrderedListSubviewID">
		<rich:dataOrderedList id="doListID" binding="#{dataOrderedList.htmlDataOrderedList}" value="#{dataOrderedList.arr}" var="arr" first="#{dataOrderedList.first}" 
			rendered="#{dataOrderedList.rendered}" title="#{dataOrderedList.title}" type="#{dataOrderedList.type}" dir="#{dataOrderedList.dir}" 
			rows="#{dataOrderedList.rows}"
			rowClasses="#{style.rowClasses}" style="#{style.style}" styleClass="#{style.styleClass}" >
			<f:facet name="header">
				<h:outputText value="Africa(header):" />				
			</f:facet>
			<h:outputText value="#{arr.str0} " />
			<h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
			<f:facet name="footer">
				<h:outputText value="Africa(footer);" />
			</f:facet>
		</rich:dataOrderedList>
</f:subview>
