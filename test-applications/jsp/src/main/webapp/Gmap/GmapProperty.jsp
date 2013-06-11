<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="GmapPropertySubviewID">
<h:commandButton value="add test" action="#{gmap.addHtmlGmap}"></h:commandButton>
	<h:panelGrid columns="2">
		<h:outputText value="mapType"></h:outputText>
		<h:selectOneMenu value="#{gmap.mapType}" onchange="submit();">
			<f:selectItem itemLabel="G_NORMAL_MAP" itemValue="G_NORMAL_MAP" />
			<f:selectItem itemLabel="G_SATELLITE_MAP" itemValue="G_SATELLITE_MAP" />
			<f:selectItem itemLabel="G_HYBRID_MAP" itemValue="G_HYBRID_MAP" />
		</h:selectOneMenu>

		<h:outputText value="enableInfoWindow:"></h:outputText>
		<h:selectOneRadio value="#{gmap.enableInfoWindow}"
			onchange="submit();">
			<f:selectItem itemLabel="on" itemValue="true" />
			<f:selectItem itemLabel="off" itemValue="false" />
		</h:selectOneRadio>

		<h:outputText value="showGLargeMapControl:"></h:outputText>
		<h:selectOneRadio value="#{gmap.showGLargeMapControl}"
			onchange="submit();">
			<f:selectItem itemLabel="on" itemValue="true" />
			<f:selectItem itemLabel="off" itemValue="false" />
		</h:selectOneRadio>


		<h:outputText value="showGMapTypeControl:"></h:outputText>
		<h:selectOneRadio value="#{gmap.showGMapTypeControl}"
			onchange="submit();">
			<f:selectItem itemLabel="on" itemValue="true" />
			<f:selectItem itemLabel="off" itemValue="false" />
		</h:selectOneRadio>


		<h:outputText value="showGScaleControl:"></h:outputText>
		<h:selectOneRadio value="#{gmap.showGScaleControl}"
			onchange="submit();">
			<f:selectItem itemLabel="on" itemValue="true" />
			<f:selectItem itemLabel="off" itemValue="false" />
		</h:selectOneRadio>

		<h:outputText value="warningMessage:"></h:outputText>
		<h:inputText value="#{gmap.warningMessage}" onchange="submit()"></h:inputText>

		<h:outputText value="lng:"></h:outputText>
		<h:inputText value="#{gmap.lng}" onchange="submit()"></h:inputText>

		<h:outputText value="lat:"></h:outputText>
		<h:inputText value="#{gmap.lat}" onchange="submit()"></h:inputText>

		<h:outputText value="locale:"></h:outputText>
		<h:inputText value="#{gmap.locale}" onchange="submit()"></h:inputText>

		<h:outputText value="Dragging:" />
		<h:selectOneRadio value="#{gmap.dragging}" onchange="submit();">
			<f:selectItem itemLabel="on" itemValue="true" />
			<f:selectItem itemLabel="off" itemValue="false" />
		</h:selectOneRadio>

		<h:outputText value="Continuous Zoom:" />
		<h:selectOneRadio value="#{gmap.continuousZoom}" onchange="submit();">
			<f:selectItem itemLabel="on" itemValue="true" />
			<f:selectItem itemLabel="off" itemValue="false" />
		</h:selectOneRadio>

		<h:outputText value="Double Click Zoom:" />
		<h:selectOneRadio value="#{gmap.doubleClickZoom}" onchange="submit();">
			<f:selectItem itemLabel="on" itemValue="true" />
			<f:selectItem itemLabel="off" itemValue="false" />
		</h:selectOneRadio>

		<h:outputText value="rendered:"></h:outputText>
		<h:selectBooleanCheckbox value="#{gmap.rendered}" onchange="submit();"></h:selectBooleanCheckbox>
	</h:panelGrid>
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getRendererType" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('gMapID').rendererType}" />
		</rich:column>
	</h:panelGrid>
</f:subview>