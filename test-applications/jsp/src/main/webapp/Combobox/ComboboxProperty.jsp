<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="comboboxPropertySubviewID">
	<h:commandButton value="add test" action="#{combobox.addHtmlCombobox}"></h:commandButton>

	<h:panelGrid columns="2">
		<h:outputText value="defaultLabel"></h:outputText>
		<h:inputText value="#{combobox.defaultLabel}" onchange="submit();"></h:inputText>

		<h:outputText value="filterNewValues"></h:outputText>
		<h:selectBooleanCheckbox value="#{combobox.filterNewValues}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="tabindex"></h:outputText>
		<h:inputText value="#{combobox.tabindex}" onchange="submit();"></h:inputText>
		<h:outputText value="width"></h:outputText>
		<h:inputText value="#{combobox.width}" onchange="submit();"></h:inputText>

		<h:outputText value="listHeight"></h:outputText>
		<h:inputText value="#{combobox.listHeight}" onchange="submit();"></h:inputText>

		<h:outputText value="listWidth"></h:outputText>
		<h:inputText value="#{combobox.listWidth}" onchange="submit();"></h:inputText>

		<h:outputText value="enableManualInput"></h:outputText>
		<h:selectBooleanCheckbox value="#{combobox.enableManualInput}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="selectFirstOnUpdate"></h:outputText>
		<h:selectBooleanCheckbox value="#{combobox.selectFirstOnUpdate}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="directInputSuggestions"></h:outputText>
		<h:selectBooleanCheckbox value="#{combobox.directInputSuggestions}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="disabled"></h:outputText>
		<h:selectBooleanCheckbox value="#{combobox.disabled}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="rendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{combobox.rendered}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="immediate"></h:outputText>
		<h:selectBooleanCheckbox value="#{combobox.immediate}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="required"></h:outputText>
		<h:selectBooleanCheckbox value="#{combobox.required}" onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="requiredMessage"></h:outputText>
		<h:inputText value="#{combobox.requiredMessage}" onchange="submit();"></h:inputText>
		
		<h:commandButton actionListener="#{combobox.checkBinding}" value="Binding"></h:commandButton>
		<h:outputText value="#{combobox.bindLabel}"></h:outputText>
		
		<h:outputText value="align"></h:outputText>
		<h:inputText value="#{combobox.align}" onchange="submit();"></h:inputText>	
	</h:panelGrid>
	
	<a4j:commandLink onclick="$('formID:comboboxSubviewID:comboboxID').component.showList()" value="showList"></a4j:commandLink> <br/>
	<a4j:commandLink onclick="$('formID:comboboxSubviewID:comboboxID').component.hideList()" value="hideList"></a4j:commandLink> <br/>
	<a4j:commandLink onclick="$('formID:comboboxSubviewID:comboboxID').component.enable()" value="enable"></a4j:commandLink> <br/>
	<a4j:commandLink onclick="$('formID:comboboxSubviewID:comboboxID').component.disable()" value="disable"></a4j:commandLink>
	<br />
	<f:verbatim>
	<h:outputText value="Component controll testing" style="FONT-WEIGHT: bold;"></h:outputText>
	<br />
	<a href="#" id="showListID">showList</a>
	<br />
	<a href="#" id="hideListID">hideList</a>
	<br />
	<a href="#" id="enableID">enable</a>
	<br />
	<a href="#" id="disableID">disable</a>
	</f:verbatim> 
	<rich:componentControl attachTo="showListID" event="onclick" for="comboboxID" operation="showList"></rich:componentControl>
	<rich:componentControl attachTo="hideListID" event="onclick" for="comboboxID" operation="hideList"></rich:componentControl>
	<rich:componentControl attachTo="enableID" event="onclick" for="comboboxID" operation="enable"></rich:componentControl>
	<rich:componentControl attachTo="disableID" event="onclick" for="comboboxID" operation="disable"></rich:componentControl>
	<br/>
	<br/>
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
	<rich:column>
	<a4j:commandLink value="getValue" reRender="findID"></a4j:commandLink>
	</rich:column>
	<rich:column id="findID">
	<h:outputText value="#{rich:findComponent('comboboxID').value}" />	
	</rich:column>
	</h:panelGrid>	
</f:subview>