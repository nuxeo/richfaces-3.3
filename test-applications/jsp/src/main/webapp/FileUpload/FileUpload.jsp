<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<style>
.addButton {
	color: white;
	background-color: red;
}
</style>
<f:subview id="FileUploadSubviewID">
	<rich:fileUpload id="fileUploadID" status="a4jStatusID"
		allowFlash="#{fileUpload.allowFlash}"
		immediate="#{fileUpload.immediate}"
		ajaxSingle="true" uploadData="#{fileUpload.data}"
		acceptedTypes="#{fileUpload.acceptedTypes}"
		disabled="#{fileUpload.disabled}" autoclear="#{fileUpload.autoclear}"
		required="#{fileUpload.required}" requiredMessage="#{item.fileName}"
		rendered="#{fileUpload.rendered}"
		listHeight="#{fileUpload.listHeight}"
		listWidth="#{fileUpload.listWidth}"
		maxFilesQuantity="#{fileUpload.maxFilesQuantity}"
		fileUploadListener="#{fileUpload.fileUploadListener}"
		accesskey="y"
		alt="alternative" binding="#{fileUpload.myFileUpload}"
		addButtonClass="#{fileUpload.addButtonClass}"
		addButtonClassDisabled="#{fileUpload.addButtonClassDisabled}"
		cleanButtonClass="#{fileUpload.cleanButtonClass}"
		cleanButtonClassDisabled="#{fileUpload.cleanButtonClassDisabled}"
		fileEntryClass="#{fileUpload.fileEntryClass}"
		fileEntryControlClass="#{fileUpload.fileEntryControlClass}"
		fileEntryControlClassDisabled="#{fileUpload.fileEntryControlClassDisabled}"
		fileEntryClassDisabled="#{fileUpload.fileEntryControlClassDisabled}"
		uploadButtonClass="#{fileUpload.uploadButtonClass}"
		uploadButtonClassDisabled="#{fileUpload.uploadButtonClassDisabled}"
		uploadListClass="#{fileUpload.uploadListClass}"
		uploadListClassDisabled="#{fileUpload.uploadListClassDisabled}"
		immediateUpload="#{fileUpload.immediateUpload}" locale="ru"
		noDuplicate="#{fileUpload.noDuplicate}" tabindex="222"
		addControlLabel="addControl" clearAllControlLabel="clearAllControl"
		cancelEntryControlLabel="cancelEntryControl"
		clearControlLabel="clearControl" doneLabel="done"
		progressLabel="progress" sizeErrorLabel="sizeError"
		stopControlLabel="stopControl"
		stopEntryControlLabel="stopEntryControl"
		transferErrorLabel="transferError" uploadControlLabel="uploadControl"
		onmousemove="#{event.onmousemove}" 
		onadd="#{event.onadd}"
		onblur="#{event.onblur}"
		onchange="#{event.onchange}"
		onclear="#{event.onclear}"
		onclick="#{event.onclick}"
		ondblclick="#{event.ondblclick}"
		onerror="#{event.onerror}"
		onfocus="#{event.onfocus}"
		onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onmousemove}"
		onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}"
		onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}"
		onselect="#{event.onselect}"
		onsizerejected="#{event.onsizerejected}"
		ontyperejected="#{event.ontyperejected}"
		onupload="#{event.onupload}"
		onuploadcanceled="#{event.onuploadcanceled}"
		onuploadcomplete="#{event.onuploadcomplete}">
		<f:facet name="label">
			<h:outputText value="{_KB}KB from {KB}KB uploaded :[ {mm}:{ss} ]"></h:outputText>
		</f:facet>
		<f:facet name="header">
			<h:outputText value="some text"></h:outputText>
		</f:facet>
	</rich:fileUpload>

	<h:panelGrid columns="2">
		<a4j:commandButton value="show file" reRender="fileUploadDataID"></a4j:commandButton>
		<h:dataTable id="fileUploadDataID" value="#{fileUpload.data}"
			var="item">
			<h:column>
				<h:outputText value="#{item.fileName}"></h:outputText>
			</h:column>
		</h:dataTable>
	</h:panelGrid>
