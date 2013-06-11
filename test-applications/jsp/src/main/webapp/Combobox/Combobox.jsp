<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="comboboxSubviewID">

	<h:inputText value="For test tabbing tabindex = 3" tabindex="3"
		size="30"></h:inputText>
	<br />

	<rich:comboBox id="comboboxID" disabled="#{combobox.disabled}"
		defaultLabel="#{combobox.defaultLabel}"
		buttonClass="#{style.buttonClass}"
		buttonDisabledClass="#{style.buttonDisabledClass}"
		buttonDisabledStyle="#{style.buttonDisabledStyle}"
		buttonInactiveClass="#{style.buttonInactiveClass}"
		buttonInactiveStyle="#{style.buttonInactiveStyle}"
		buttonStyle="#{style.buttonStyle}" inputClass="#{style.inputClass}"
		inputDisabledClass="#{style.inputDisabledClass}"
		inputDisabledStyle="#{style.inputDisabledStyle}"
		inputInactiveClass="#{style.inputInactiveClass}"
		inputInactiveStyle="#{style.inputInactiveStyle}"
		itemClass="#{style.itemClass}" inputStyle="#{style.inputStyle}"
		listClass="#{style.listClass}" listStyle="#{style.listStyle}"
		filterNewValues="#{combobox.filterNewValues}"
		directInputSuggestions="#{combobox.directInputSuggestions}"
		immediate="#{combobox.immediate}" width="#{combobox.width}"
		valueChangeListener="#{combobox.valueChangeListener}"
		tabindex="#{combobox.tabindex}"
		suggestionValues="#{combobox.suggestionValues}"
		required="#{combobox.required}"
		requiredMessage="#{combobox.requiredMessage}"
		rendered="#{combobox.rendered}"
		selectFirstOnUpdate="#{combobox.selectFirstOnUpdate}"
		enableManualInput="#{combobox.enableManualInput}"
		listHeight="#{combobox.listHeight}" listWidth="#{combobox.listWidth}"
		style="#{style.style}" styleClass="#{style.styleClass}"
		onblur="#{event.onblur}" onchange="#{event.onchange}"
		onclick="#{event.onclick}" ondblclick="#{event.ondblclick}"
		onfocus="#{event.onfocus}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
		onlistcall="#{event.onlistcall}" onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"
		onselect="#{event.onselect}" binding="#{combobox.htmlComboBox}">
		<f:selectItem itemValue="Gosha" />
		<f:selectItem itemValue="DubSer_1" />
		<f:selectItem itemValue="DubSer_2" />
	</rich:comboBox>

	<a4j:commandButton value="reRender" reRender="comboboxID"></a4j:commandButton>

</f:subview>