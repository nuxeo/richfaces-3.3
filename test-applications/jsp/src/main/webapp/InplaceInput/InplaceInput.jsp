<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<script type="text/javascript">
<!--
function onClick() {

}
//-->
</script>
<f:subview id="inplaceInputSubviewID">
	<h:inputText value="For verify tabbing tabindex=3" size="25" tabindex="3"></h:inputText>
	<f:verbatim><br /><br /></f:verbatim>
	This is because such an error can easily be made at programming level, <br />
	and while invisible for the user who does not understand or cannot <br />
	acquire the source code, many of those 	
	<rich:inplaceInput id="inplaceInputId"
		valueChangeListener="#{inplaceInput.valueChangeListener}"
		converter="inplaceInputConverter" converterMessage="Can't convert"
		inputWidth="#{inplaceInput.inputWidth}"
		maxInputWidth="#{inplaceInput.maxInputWidth}"
		minInputWidth="#{inplaceInput.minInputWidth}"
		required="#{inplaceInput.required}"
		requiredMessage="#{inplaceInput.requiredMessage}"
		tabindex="#{inplaceInput.tabindex}"
		editEvent="#{inplaceInput.editEvent}"
		defaultLabel="#{inplaceInput.defaultLabel}"
		controlsVerticalPosition="#{inplaceInput.controlsVerticalPosition}"
		controlsHorizontalPosition="#{inplaceInput.controlsHorizontalPosition}"
		value="#{inplaceInput.value}"
		selectOnEdit="#{inplaceInput.selectOnEdit}"
		showControls="#{inplaceInput.showControls}"
		rendered="#{inplaceInput.rendered}"
		immediate="#{inplaceInput.immediate}" layout="#{inplaceInput.layout}"
		onblur="#{event.onblur}" onchange="#{event.onchange}"
		onclick="#{event.onclick}" ondblclick="#{event.ondblclick}"
		oneditactivated="#{event.oneditactivated}"
		oneditactivation="#{event.oneditactivation}"
		onfocus="#{event.onfocus}" oninputclick="#{event.oninputclick}"
		oninputdblclick="#{event.oninputdblclick}"
		oninputkeydown="#{event.oninputkeydown}"
		oninputkeypress="#{event.oninputkeypress}"
		oninputkeyup="#{event.oninputkeyup}"
		oninputmousedown="#{event.oninputmousedown}"
		oninputmousemove="#{event.oninputmousemove}"
		oninputmouseout="#{event.oninputmouseout}"
		oninputmouseover="#{event.oninputmouseover}"
		oninputmouseup="#{event.oninputmouseup}"
		onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}"
		onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"
		onselect="#{event.onselect}"
		onviewactivated="#{event.onviewactivated}"
		onviewactivation="#{event.onviewactivation}"
		binding="#{inplaceInput.htmlInplaceInput}"
		maxlength="#{inplaceInput.maxLength}">
	</rich:inplaceInput>
	 are easy to exploit. 
 
  	<h:panelGrid columns="2">
		<a4j:commandButton value="reRender" reRender="inplaceInputValueCLID, inplaceInputId"></a4j:commandButton>
		<h:outputText id="inplaceInputValueCLID"
			value="#{inplaceInput.valueCL}"></h:outputText>
	</h:panelGrid>
</f:subview>