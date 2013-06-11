<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dropDownMenuSubview">
	<div>The &lt;rich:dropDownMenu&gt; component is used for creating
	multilevel drop-down menus.</div>
	<rich:spacer height="30"></rich:spacer>

	<h:panelGrid id="dropDownMenuGrid" columns="1">
		<h:messages id="mess" style="color: red" />
		<rich:dropDownMenu value="Tables">
			<rich:menuItem value="Table1"
				action="#{dropDownMenuGeneral.doSummary}" id="dropdownmenu_mItem5" />
			<rich:menuItem value="Table2" action="#{dropDownMenuGeneral.doInfo}"
				id="dropdownmenu_menuItem6" />
			<rich:menuItem value="Table3"
				action="#{dropDownMenuGeneral.doTable3}" id="dropdownmenu_menuItem7" />
		</rich:dropDownMenu>
	</h:panelGrid>

</f:subview>