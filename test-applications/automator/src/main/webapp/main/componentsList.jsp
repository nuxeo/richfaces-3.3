<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="componentsListSubview">
	<h:panelGrid columns="1">
		<a4j:commandLink reRender="componentPage" value="Combo Box">
			<a4j:actionparam value="/component/comboBox.jsp"
				assignTo="#{general.componentPage}" />
		</a4j:commandLink>
		<a4j:commandLink reRender="componentPage" value="Calendar">
			<a4j:actionparam value="/component/calendar.jsp"
				assignTo="#{general.componentPage}" />
		</a4j:commandLink>
		<a4j:commandLink reRender="componentPage" value="DropDownMenu">
			<a4j:actionparam value="/component/dropDownMenu.jsp"
				assignTo="#{general.componentPage}" />
		</a4j:commandLink>
	</h:panelGrid>
</f:subview>