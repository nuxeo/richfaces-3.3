<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="virtualEarthSubviewID">
	<h:panelGrid columns="2">

		<rich:virtualEarth id="vEarthID" lng="#{virtualEarth.lng}" onLoadMap="#{event.onLoadMap}" rendered="#{virtualEarth.rendered}" version="#{virtualEarth.version}" lat="#{virtualEarth.lat}" zoom="#{virtualEarth.zoom}"
			dashboardSize="#{virtualEarth.dashboardSize}" mapStyle="#{virtualEarth.mapStyle}" var="map" binding="#{virtualEarth.myVirtualEarth}"
			onclick="#{event.onclick}" ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"/>

	</h:panelGrid>

	<a4j:outputPanel>
		<script>
      function createMarker(point,html) {
        var marker = new GMarker(point);
        GEvent.addListener(marker, "click", function() {
          marker.openInfoWindowHtml(html);
        });
        return marker;
      }
	   function showExadel() {
	    var point = new VELatLong(37.9721046, -122.0424842834);
		map.SetCenterAndZoom(point, 16);
		map.SetMapStyle(VEMapStyle.Birdseye);
	   }
	    function showExadelInMinsk() {
	    var point = new VELatLong(53.92316,27.510737);
		map.SetCenterAndZoom(point, 16);
		map.SetMapStyle(VEMapStyle.Birdseye);
	   }
	   </script>
	</a4j:outputPanel>
</f:subview>
