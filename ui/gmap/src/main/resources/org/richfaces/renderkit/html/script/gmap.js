
function __addLoadEvent(func) {
Event.observe(window, "load", func );
}
function __addUnLoadEvent(func) {
Event.observe(window, "unload", func );
}

function __initGmapdiv(mapVar, clid,warningMessage ) {
var mapdiv = document.getElementById(clid);
if (GBrowserIsCompatible()) {
window[mapVar] = new GMap2(mapdiv);
mapdiv.map = window[mapVar];
} else {
  mapdiv.innerHTML=warningMessage;
}
}

function __applyGmapparam(gmapVar,lat,lng,zoom,mapType,enableDragging,enableInfoWindow,
enableDoubleClickZoom,enableContinuousZoom,isGMapType,isGScale,isGLarge)
 {
   if (GBrowserIsCompatible()) {

window[gmapVar].setCenter(new GLatLng(lat, lng), zoom, mapType);

if (enableDragging)
 window[gmapVar].enableDragging();
else
 window[gmapVar].disableDragging();

if (enableInfoWindow)
 window[gmapVar].enableInfoWindow();
else
 window[gmapVar].disableInfoWindow();

if (enableDoubleClickZoom)
 window[gmapVar].enableDoubleClickZoom();
else
 window[gmapVar].disableDoubleClickZoom();

if (enableContinuousZoom)
 window[gmapVar].enableContinuousZoom();
else
 window[gmapVar].disableContinuousZoom();

if (isGMapType)
 window[gmapVar].addControl(new GMapTypeControl());

if (isGScale)
window[gmapVar].addControl(new GScaleControl());

if (isGLarge)
window[gmapVar].addControl(new GLargeMapControl());
}

}