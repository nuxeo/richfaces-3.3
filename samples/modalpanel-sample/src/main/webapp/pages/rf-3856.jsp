<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!-- DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" -->

<!-- !doctype html public "-//w3c//dtd html 4.0 transitional//en"-->

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/modal-panel"
	prefix="mp"%>

<html>
<head>
<title></title>

<style type="text/css">
.pointerCursor {
	cursor: pointer;
}
</style>
</head>
<body style="text-align: center;">
<f:view>

	<mp:modalPanel style="border: 1px solid navy;" minHeight="100" top="10"
		width="450" id="_panel" controlsClass="pointerCursor">
		<f:facet name="header">
			<f:verbatim>
				Header
			</f:verbatim>
		</f:facet>

		<f:facet name="controls">
			<h:graphicImage value="/images/ico_close.gif"
				onclick="Richfaces.hideModalPanel('_panel');" />
		</f:facet>

		<h:form id="_form">
			<f:verbatim>
				Modal panel is here!
			</f:verbatim>

			<h:panelGroup id="inputsGroup">

				<h:selectBooleanCheckbox value="#{bean.checked}" />

				<h:selectOneRadio value="#{bean.radio}">
					<f:selectItem itemLabel="hp" itemValue="hp" />
					<f:selectItem itemLabel="ibm" itemValue="ibm" />
					<f:selectItem itemLabel="dell" itemValue="dell" />
				</h:selectOneRadio>
			</h:panelGroup>

			<a4j:commandLink value="Update inputs" reRender="inputsGroup" />

		</h:form>


	</mp:modalPanel>
	<f:verbatim>
		<a href="javascript:Richfaces.showModalPanel('_panel');">Show</a>
	</f:verbatim>

</f:view>
</body>
</html>
