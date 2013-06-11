<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="listShuttleStraightforwardSubviewID">
	<h:panelGrid columns="3">
		<a4j:commandButton value="Show selection (reRender)"
			reRender="infoLSID"></a4j:commandButton>
		<h:column>
			<h:selectBooleanCheckbox value="#{listShuttle.showSelect}"
				onclick="submit();" />
		</h:column>
		<h:column>
			<h:dataTable id="infoLSID" value="#{listShuttle.info}" var="info"
				rendered="#{listShuttle.showSelect}" border="1">
				<h:column>
					<h:outputText value="#{info}" />
				</h:column>
			</h:dataTable>
		</h:column>

		<a4j:commandButton value="Show all source (reRender)"
			reRender="allInfoLSSourceID"></a4j:commandButton>
		<h:column>
			<h:selectBooleanCheckbox value="#{listShuttle.showAllSourceData}"
				onclick="submit();" />
		</h:column>
		<h:column>
			<h:dataTable id="allInfoLSSourceID"
				value="#{listShuttle.sourceValue}" var="allInfo"
				rendered="#{listShuttle.showAllSourceData}" border="1">
				<h:column>
					<f:facet name="header">
						<h:outputText value="Output" />
					</f:facet>
					<h:outputText value="#{allInfo.int0}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Input" />
					</f:facet>
					<h:outputText value="#{allInfo.str4}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Button" />
					</f:facet>
					<h:outputText value="#{allInfo.str0} submit();" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="a4jLink" />
					</f:facet>
					<h:outputText value="#{allInfo.str1} a4j" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Select" />
					</f:facet>
					<h:outputText value="#{allInfo.str2}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="GraphicImage" />
					</f:facet>
					<h:outputText value="#{allInfo.str3}" />
				</h:column>
			</h:dataTable>
		</h:column>

		<a4j:commandButton value="Show all target (reRender)"
			reRender="allInfoLSTargetID"></a4j:commandButton>
		<h:column>
			<h:selectBooleanCheckbox value="#{listShuttle.showAllTargetData}"
				onclick="submit();" />
		</h:column>
		<h:column>
			<h:dataTable id="allInfoLSTargetID"
				value="#{listShuttle.targetValue}" var="allInfo"
				rendered="#{listShuttle.showAllTargetData}" border="1">
				<h:column>
					<f:facet name="header">
						<h:outputText value="Output" />
					</f:facet>
					<h:outputText value="#{allInfo.int0}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Input" />
					</f:facet>
					<h:outputText value="#{allInfo.str4}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Button" />
					</f:facet>
					<h:outputText value="#{allInfo.str0} submit();" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="a4jLink" />
					</f:facet>
					<h:outputText value="#{allInfo.str1} a4j" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="Select" />
					</f:facet>
					<h:outputText value="#{allInfo.str2}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText value="GraphicImage" />
					</f:facet>
					<h:outputText value="#{allInfo.str3}" />
				</h:column>

				<rich:column>
					<f:facet name="header">
						<h:outputText value="OutputLink"></h:outputText>
					</f:facet>
					<h:outputText value="http://www.jboss.com/" />
				</rich:column>
			</h:dataTable>
		</h:column>
	</h:panelGrid>
</f:subview>