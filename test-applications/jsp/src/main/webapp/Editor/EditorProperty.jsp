<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="editorPropertySubviewID">
	<h:panelGrid columns="2">
		<f:facet name="header">
			<h:outputText value="List of Attributes" />
		</f:facet>

		<h:outputText value="height: "></h:outputText>
		<h:inputText value="#{editor.height}" onchange="submit();"></h:inputText>

		<h:outputText value="width: "></h:outputText>
		<h:inputText value="#{editor.width}" onchange="submit();"></h:inputText>

		<h:outputText value="theme: "></h:outputText>
		<h:selectOneRadio value="#{editor.theme}" onchange="submit();">
			<f:selectItem itemValue="simple" itemLabel="simple" />
			<f:selectItem itemValue="advanced" itemLabel="advanced" />
		</h:selectOneRadio>

		<h:outputText value="autoResize: "></h:outputText>
		<h:selectBooleanCheckbox value="#{editor.autoResize}"
			onchange="submit();" />

		<h:outputText value="immediate: "></h:outputText>
		<h:selectBooleanCheckbox value="#{editor.immediate}"
			onchange="submit();" />

		<h:outputText value="rendered: "></h:outputText>
		<h:selectBooleanCheckbox value="#{editor.rendered}"
			onchange="submit();" />

		<h:outputText value="required: "></h:outputText>
		<h:selectBooleanCheckbox value="#{editor.required}"
			onchange="submit();" />

		<h:outputText value="useSeamText (work only with Seam libraries): "></h:outputText>
		<h:selectBooleanCheckbox value="#{editor.useSeamText}"
			onchange="submit();" />

		<h:outputText value="readonly: "></h:outputText>
		<h:selectBooleanCheckbox value="#{editor.readonly}"
			onchange="submit();" />

		<h:outputText value="viewMode: "></h:outputText>
		<h:selectOneRadio value="#{editor.viewMode}" onchange="submit();">
			<f:selectItem itemValue="visual" itemLabel="visual" />
			<f:selectItem itemValue="source" itemLabel="source" />
		</h:selectOneRadio>

		<h:outputText value="tabindex: "></h:outputText>
		<h:inputText value="#{editor.tabindex}" onchange="submit();"></h:inputText>

		<h:outputText value="dialogType: "></h:outputText>
		<h:selectOneRadio value="#{editor.dialogType}" onchange="submit();">
			<f:selectItem itemValue="window" itemLabel="window" />
			<f:selectItem itemValue="modal" itemLabel="modal" />
		</h:selectOneRadio>

		<h:outputText value="language: "></h:outputText>
		<h:selectOneRadio value="#{editor.language}" onchange="submit();">
			<f:selectItem itemValue="en" itemLabel="en" />
			<f:selectItem itemValue="de" itemLabel="de" />
			<f:selectItem itemValue="ru" itemLabel="ru" />
		</h:selectOneRadio>

		<%--
		<h:outputText value="skin: "></h:outputText>
		<h:selectOneRadio value="#{editor.skin}" onchange="submit();">
			<f:selectItem itemValue="default" itemLabel="default" />
			<f:selectItem itemValue="o2k7" itemLabel="o2k7" />
			<f:selectItem itemValue="null" itemLabel="rich" />
		</h:selectOneRadio>
		--%>
		
		<h:outputText value="use configuration file: "></h:outputText>
		<h:selectBooleanCheckbox value="#{editor.configuration}"
			onchange="submit();" valueChangeListener="#{editor.useConfigFile}" />
	</h:panelGrid>
	<h:commandButton value="add test" action="#{editor.addHtmlEditor}"></h:commandButton>
</f:subview>