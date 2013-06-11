<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="dataFilterSliderPropertySubviewID">
<h:commandButton value="add test" action="#{dfs.addHtmlDataFilterSlider}"></h:commandButton>
		<h:panelGrid columns="2">
			<h:outputText value="Rendered:" />
			<h:selectBooleanCheckbox value="#{dfs.rendered}" onchange="submit();" />
		</h:panelGrid>
		<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getEndRange" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText
				value="#{rich:findComponent('dfsID').endRange}" />
		</rich:column>
	</h:panelGrid>
</f:subview>