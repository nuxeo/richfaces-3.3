<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="scrollableDataTablePropertySubviewID">
	<h:commandButton value="add test"
		action="#{scrollableDT.addHtmlScrollableDataTable}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="length:"></h:outputText>
		<h:panelGroup>
			<h:inputText value="#{scrollableDT.dataLength}"></h:inputText>
			<a4j:commandButton action="#{scrollableDT.addNewItem}" reRender="sdt"
				value="ok" />
		</h:panelGroup>
		<h:outputText value="rows:" />
		<h:inputText value="#{scrollableDT.rows}" onchange="submit();">
		</h:inputText>

		<h:outputText value="first:"></h:outputText>
		<h:inputText value="#{scrollableDT.first}" onchange="submit();">
		</h:inputText>

		<h:outputText value="timeout"></h:outputText>
		<h:inputText value="#{scrollableDT.timeout}" onchange="submit();">
		</h:inputText>

		<h:outputText value="width:" />
		<h:inputText value="#{scrollableDT.width}" onchange="submit();">
		</h:inputText>

		<h:outputText value="height:"></h:outputText>
		<h:inputText value="#{scrollableDT.height}" onchange="submit();">
		</h:inputText>

		<h:outputText value="frozenColCount"></h:outputText>
		<h:inputText value="#{scrollableDT.frozenColCount}"
			onchange="submit();">
		</h:inputText>

		<h:outputLabel value="Binding:" for="checkBind"></h:outputLabel>
		<a4j:commandButton value="Check" id="checkBind" actionListener="#{scrollableDT.checkBinding}"></a4j:commandButton>:

		<h:outputText value="limitToList"></h:outputText>
		<h:selectBooleanCheckbox value="#{scrollableDT.limitToList}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="bypassUpdates:"></h:outputText>
		<h:selectBooleanCheckbox value="#{scrollableDT.bypassUpdates}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="ajaxSingle:"></h:outputText>
		<h:selectBooleanCheckbox value="#{scrollableDT.ajaxSingle}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="rendered:"></h:outputText>
		<h:selectBooleanCheckbox value="#{scrollableDT.rendered}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="hideWhenScrolling:" />
		<h:selectBooleanCheckbox value="#{scrollableDT.hideWhenScrolling}"
			onchange="submit();">
		</h:selectBooleanCheckbox>

		<h:outputText value="sortMode" />
		<h:selectOneRadio value="#{sortingAndFiltering.sortMode}"
			onchange="submit();">
			<f:selectItem itemLabel="single" itemValue="single" />
			<f:selectItem itemLabel="multi" itemValue="multi" />
		</h:selectOneRadio>
	</h:panelGrid>
	<br />
	<%-- 
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<a4j:commandLink value="getSelection" reRender="findID"></a4j:commandLink>
		<h:outputText id="findID"
			value="#{rich:findComponent('sdt').selection}" />
	</h:panelGrid>
	--%>
	<a4j:commandButton value="reRender" reRender="dataList"></a4j:commandButton>
</f:subview>