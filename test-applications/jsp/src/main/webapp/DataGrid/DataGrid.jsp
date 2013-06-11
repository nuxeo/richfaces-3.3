<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dataGridSubviewID">

	<rich:dataGrid id="dataGridID" value="#{dataGrid.allCars}" var="car" columns="#{dataGrid.columns}"
		elements="#{dataGrid.elements}" first="#{dataGrid.first}" binding="#{dataGrid.htmlDataGrid}"
		dir="#{dataGrid.dir}" border="#{dataGrid.border}" 
		cellpadding="#{dataGrid.cellpadding}" cellspacing="#{dataGrid.cellspacing}"
		rendered="#{dataGrid.rendered}" width="#{dataGrid.width}"				
		onclick="#{event.onclick}"
		ondblclick="#{event.ondblclick}"
		onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}"
		onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}"
		onRowClick="#{event.onRowClick}"
		onRowDblClick="#{event.onRowDblClick}"
		onRowMouseDown="#{event.onRowMouseDown}"
		onRowMouseMove="#{event.onRowMouseMove}"
		onRowMouseOut="#{event.onRowMouseOut}"
		onRowMouseOver="#{event.onRowMouseOver}"
		onRowMouseUp="#{event.onRowMouseUp}" >
		<f:facet name="header">
			<h:outputText value="Car Store"></h:outputText>
		</f:facet>
		<rich:panel>
			<f:facet name="header">
				<h:outputText value="#{car.make} #{car.model}"></h:outputText>
			</f:facet>
			<h:panelGrid columns="2">
				<h:outputText value="Price:"></h:outputText>
				<h:outputText value="#{car.price}" />
				<h:outputText value="Mileage:"></h:outputText>
				<h:outputText value="#{car.mileage}" />
				<rich:inplaceInput defaultLabel="add comment here.." />
			</h:panelGrid>
		</rich:panel>
		<f:facet name="footer">
			<rich:datascroller></rich:datascroller>
		</f:facet>
	</rich:dataGrid>
</f:subview>