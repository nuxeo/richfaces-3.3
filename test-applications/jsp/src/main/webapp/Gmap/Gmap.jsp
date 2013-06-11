<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="GmapSubviewID">
	<rich:gmap id="gMapID" 
		binding="#{gmap.htmlGmap}" 
		locale="#{gmap.locale}" 
		enableInfoWindow="#{gmap.enableInfoWindow}" 
		mapType="#{gmap.mapType}" lng="#{gmap.lng}" 
		lat="#{gmap.lat}"
		warningMessage="#{gmap.warningMessage}" 
		rendered="#{gmap.rendered}" 
		zoom="#{gmap.zoom}"
		enableContinuousZoom="#{gmap.continuousZoom}" 
		enableDoubleClickZoom="#{gmap.doubleClickZoom}" 
		enableDragging="#{gmap.dragging}"
		gmapVar="map" 
		oninit="#{event.oninit}" 
		showGLargeMapControl="#{gmap.showGLargeMapControl}" 
		showGMapTypeControl="#{gmap.showGMapTypeControl}"
		showGScaleControl="#{gmap.showGScaleControl}" 
		onclick="#{event.onclick}" ondblclick="#{event.ondblclick}" 
		onkeydown="#{event.onkeydown}" 
		onkeypress="#{event.onkeypress}" 
		onkeyup="#{event.onkeyup}" 
		onmousedown="#{event.onmousedown}" 
		onmousemove="#{event.onmousemove}" 
		onmouseout="#{event.onmouseout}" 
		onmouseover="#{event.onmouseover}" 
		onmouseup="#{event.onmouseup}"
		gmapKey="ABQIAAAAxU6W9QEhFLMNdc3ATIu-VxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRkrpOGzxH8_ud3inE9pG1845-FCA" 
		style="#{style.style}" 
		styleClass="#{style.styleClass}" />

	<h:panelGroup><br />
		<f:verbatim>
		Controls:<br />
			<a href="javascript:void(0);" onclick="map.hideControls();return false;">Remove</a>
			<a href="javascript:void(0);" onclick="map.showControls();return false;">Show</a>
			<br />
			<br />
		Type:<br />
			<a href="javascript:void(0);" onclick="map.setMapType(G_NORMAL_MAP);return false;">Normal</a>
			<a href="javascript:void(0);" onclick="map.setMapType(G_SATELLITE_MAP);return false;">Satellite</a>
			<a href="javascript:void(0);" onclick="map.setMapType(G_HYBRID_MAP);return false;">Hybrid</a>
			<br />
			<br />
		Zoom:<br />
			<a href="javascript:void(0);" onclick="map.zoomIn();return false;">In</a>
			<a href="javascript:void(0);" onclick="map.zoomOut();return false;">Out</a>
			<br />
			<br />
			<a href="javascript:void(0);" onclick="showExadel();return false;">Show Exadel Office</a>
			<br />
			<a href="javascript:void(0);" onclick="showExadelInMinsk();return false;">Show Exadel in Belarus</a>
		</f:verbatim>
	</h:panelGroup>

	<a4j:outputPanel>
		<script>
	   
      function createMarker(point,html) {
        var marker = new GMarker(point);
        GEvent.addListener(marker, "click", function() {
          marker.openInfoWindowHtml(html);
        });
        return marker;
      }

	   function showExadelInMinsk() {
	    var point = new  GLatLng(53.92316,27.510737, 53.92316,27.510737);
	    map.setCenter(point);
      	var marker = createMarker(point,'Go to the <a target="_blank" href="http://www.exadel.com">Exadle Web Site</a>');
		map.setZoom(16);
		map.addOverlay(marker);
	   }
	   
	   function showExadel() {
	    var point = new  GLatLng(37.9721046, -122.0424842834);
	    map.setCenter(point);
      	var marker = createMarker(point,'Go to the <a target="_blank" href="http://www.exadel.com">Exadle Web Site</a>');
		map.setZoom(16);
		map.addOverlay(marker);
	   }
	   
	    function setCenter(lag, lat, zoom) {
	     map.setCenter(new GLatLng(lag, lat), zoom);
	     var ulp = new GPoint(lag,lat);
      	 var ul = G_NORMAL_MAP.getProjection().fromPixelToLatLng(ulp,zoom);
	    }
	   </script>
	</a4j:outputPanel>

</f:subview>
