<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="inplaceSelectSubviewID">

<h:inputText value="For verify tabbing tabindex=3" size="25" tabindex="3"></h:inputText> <br/>

This is because such an error can easily be made at programming level, <br />
and while invisible for the user who does not understand or cannot <br />
acquire the source code, many of those 
<rich:inplaceSelect id="inplaceSelectID"		
		tabindex="#{inplaceSelect.tabindex}"
		editEvent="#{inplaceSelect.editEvent}"
		maxSelectWidth="#{inplaceSelect.maxSelectWidth}"
		minSelectWidth="#{inplaceSelect.minSelectWidth}"
		selectWidth="#{inplaceSelect.selectWidth}"
		defaultLabel="#{inplaceSelect.defaultLabel}"
		controlsVerticalPosition="#{inplaceSelect.controlsVerticalPosition}"
		controlsHorizontalPosition="#{inplaceSelect.controlsHorizontalPosition}"
		listWidth="#{inplaceSelect.listWidth}"
		listHeight="#{inplaceSelect.listHeight}"
		showControls="#{inplaceSelect.showControls}"
		openOnEdit="#{inplaceSelect.openOnEdit}"
		rendered="#{inplaceSelect.rendered}"
		immediate="#{inplaceSelect.immediate}"
		binding="#{inplaceSelect.myInplaceSelect}"
		required="#{inplaceSelect.required}"
		requiredMessage="#{inplaceSelect.requiredMessage}"
		valueChangeListener="#{inplaceSelect.valueChangeListener}"
		onblur="#{event.onblur}"  
		onchange="#{event.onchange}" 
		onclick="#{event.onclick}" 
		ondblclick="#{event.ondblclick}" 
		oneditactivated="#{event.oneditactivated}"
		oneditactivation="#{event.oneditactivation}"
		onfocus="#{event.onfocus}" 
		oninputblur="#{event.oninputblur}"
		oninputclick="#{event.oninputclick}"		
		oninputdblclick="#{event.oninputdblclick}"
		oninputfocus="#{event.oninputfocus}"
		oninputkeydown="#{event.oninputkeydown}"
		oninputkeypress="#{event.oninputkeypress}"
		oninputkeyup="#{event.oninputkeyup}"
		oninputmousedown="#{event.oninputmousedown}"
		oninputmousemove="#{event.oninputmousemove}"
		oninputmouseout="#{event.oninputmouseout}"
		oninputmouseover="#{event.oninputmouseover}"
		oninputmouseup="#{event.oninputmouseup}"
		onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}"
		onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}"
		onselect="#{event.onselect}"
		onviewactivated="#{event.onviewactivated}"
		onviewactivation="#{event.onviewactivation}"
		layout="#{inplaceSelect.layout}"
		value="#{inplaceSelect.value}" >
		
		<f:selectItem itemLabel="errors" itemValue="errors" />
		<f:selectItem itemLabel="fatals" itemValue="fatals" />
		<f:selectItem itemLabel="infos" itemValue="infos" />
		<f:selectItem itemLabel="passeds" itemValue="passeds" />
		<f:selectItem itemLabel="warns" itemValue="warns" />
	</rich:inplaceSelect>
 are easy to exploit.
 
 	<h:panelGrid columns="2">
		<a4j:commandButton value="refresh" reRender="inplaceSelectValueCLID, inplaceSelectID"></a4j:commandButton>
		<h:outputText id="inplaceSelectValueCLID"
			value="#{inplaceSelect.valueCL}"></h:outputText>
	</h:panelGrid>

</f:subview>