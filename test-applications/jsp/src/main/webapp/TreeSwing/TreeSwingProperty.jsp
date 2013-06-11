<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<f:subview id="treeSwingPropertySubviewID">
	
	<h:commandButton id="focusID" action="#{treeSwing.add}" value="add test" />
	<h:panelGrid columns="2">
		<f:facet name="header">
			<h:outputText value="Tree Properties" />
		</f:facet>

		<h:outputText value="Change tree switchType:" />
		<h:selectOneRadio value="#{treeSwing.switchType}" onclick="submit();">
			<f:selectItem itemLabel="client" itemValue="client" />
			<f:selectItem itemLabel="server" itemValue="server" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
		</h:selectOneRadio>

		<h:outputText value="rendered:" />
		<h:selectBooleanCheckbox value="#{treeSwing.rendered}"
			onchange="submit();" />

		<h:outputText value="disableKeyboardNavigation:" />
		<h:selectBooleanCheckbox
			value="#{treeSwing.disableKeyboardNavigation}" onchange="submit();" />

		<h:outputText value="showConnectingLines:" />
		<h:selectBooleanCheckbox value="#{treeSwing.showConnectingLines}"
			onchange="submit();" />

		<h:outputText value="use custom icons:" />
		<h:selectBooleanCheckbox value="#{treeSwing.useCustomIcons}">
			<a4j:support action="#{treeSwing.addCustomIcons}" event="onchange"
				reRender="tree"></a4j:support>
		</h:selectBooleanCheckbox>
	</h:panelGrid>
</f:subview>