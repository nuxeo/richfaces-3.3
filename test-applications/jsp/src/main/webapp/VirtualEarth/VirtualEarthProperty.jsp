<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="virtualEarthPropertySubviewID">
	<h:panelGrid columns="2">
		<h:outputText value="lat(-97 < x < 97):"></h:outputText>
		<h:inputText value="#{virtualEarth.lat}" onchange="submit();"></h:inputText>

		<h:outputText value="lng(-180 < x < 180):"></h:outputText>
		<h:inputText value="#{virtualEarth.lng}" onchange="submit();"></h:inputText>

		<h:outputText value="dashboardSize"></h:outputText>
		<h:selectOneMenu value="#{virtualEarth.dashboardSize}"
			onchange="submit();">
			<f:selectItem itemLabel="Normal" itemValue="Normal" />
			<f:selectItem itemLabel="Small" itemValue="Small" />
			<f:selectItem itemLabel="Tiny" itemValue="Tiny" />
		</h:selectOneMenu>

		<h:outputText value="mapStyle"></h:outputText>
		<h:selectOneMenu value="#{virtualEarth.mapStyle}" onchange="submit();">
			<f:selectItem itemLabel="Birdseye" itemValue="Birdseye" />
			<f:selectItem itemLabel="Hybrid" itemValue="Hybrid" />
			<f:selectItem itemLabel="Aerial" itemValue="Aerial" />
			<f:selectItem itemLabel="Road" itemValue="Road" />
		</h:selectOneMenu>

		<h:outputText value="rendered:"></h:outputText>
		<h:selectBooleanCheckbox value="#{virtualEarth.rendered}"
			onchange="submit();"></h:selectBooleanCheckbox>
	</h:panelGrid>
	<h:panelGroup>
		<f:verbatim>
		Dashboard:<br />
			<a href="javascript:void(0);"
				onclick="map.HideDashboard();return false;">Remove</a>
			<a href="javascript:void(0);"
				onclick="map.ShowDashboard();return false;">Show</a>
			<br />
			<br />
		Type:<br />
			<a href="javascript:void(0);"
				onclick="map.SetMapStyle(VEMapStyle.Road);return false;">Road</a>
			<a href="javascript:void(0);"
				onclick="map.SetMapStyle(VEMapStyle.Aerial);return false;">Aerial</a>
			<a href="javascript:void(0);"
				onclick="map.SetMapStyle(VEMapStyle.Hybrid);return false;">Hybrid</a>
			<a href="javascript:void(0);"
				onclick="map.SetMapStyle(VEMapStyle.Birdseye);return false;">Birdseye</a>
			<br />
			<br />
		Zoom:<br />
			<a href="javascript:void(0);" onclick="map.ZoomIn();return false;">In</a>
			<a href="javascript:void(0);" onclick="map.ZoomOut();return false;">Out</a>
			<br />
			<br />
			<a href="javascript:void(0);" onclick="showExadel();return false;">Show
			Exadel Office</a>
			<br />
			<a href="javascript:void(0);"
				onclick="showExadelInMinsk();return false;">Show Exadel in
			Belarus</a>
		</f:verbatim>
	</h:panelGroup>
	<br />
	<h:commandButton value="add test" action="#{virtualEarth.addHtmlVirtualEarth}"></h:commandButton>
	<br />
	<%--
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getRendererType" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('vEarthID').rendererType}" />
		</rich:column>
	</h:panelGrid>
	--%>
</f:subview>