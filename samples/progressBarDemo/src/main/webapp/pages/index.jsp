<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/progressBar" prefix="progressBar" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/componentControl" prefix="cctrl" %>
<html>
	<head>
		<title></title>
		<style>
		body{font-size : 11px}
			.complete {
				background-color: green; 
				color: white;
				}
			.remain {
				background-color: #FCBBCD;
			}
			.main {
				font-size: 12px;
				font-weight: bold;
			}
		</style>
	</head>
	<body>
	<f:view>
	
	<h:form>
           <h:selectOneRadio binding="#{skinBean.component}" />
           <h:commandLink action="#{skinBean.change}" value="set skin" />
	</h:form>

	<h:form>
		<h:panelGrid columns="3">
			<h:outputText value="Progress value: " />
			<h:inputText value="#{bean.value}" />
			
			<h:commandButton value="Set" />
		</h:panelGrid>
	
	</h:form>
		
	<h:form id="_form">
	
	<progressBar:progressBar value="#{bean.incValue}" enabled="#{bean.enabled}" id="progrs"
			interval="700" 
			reRender="per1"
			reRenderAfterComplete="per2"
			mode="#{bean.modeString}"
			progressVar="percent"
			parameters="text:'crack'"
			style="width: 300px; height: 14px;"
			action="#{bean.action}">
		<f:facet name="initial">
			<h:outputText value="Process not started"></h:outputText>
		</f:facet>
		<f:facet name="complete">
			<h:outputText value="Process completed"></h:outputText>
		</f:facet>
		<h:outputText value="{value}%"></h:outputText>
	</progressBar:progressBar>
	<br clear="all"/>
	<table><tr>
	<td>ReRender:</td><td><h:outputText value="#{bean.date}" id="per1"></h:outputText></td></tr><tr>
	<td>ReRender after complete:</td><td><h:outputText value="#{bean.date}" id="per2"></h:outputText></td>
	</tr></table></h:form>
	
	<h:form>
	Enabled: <h:selectBooleanCheckbox value="#{bean.enabled}" id="flag">
		<a4j:support event="onclick" reRender="progrs"></a4j:support>
	</h:selectBooleanCheckbox>

	<f:verbatim><br /></f:verbatim>


	Ajax mode: <h:selectBooleanCheckbox value="#{bean.ajaxMode}" id="flag3">
		<a4j:support event="onclick" reRender="progrs"></a4j:support>
	</h:selectBooleanCheckbox>
	<f:verbatim><br /></f:verbatim>
	</h:form>
	<script>
	var pr = $('_form:progrs').component;
	</script>
	<input type="button" value="Get value" onclick="alert($('_form:progrs').component.getValue());" /><br/>
	<input type="text" value="" id="percent"/>
	<input type="button" value="Set value" onclick="$('_form:progrs').component.setValue(document.getElementById('percent').value);" />
	<input type="button" id="ctrlValueButton" value="Set value by componentControl" />
	<cctrl:componentControl attachTo="ctrlValueButton" for="progrs" event="click" disableDefault="true" operation="setValue">
		<a4j:actionparam name="value" value="$F('percent')" noEscape="true" />
	</cctrl:componentControl>
	<br/>

	<input type="text" value="111" id="label"/>
	<input type="button" value="Set label" onclick="$('_form:progrs').component.setLabel(document.getElementById('label').value);" />
	<input type="button" id="ctrlLabelButton" value="Set label by componentControl" />
	<cctrl:componentControl attachTo="ctrlLabelButton" for="progrs" event="click" disableDefault="true" operation="setLabel">
		<a4j:actionparam name="label" value="$F('label')" noEscape="true" />
	</cctrl:componentControl>
	<br/>
	<input type="button" value="Disable" onclick="$('_form:progrs').component.disable();" />
	<br/>
	<input type="button" value="Enable" onclick="$('_form:progrs').component.enable(event);" />
	<br />
	</f:view>
	</body>	
</html>
