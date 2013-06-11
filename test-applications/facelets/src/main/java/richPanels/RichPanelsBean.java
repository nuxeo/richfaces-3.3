package richPanels;

import java.util.ArrayList;

public class RichPanelsBean {
	private String src;
	private MapComponent map;
	
	public RichPanelsBean() {
		src = "Blank";
		map = new MapComponent();
		// map.add( value,  add(  pages_path/name_pages, array<boolean>(Property, Straightforward) );
		map.add("Blank", add("/pages/Blank/Blank", new boolean [] {true, true}));
		map.add("Calendar", add("/Calendar/Calendar", new boolean [] {true, true}));
		map.add("DataFilterSlider", add("/DataFilterSlider/DataFilterSlider", new boolean [] {true, false}));
		map.add("DataScroller", add("/DataScroller/DataScroller", new boolean [] {true, true}));
		map.add("DataTable", add("/DataTable/DataTable", new boolean [] {true, true}));
		map.add("DragAndDrop", add("/DragAndDrop/DragAndDrop", new boolean [] {false, false}));
		map.add("DropDownMenu", add("/DropDownMenu/DropDownMenu", new boolean [] {true, true}));
		map.add("Effect", add("/Effect/Effect", new boolean [] {false, false}));
		map.add("Gmap", add("/Gmap/Gmap", new boolean [] {true, false}));
		map.add("InputNumberSlider", add("/InputNumberSlider/InputNumberSlider", new boolean [] {true, true}));
		map.add("InputNumberSpinner", add("/InputNumberSpinner/InputNumberSpinner", new boolean [] {true, true}));
		map.add("Insert", add("/Insert/Insert", new boolean [] {true, false}));
		map.add("Message", add("/Message/Message", new boolean [] {true, true}));
		map.add("ModalPanel", add("/RichPanels/ModalPanel/ModalPanel", new boolean [] {false, false}));
		map.add("Paint2D", add("/Paint2D/Paint2D", new boolean [] {true, true}));
		map.add("Panel", add("/RichPanels/Panel/Panel", new boolean [] {false, false}));
		map.add("Panel2", add("/Panel/Panel2", new boolean [] {false, false}));
		map.add("PanelBar", add("/RichPanels/PanelBar/PanelBar", new boolean [] {false, false}));
		map.add("PanelMenu", add("/PanelMenu/PanelMenu", new boolean [] {true, true}));
		map.add("Separator", add("/Separator/Separator", new boolean [] {true, true}));
		map.add("SimpleTogglePanel", add("/RichPanels/SimpleTogglePanel/SimpleTogglePanel", new boolean [] {false, false}));
		map.add("Spacer", add("/Spacer/Spacer", new boolean [] {true, true}));
		map.add("SuggestionBox", add("/SuggestionBox/SuggestionBox", new boolean [] {true, true}));
		map.add("TabPanel", add("/RichPanels/TabPanel/TabPanel", new boolean [] {false, false}));
		map.add("TogglePanel", add("/RichPanels/TogglePanel/TogglePanel", new boolean [] {false, false}));
		map.add("ToolBar", add("/ToolBar/ToolBar", new boolean [] {true, false}));
		map.add("Tooltip", add("/Tooltip/Tooltip", new boolean [] {true, true}));
		map.add("Tree", add("/Tree/Tree", new boolean [] {true, false}));
		map.add("VirtualEarth", add("/VirtualEarth/VirtualEarth", new boolean [] {true, true}));
		map.add("ScrollableDataTable", add("/ScrollableDataTable/ScrollableDataTable", new boolean [] {true, false}));
		map.add("jQuery", add("/jQuery/jQuery", new boolean [] {false, false}));
		map.add("OrderingList", add("/OrderingList/OrderingList", new boolean [] {true, false}));
	}
	
	public String getSrc() {
		return src;
	}

	public String getPathComponent() {
		return map.get(src).get(0);
	}
	
	public String getPathProperty() {
		return map.get(src).get(1);
	}
	
	public String getPathStraightforward() {
		return map.get(src).get(2);
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	private ArrayList<String> add(String path, boolean [] arr){
		ArrayList<String> list = new ArrayList<String>();
		list.add(path + ".xhtml");
		if(arr[0]) list.add(path + "Property.xhtml"); else list.add("/pages/Blank/BlankProperty.xhtml");
		if(arr[1]) list.add(path + "Straightforward.xhtml"); else list.add("/pages/Blank/BlankStraightforward.xhtml");
		return list;
	}
}

