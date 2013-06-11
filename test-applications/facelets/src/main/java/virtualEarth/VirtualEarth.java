package virtualEarth;

import org.richfaces.component.html.HtmlVirtualEarth;

import util.componentInfo.ComponentInfo;

public class VirtualEarth {
	private String zoom;
	private String version;
	private String lat;
	private String lng;
	private boolean rendered;
	private String dashboardSize;
	private String mapStyle;
	private HtmlVirtualEarth htmlVirtualEarth = null;

	public HtmlVirtualEarth getHtmlVirtualEarth() {
		return htmlVirtualEarth;
	}

	public void setHtmlVirtualEarth(HtmlVirtualEarth htmlVirtualEarth) {
		this.htmlVirtualEarth = htmlVirtualEarth;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlVirtualEarth);
		return null;
	}

	public VirtualEarth() {
		version = "6";
		zoom = "17";
		version = "6";
		lat = "37.37";
		lng = "44.44";
		dashboardSize = "Normal";
		mapStyle = "Hybrid";
		rendered = true;
	}
	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		double d = Double.parseDouble(lat);
		if(-97 < d && d < 97)
			this.lat = lat;
		else this.lat = "0";
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		double d = Double.parseDouble(lng);
		if(-180 < d && d < 180)
			this.lng = lng;
		else this.lng = "0";
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getDashboardSize() {
		return dashboardSize;
	}

	public void setDashboardSize(String dashboardSize) {
		this.dashboardSize = dashboardSize;
	}

	public String getMapStyle() {
		return mapStyle;
	}

	public void setMapStyle(String mapStyle) {
		this.mapStyle = mapStyle;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getZoom() {
		return zoom;
	}

	public void setZoom(String zoom) {
		this.zoom = zoom;
	}

	public String act() {
		zoom = "17";
		System.out.println("zoom=" + zoom);
		return null;
	}
}
