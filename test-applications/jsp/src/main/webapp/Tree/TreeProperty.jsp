<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="treePropertySubviewID">
<br />
	<br />
	<h:commandButton value="add test" action="#{treeBean.addHtmlTree}"></h:commandButton>
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getAdviseNodeSelected" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('tree').adviseNodeSelected}" />
		</rich:column>
		<rich:column>
			<a4j:commandLink
		onclick="$('formID:treeSubviewID:tree').component.isRowAvailable()"
		value="isRowAvailable"></a4j:commandLink>
		</rich:column>
	</h:panelGrid>
</f:subview>