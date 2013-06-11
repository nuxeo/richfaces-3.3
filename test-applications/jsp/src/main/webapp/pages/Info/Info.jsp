<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="infoSubview">
	<h:panelGrid binding="#{info.panelEvent}" columns="2">
	</h:panelGrid>
	<f:verbatim><br/></f:verbatim>
	<h:commandButton action="#{info.getEvent}" value="test event"></h:commandButton>
	
	<h:panelGrid columns="2">
		<h:outputText value="style:"></h:outputText>
		<h:dataTable id="styleTableID" value="#{info.style}" var="info">
			<h:column>
				<h:outputText value="#{info.key}"></h:outputText>
			</h:column>
			<h:column>
				<h:outputText value="#{info.value}"></h:outputText>
			</h:column>
		</h:dataTable>

		<h:outputText value="attribute"></h:outputText>
		<h:selectBooleanCheckbox value="#{option.attribute}" onchange="submit();"></h:selectBooleanCheckbox>

	
		<h:outputText value="attribute:" rendered="#{option.attribute}"></h:outputText>
		<h:dataTable id="attributeTableID" rendered="#{option.attribute}" value="#{info.attribute}" var="info">
			<h:column>
				<h:outputText value="#{info.key}"></h:outputText>
			</h:column>
			<h:column>
				<h:outputText value="#{info.value}"></h:outputText>
			</h:column>
		</h:dataTable>
	</h:panelGrid>
</f:subview>
