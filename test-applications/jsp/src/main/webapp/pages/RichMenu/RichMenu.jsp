<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="richMenuSubviewID">
	<h:panelGrid columns="5" border="1">
		<h:panelGrid columns="2">
			<h:outputText value="h" />
			<h:outputText value="a4j" />
			<h:commandButton value="submit" style="width : 85px; height : 21px;"></h:commandButton>
			<a4j:commandButton value="submit" reRender="richGridID" style="width : 85px; height : 21px;"></a4j:commandButton>
			<h:commandButton value="immediate" immediate="true" style="width : 85px; height : 21px;"></h:commandButton>
			<a4j:commandButton value="immediate" immediate="true" reRender="richGridID" style="width : 85px; height : 21px;"></a4j:commandButton>
		</h:panelGrid>
		<h:panelGrid columns="2">
			<h:outputLabel for="a4jLodID" value="a4j:log"></h:outputLabel>
			<h:selectBooleanCheckbox id="a4jLodID" value="#{option.log}" onchange="submit();"></h:selectBooleanCheckbox>

			<a4j:status id="a4jStatusID" startText="WORK!" startStyle="color: red;" stopText="a4j:status"></a4j:status>
			<h:panelGroup></h:panelGroup>
		</h:panelGrid>
		<h:panelGrid columns="1">
			<h:panelGrid columns="4">
				<h:outputText value="Default:" />
				<h:selectBooleanCheckbox value="#{option.reDefault}" />
				
				<h:outputText value="Component" />
				<h:selectBooleanCheckbox value="#{option.reComponent}" onchange="submit();" />

				<h:outputText value="Straightforward" />
				<h:selectBooleanCheckbox value="#{option.reStraightforward}" onchange="submit();" />

				<h:outputText value="Property" />
				<h:selectBooleanCheckbox value="#{option.reProperty}" onchange="submit();" />
			</h:panelGrid>
		</h:panelGrid>

		<h:panelGrid columns="1">
			<h:outputText value="Select skin: " />
			<h:selectOneMenu binding="#{skinBean.component}" onblur="submit();">
				<a4j:support action="#{skinBean.change}" event="onchange" />
			</h:selectOneMenu>
			<h:selectOneMenu value="#{skinning.selectSkinning}" onblur="submit();">
				<f:selectItem itemLabel="none" itemValue="none"/>
				<f:selectItem itemLabel="skinning" itemValue="SKINNING"/>
				<f:selectItem itemLabel="skinningClass" itemValue="SKINNING_CLASSES"/>
			</h:selectOneMenu>
		</h:panelGrid>

		<h:panelGrid columns="1">
			<h:commandButton action="#{richBean.invalidateSession}" immediate="true" value="Invalidates this session"></h:commandButton>
			<h:outputText value="Select component:" />
			<h:selectOneMenu value="#{richBean.src}" immediate="true" onchange="submit();">
				<f:selectItems value="#{richBean.list}" />
				<f:param value="#{richBean.src}" name="currentComponent"/>
			</h:selectOneMenu>
		</h:panelGrid>
	</h:panelGrid>
	<h:commandLink value="Back" action="main"></h:commandLink>
</f:subview> 
