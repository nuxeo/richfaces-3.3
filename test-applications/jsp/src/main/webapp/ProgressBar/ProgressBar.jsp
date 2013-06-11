<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:subview id="progressBarSubviewID">
	<rich:progressBar id="progressBarID" binding="#{progressBar.htmlProgressBar}"
		ajaxSingle="#{progressBar.ajaxSingle}" label="#{progressBar.label}"
		immediate="#{progressBar.immediate}" value="#{progressBar.value}"
		style="width: 450px; height: 19px;#{style.style};"
		completeClass="#{style.completeClass}"
		finishClass="#{style.finishClass}"
		initialClass="#{style.initialClass}"
		remainClass="#{style.remainClass}" styleClass="#{style.styleClass}"
		mode="#{progressBar.mode}" enabled="#{progressBar.enabled}"
		actionListener="#{progressBar.actionListener}"
		interval="#{progressBar.interval}" maxValue="#{progressBar.maxValue}"
		minValue="#{progressBar.minValue}" rendered="#{progressBar.rendered}"
		reRender="loadInfoPBID,valuePBID" progressVar="progressVar"
		parameters="params:'%'" reRenderAfterComplete="completedPBID"
		ignoreDupResponses="#{progressBar.ignoreDupResponses}"
		onbeforedomupdate="#{event.onbeforedomupdate}"
		onclick="#{event.onclick}" oncomplete="#{event.oncomplete}"
		ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}" onsubmit="#{event.onsubmit}">
		<f:facet name="initial">
			<h:outputText value="Process not started"></h:outputText>
		</f:facet>
		<f:facet name="complete">
			<h:outputText value="Process completed"></h:outputText>
		</f:facet>
		<h:outputText
			value="{minValue} {params} / {value} {params}/ {maxValue} {params}"></h:outputText>
		<h:outputText id="loadInfoPBID"
			value="[Load: #{progressBar.loadInfo}]" />
		<h:graphicImage value="/pics/ajax_process.gif" />

	</rich:progressBar>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:outputText
		value="[parameters=params:'%'], [{progressVar} | {minValue} {params} / {value} {params}/ {maxValue} {params}]" />
	<h:panelGrid columns="1">
		<a4j:commandButton value="getValue"
			onclick="alert($('formID:progressBarSubviewID:progressBarID').component.getValue())"></a4j:commandButton>
		<a4j:commandLink value="setLabel"
			onclick="$('formID:progressBarSubviewID:progressBarID').component.setLabel('setLabel work')"></a4j:commandLink>
		<a4j:commandLink value="setValue(5)"
			onclick="$('formID:progressBarSubviewID:progressBarID').component.setValue('5')"></a4j:commandLink>
		
		<a4j:commandLink value="disable"
			onclick="$('formID:progressBarSubviewID:progressBarID').component.disable();"></a4j:commandLink>
		<a4j:commandLink value="enable"
			onclick="$('formID:progressBarSubviewID:progressBarID').component.enable(event);"></a4j:commandLink>
	</h:panelGrid>
	<br />
	<f:verbatim>
		<h:outputText value="Component control test"
			style="FONT-WEIGHT: bold;"></h:outputText>
		<br />
		<a href="#" id="setLabelID">setLabel</a>
		<br />
		<a href="#" id="setValueID">setValue(10)</a>
		<br />
		<a href="#" id="disableID">disable</a>
		<br />
		<a href="#" id="enableID">enable</a>
	</f:verbatim>
	<rich:componentControl attachTo="setLabelID" event="onclick"
		for="progressBarID" operation="setLabel" attachTiming="onload">
		<f:param name="label" value="new label" />
	</rich:componentControl>
	<rich:componentControl attachTo="setValueID" event="onclick"
		for="progressBarID" operation="setValue">
			<f:param name="value" value="10"/>
		</rich:componentControl>
	<rich:componentControl attachTo="disableID" event="onclick"
		for="progressBarID" operation="disable"></rich:componentControl>
	<rich:componentControl attachTo="enableID" event="onclick"
		for="progressBarID" operation="enable"></rich:componentControl>
	<h:panelGrid columns="2">
		<h:outputText value="first value:" />
		<h:outputText value="#{progressBar.value}" />
		<h:outputText value="completed value:" />
		<h:outputText id="completedPBID" value="#{progressBar.value}" />
	</h:panelGrid>

	<h:panelGrid columns="2">
		<h:outputText value="value:" />
		<h:inputText id="valuePBID" value="#{progressBar.value}">
			<a4j:support event="onchange" reRender="progressBarID"></a4j:support>
		</h:inputText>
		<h:outputText value="label:" />
		<h:inputText value="#{progressBar.label}">
			<a4j:support event="onchange" reRender="progressBarID"></a4j:support>
		</h:inputText>

		<h:outputText value="interval:" />
		<h:inputText value="#{progressBar.interval}">
			<a4j:support event="onchange" reRender="progressBarID"></a4j:support>
		</h:inputText>

		<h:outputText value="mode:" />
		<h:selectOneRadio value="#{progressBar.mode}">
			<f:selectItem itemValue="ajax" itemLabel="ajax" />
			<f:selectItem itemValue="client" itemLabel="client" />
			<a4j:support event="onchange" reRender="progressBarID"></a4j:support>
		</h:selectOneRadio>

		<h:outputText value="enabled" />
		<h:selectBooleanCheckbox value="#{progressBar.enabled}">
			<a4j:support event="onchange" reRender="progressBarID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="ajaxSingle" />
		<h:selectBooleanCheckbox value="#{progressBar.ajaxSingle}">
			<a4j:support event="onchange" reRender="progressBarID"></a4j:support>
		</h:selectBooleanCheckbox>

		<h:outputText value="permanent:" />
		<h:selectBooleanCheckbox value="#{progressBar.permanent}"
			onchange="submit();" />

		<h:outputText value="minValue:" />
		<h:inputText value="#{progressBar.minValue}" onchange="submit();" />

		<h:outputText value="maxValue:" />
		<h:inputText value="#{progressBar.maxValue}" onchange="submit();" />

		<h:outputText value="ignoreDupResponses" />
		<h:selectBooleanCheckbox value="#{progressBar.ignoreDupResponses}"
			onchange="submit();" />

		<h:outputText value="rendered:" />
		<h:selectBooleanCheckbox value="#{progressBar.rendered}"
			onchange="submit();" />

		<h:outputText value="immediate" />
		<h:selectBooleanCheckbox value="#{progressBar.immediate}">
			<a4j:support event="onchange" reRender="progressBarID"></a4j:support>
		</h:selectBooleanCheckbox>

	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getValue" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column>
			<h:outputText value="#{rich:findComponent('progressBarID').value}" id="findID"/>
		</rich:column>
	</h:panelGrid>
	<h:commandButton value="add test" action="#{progressBar.addHtmlProgressBar}"></h:commandButton>
</f:subview>