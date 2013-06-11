
function __addLoadEvent(func) {
Event.observe(window, "load", func ); 
}

function __initVirtualEarthdiv(mapVar, clid ) {
	window[mapVar] = new VEMap(clid);
}

function __applyVirtualEarthparam(mapVar,onloadfunc,lat,lng,zoom,mapStyle,dashboardSize)
 {
  var map = window[mapVar];
  map.SetDashboardSize(dashboardSize);
  map.onLoadMap = onloadfunc;
  map.LoadMap();
  map.SetCenterAndZoom(new VELatLong(lat, lng), zoom);
  map.SetMapStyle(mapStyle);
}