<h:commandButton value="add test" action="#{fileUpload.addHtmlFileUpload}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="acceptedTypes"></h:outputText>
		<h:inputText value="#{fileUpload.acceptedTypes}" onchange="submit();"></h:inputText>

		<h:outputText value="listHeight"></h:outputText>
		<h:inputText value="#{fileUpload.listHeight}" onchange="submit();"></h:inputText>

		<h:outputText value="listWidth"></h:outputText>
		<h:inputText value="#{fileUpload.listWidth}" onchange="submit();"></h:inputText>
		
		<h:outputText value="autoclear"></h:outputText>
		<h:selectBooleanCheckbox value="#{fileUpload.autoclear}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="disabled"></h:outputText>
		<h:selectBooleanCheckbox value="#{fileUpload.disabled}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="rendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{fileUpload.rendered}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="required"></h:outputText>
		<h:selectBooleanCheckbox value="#{fileUpload.required}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="immediateUpload"></h:outputText>
		<h:selectBooleanCheckbox value="#{fileUpload.immediateUpload}">
			<a4j:support event="onchange" reRender="fileUploadID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="requiredMessage"></h:outputText>
		<h:inputText value="#{fileUpload.requiredMessage}"
			onchange="submit();"></h:inputText>

		<h:outputText value="Align"></h:outputText>
		<h:selectOneRadio value="#{fileUpload.align}">
			<a4j:support event="onchange" reRender="fileUploadID"></a4j:support>
			<f:selectItem itemLabel="right" itemValue="right" />
			<f:selectItem itemLabel="left" itemValue="left" />
			<f:selectItem itemLabel="center" itemValue="center" />
		</h:selectOneRadio>

		<h:commandButton actionListener="#{fileUpload.checkBinding}"
			value="Binding"></h:commandButton>
		<h:outputText value="#{fileUpload.bindLabel}"></h:outputText>

		<h:outputText value="noDuplicate"></h:outputText>
		<h:selectBooleanCheckbox value="#{fileUpload.noDuplicate}">
			<a4j:support event="onchange" reRender="fileUploadID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="ajaxSingle"></h:outputText>
		<h:selectBooleanCheckbox value="#{fileUpload.ajaxSingle}">
			<a4j:support event="onchange" reRender="fileUploadID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="immediate"></h:outputText>
		<h:selectBooleanCheckbox value="#{fileUpload.immediate}">
			<a4j:support event="onchange" reRender="fileUploadID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="Uploaded data:"></h:outputText>
		<h:outputText value="#{fileUpload.changedLabel}"></h:outputText>

		<h:outputText value="allowFlash" />
		<h:selectOneRadio value="#{fileUpload.allowFlash}">
			<f:selectItem itemValue="true" itemLabel="true" />
			<f:selectItem itemValue="false" itemLabel="false" />
			<f:selectItem itemValue="auto" itemLabel="auto" />
			<a4j:support event="onchange" reRender="fileUploadID"></a4j:support>
		</h:selectOneRadio>

		<a4j:commandLink
			onclick="$('formID:FileUploadSubviewID:fileUploadID').component.enable()"
			value="enable()"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:FileUploadSubviewID:fileUploadID').component.disable()"
			value="disable()"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:FileUploadSubviewID:fileUploadID').component.stop()"
			value="stop()"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:FileUploadSubviewID:fileUploadID').component.clear()"
			value="clear()"></a4j:commandLink>
		<h:commandButton
			onmousedown="return $('formID:FileUploadSubviewID:fileUploadID').component.beforeSubmit()"
			value="Submit"></h:commandButton>
	</h:panelGrid>
	<br />	
	<f:verbatim>
		<h:outputText value="Component controll" style="FONT-WEIGHT: bold;"></h:outputText>
		<br />
		<a href="#" id="enableID">enable()</a>
		<br />
		<a href="#" id="disableID">disable()</a>
		<br />
		<a href="#" id="stopID">stop()</a>
		<br />
		<a href="#" id="clearID">clear()</a>
	</f:verbatim>

	<rich:componentControl attachTo="enableID" event="onclick"
		for="fileUploadID" operation="enable"></rich:componentControl>
	<rich:componentControl attachTo="disableID" event="onclick"
		for="fileUploadID" operation="disable"></rich:componentControl>
	<rich:componentControl attachTo="stopID" event="onclick"
		for="fileUploadID" operation="stop"></rich:componentControl>
	<rich:componentControl attachTo="clearID" event="onclick"
		for="fileUploadID" operation="clear"></rich:componentControl>	
</f:subview>