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
	<br/>
	
	<h:form id="_form3">
	<progressBar:progressBar value="#{bean.incValue1}" enabled="#{bean.enabled1}" id="progrs1"
			interval="500" 
			reRender="per11"
			reRenderAfterComplete="per12"
			mode="#{bean.modeString1}"
			progressVar="percent1"
			parameters="text:'crack'"
			style="width: 300px; height: 14px;"
			onclick="alert('');">
		<f:facet name="initial">
			<h:outputText value="Process not started"></h:outputText>
		</f:facet>
		<f:facet name="complete">
			<h:outputText value="Process completed"></h:outputText>
		</f:facet>
		<h:outputText value="{value}%"></h:outputText>
	</progressBar:progressBar> 
	
	<progressBar:progressBar value="#{bean.incValue2}" enabled="#{bean.enabled2}" id="progrs2"
			interval="1000" 
			reRender="per21"
			reRenderAfterComplete="per22"
			mode="#{bean.modeString2}"
			progressVar="percent2"
			parameters="text:'crack'"
			style="width: 300px; height: 14px;"
			actionListener="#{bean.listener2}">
		<f:facet name="initial">
			<h:outputText value="Process not started"></h:outputText>
		</f:facet>
		<f:facet name="complete">
			<h:outputText value="Process completed"></h:outputText>
		</f:facet>
		<h:outputText value="{value}%"></h:outputText>
	</progressBar:progressBar>
	
	</h:form>
	
	
	<table><tr><td>
	

	<h:form>
		<h:panelGrid columns="3">
			<h:outputText value="Progress1 value: " />
			<h:inputText value="#{bean.value1}" />
			
			<h:commandButton value="Set" />
		</h:panelGrid>
	
	</h:form>
		
	<h:form id="_form1">
	
		
	<table><tr>
	<td>ReRender:</td><td><h:outputText value="#{bean.date1}" id="per11"></h:outputText></td></tr><tr>
	<td>ReRender after complete:</td><td><h:outputText value="#{bean.date1}" id="per12"></h:outputText></td>
	</tr></table></h:form>
	
	<h:form>
	Enabled: <h:selectBooleanCheckbox value="#{bean.enabled1}" id="flag">
		<a4j:support event="onclick" reRender="progrs1"></a4j:support>
	</h:selectBooleanCheckbox>

	<f:verbatim><br /></f:verbatim>


	Ajax mode: <h:selectBooleanCheckbox value="#{bean.ajaxMode1}" id="flag3">
		<a4j:support event="onclick" reRender="progrs1"></a4j:support>
	</h:selectBooleanCheckbox>
	<f:verbatim><br /></f:verbatim>
	</h:form>
	<script>
	var pr = $('_form3:progrs1').component;
	</script>
	<input type="button" value="Get value" onclick="alert($('_form3:progrs1').component.getValue());" /><br/>
	<input type="text" value="" id="percent1"/>
	<input type="button" value="Set value" onclick="$('_form3:progrs1').component.setValue(document.getElementById('percent1').value);" />
	<input type="button" id="ctrlValueButton1" value="Set value by componentControl" />
	<cctrl:componentControl attachTo="ctrlValueButton1" for="progrs1" event="click" disableDefault="true" operation="setValue">
		<a4j:actionparam name="value" value="$F('percent1')" noEscape="true" />
	</cctrl:componentControl>
	<br/>

	<input type="text" value="111" id="label1"/>
	<input type="button" value="Set label" onclick="$('_form3:progrs1').component.setLabel(document.getElementById('label1').value);" />
	<input type="button" id="ctrlLabelButton1" value="Set label by componentControl" />
	<cctrl:componentControl attachTo="ctrlLabelButton1" for="progrs" event="click" disableDefault="true" operation="setLabel">
		<a4j:actionparam name="label" value="$F('label1')" noEscape="true" />
	</cctrl:componentControl>
	<br/>
	<input type="button" value="Disable" onclick="$('_form3:progrs1').component.disable();" />
	<br/>
	<input type="button" value="Enable" onclick="$('_form3:progrs1').component.enable(event);" />
	<br />
	</td>
	<td>
	
	

	<h:form>
		<h:panelGrid columns="3">
			<h:outputText value="Progress value: " />
			<h:inputText value="#{bean.value2}" />
			
			<h:commandButton value="Set" />
		</h:panelGrid>
	
	</h:form>
		
	<h:form id="_form2">
	
	<br clear="all"/>
	<table><tr>
	<td>ReRender:</td><td><h:outputText value="#{bean.date2}" id="per21"></h:outputText></td></tr><tr>
	<td>ReRender after complete:</td><td><h:outputText value="#{bean.date2}" id="per22"></h:outputText></td>
	</tr></table></h:form>
	
	<h:form>
	Enabled: <h:selectBooleanCheckbox value="#{bean.enabled2}" id="flag">
		<a4j:support event="onclick" reRender="progrs2"></a4j:support>
	</h:selectBooleanCheckbox>

	<f:verbatim><br /></f:verbatim>


	Ajax mode: <h:selectBooleanCheckbox value="#{bean.ajaxMode2}" id="flag3">
		<a4j:support event="onclick" reRender="progrs2"></a4j:support>
	</h:selectBooleanCheckbox>
	<f:verbatim><br /></f:verbatim>
	</h:form>
	<script>
	var pr = $('_form3:progrs2').component;
	</script>
	<input type="button" value="Get value" onclick="alert($('_form3:progrs2').component.getValue());" /><br/>
	<input type="text" value="" id="percent2"/>
	<input type="button" value="Set value" onclick="$('_form3:progrs2').component.setValue(document.getElementById('percent2').value);" />
	<input type="button" id="ctrlValueButton2" value="Set value by componentControl" />
	<cctrl:componentControl attachTo="ctrlValueButton2" for="progrs2" event="click" disableDefault="true" operation="setValue">
		<a4j:actionparam name="value" value="$F('percent2')" noEscape="true" />
	</cctrl:componentControl>
	<br/>

	<input type="text" value="111" id="label2"/>
	<input type="button" value="Set label" onclick="$('_form3:progrs2').component.setLabel(document.getElementById('label2').value);" />
	<input type="button" id="ctrlLabelButton2" value="Set label by componentControl" />
	<cctrl:componentControl attachTo="ctrlLabelButton2" for="progrs2" event="click" disableDefault="true" operation="setLabel">
		<a4j:actionparam name="label" value="$F('label2')" noEscape="true" />
	</cctrl:componentControl>
	<br/>
	<input type="button" value="Disable" onclick="$('_form3:progrs2').component.disable();" />
	<br/>
	<input type="button" value="Enable" onclick="$('_form3:progrs2').component.enable(event);" />
	<br />
	
	</td>
	</tr></table><br/>
	
		
	</f:view>
	</body>	
</html>
