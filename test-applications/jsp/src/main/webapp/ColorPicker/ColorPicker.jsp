<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>


<f:subview id="colorPickerSubviewID">
	<rich:colorPicker binding="#{colorPicker.component}"
		colorMode="#{colorPicker.colorMode}" converter="colorPickerConverter"
		converterMessage="#{colorPicker.converterMessage}"
		flat="#{colorPicker.flat}" id="colorPickerID"
		immediate="#{colorPicker.immediate}" onclick="#{event.onclick}"
		ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}"
		onchange="#{event.onchange}"
		onhide="#{event.onhide}"
		onshow="#{event.onshow}"
		onselect="#{event.onselect}"
		onbeforeshow="#{event.onbeforeshow}"
		rendered="#{colorPicker.rendered and !colorPicker.facets}"
		required="#{colorPicker.required}" value="#{colorPicker.value}"
		showEvent="#{colorPicker.showEvent}"
		validator="#{colorPicker.validate}"
		validatorMessage="#{colorPicker.validatorMessage}"
		valueChangeListener="#{colorPicker.changeValue}">
	</rich:colorPicker>

	<rich:colorPicker colorMode="#{colorPicker.colorMode}"
		flat="#{colorPicker.flat}" id="colorPickerIDFacets"
		onclick="#{event.onclick}" ondblclick="#{event.ondblclick}"
		onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}"
		onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"
		rendered="#{colorPicker.rendered and colorPicker.facets}"
		showEvent="#{colorPicker.showEvent}"
		validator="#{colorPicker.validate}"
		validatorMessage="#{colorPicker.validatorMessage}"
		valueChangeListener="#{colorPicker.changeValue}">
		<f:facet name="icon">
			<h:graphicImage value="/ColorPicker/images/colorPicker_ico.png"
				width="18px" height="18px"></h:graphicImage>
		</f:facet>
	</rich:colorPicker>

	<rich:colorPicker>
		<f:facet name="icon">
			<h:graphicImage value="/ColorPicker/images/colorPicker_ico.png"
				width="18px" height="18px"></h:graphicImage>
		</f:facet>
	</rich:colorPicker>

</f:subview>
