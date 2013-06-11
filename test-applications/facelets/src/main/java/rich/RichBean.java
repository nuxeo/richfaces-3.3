package rich;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.richfaces.VersionBean;

public class RichBean {
	private static final String EXT = ".xhtml";
	private String version = VersionBean.SCM_REVISION;
	private String src;
	private String srcContainer;
	private MapComponent map;
	private List<SelectItem> list;


	public RichBean() {
		list = new ArrayList<SelectItem>();
		src = "Blank";
		srcContainer = "Blank";
		map = new MapComponent();
		// map.add( value,  add(  pages_path/name_pages, array<boolean>(Property, Straightforward) );
		map.add("Blank", add("/pages/Blank/Blank", new boolean [] {false, true, true}));
		map.add("Calendar", add("/Calendar/Calendar", new boolean [] {false, true, true}));
		map.add("DataFilterSlider", add("/DataFilterSlider/DataFilterSlider", new boolean [] {false, true, false}));
		map.add("DataScroller", add("/DataScroller/DataScroller", new boolean [] {false, true, true}));
		map.add("DataTable", add("/DataTable/DataTable", new boolean [] {false, true, true}));
		map.add("DragAndDrop", add("/DragAndDrop/DragAndDrop", new boolean [] {false, false, false}));
		map.add("DropDownMenu", add("/DropDownMenu/DropDownMenu", new boolean [] {false, true, true}));
		map.add("Effect", add("/Effect/Effect", new boolean [] {false, false, false}));
		map.add("Gmap", add("/Gmap/Gmap", new boolean [] {false, true, false}));
		map.add("InputNumberSlider", add("/InputNumberSlider/InputNumberSlider", new boolean [] {false, true, true}));
		map.add("InputNumberSpinner", add("/InputNumberSpinner/InputNumberSpinner", new boolean [] {false, true, true}));
		map.add("Insert", add("/Insert/Insert", new boolean [] {false, true, false}));
		map.add("Message", add("/Message/Message", new boolean [] {false, true, true}));
		map.add("ModalPanel", add("/ModalPanel/ModalPanel", new boolean [] {false, true, true}));
		map.add("Paint2D", add("/Paint2D/Paint2D", new boolean [] {false, true, true}));
		map.add("Panel", add("/Panel/Panel", new boolean [] {false, true, true}));
		map.add("PanelBar", add("/PanelBar/PanelBar", new boolean [] {false, true, true}));
		map.add("PanelMenu", add("/PanelMenu/PanelMenu", new boolean [] {false, true, true}));
		map.add("Separator", add("/Separator/Separator", new boolean [] {false, true, true}));
		map.add("SimpleTogglePanel", add("/SimpleTogglePanel/SimpleTogglePanel", new boolean [] {false, true, true}));
		map.add("Spacer", add("/Spacer/Spacer", new boolean [] {false, true, true}));
		map.add("SuggestionBox", add("/SuggestionBox/SuggestionBox", new boolean [] {false, true, true}));
		map.add("TabPanel", add("/TabPanel/TabPanel", new boolean [] {false, true, true}));
		map.add("TogglePanel", add("/TogglePanel/TogglePanel", new boolean [] {false, true, true}));
		map.add("ToolBar", add("/ToolBar/ToolBar", new boolean [] {false, true, false}));
		map.add("Tooltip", add("/Tooltip/Tooltip", new boolean [] {false, true, true}));
		map.add("Tree", add("/Tree/Tree", new boolean [] {false, true, false}));
		map.add("VirtualEarth", add("/VirtualEarth/VirtualEarth", new boolean [] {false, true, false}));
		map.add("ScrollableDataTable", add("/ScrollableDataTable/ScrollableDataTable", new boolean [] {false, true, false}));
		map.add("jQuery", add("/jQuery/jQuery", new boolean [] {true, false, false}));
		map.add("OrderingList", add("/OrderingList/OrderingList", new boolean [] {false, true, true}));
		map.add("DataDefinitionList", add("/DataDefinitionList/DataDefinitionList", new boolean [] {false, true, false}));
		map.add("DataOrderedList", add("/DataOrderedList/DataOrderedList", new boolean [] {false, true, false}));
		map.add("ContextMenu", add("/ContextMenu/ContextMenu", new boolean [] {false, true, false}));
		map.add("ListShuttle", add("/ListShuttle/ListShuttle", new boolean [] {false, true, true}));
		map.add("ComponentControl", add("/ComponentControl/ComponentControl", new boolean [] {false, false, false}));
		map.add("Columns", add("/Columns/Columns", new boolean [] {false, true, false}));
		map.add("PickList", add("/PickList/PickList", new boolean [] {false, true, false}));
		map.add("Combobox", add("/Combobox/Combobox", new boolean [] {false, true, false}));
		map.add("ProgressBar", add("/ProgressBar/ProgressBar", new boolean [] {false, false, false}));
		map.add("SortingAndFiltering", add("/SortingAndFiltering/SortingAndFiltering", new boolean [] {false, false, false}));
		map.add("FileUpload", add("/FileUpload/FileUpload", new boolean [] {false, false, false}));
		map.add("InplaceSelect", add("/InplaceSelect/InplaceSelect", new boolean [] {false, true, false}));
		map.add("InplaceInput", add("/InplaceInput/InplaceInput", new boolean [] {false, true, false}));
		map.add("Skinning", add("/Skinning/Skinning", new boolean [] {false, false, false}));
		map.add("HotKey", add("/HotKey/HotKey", new boolean [] {false, false, false}));
		map.add("Validator", add("/Validator/Validator", new boolean [] {true, true, true}));
		map.add("DataGrid", add("/DataGrid/DataGrid", new boolean [] {false, true, false}));
		map.add("ExtendedDataTable", add("/ExtendedDataTable/ExtendedDataTable", new boolean [] {false, true, false}));
		map.add("Editor", add("/Editor/Editor", new boolean [] {false, true, false}));
		map.add("Queue", add("/Queue/Queue", new boolean [] {false, true, true}));
		map.add("TreeAll", add("/TreeAll/tTree", new boolean [] {true, true, true}));
		map.add("TreeSwing", add("/TreeSwing/TreeSwing", new boolean [] {false, true, false}));
		map.add("ColorPicker", add("/ColorPicker/ColorPicker", new boolean [] {false, true, false}));
		map.add("LayoutComponents", add("/LayoutComponents/LayoutComponents", new boolean [] {true, true, true}));
		Iterator<String> iterator = map.getSet().iterator();
		while(iterator.hasNext()){
			list.add(new SelectItem(iterator.next()));
		}
	}
	
	public String getSrc() {
		return src;
	}

	public String getPathComponent() {
		return map.get(src).get(0);
	}

	public String getDefaultPathComponent() {
		return map.get(src).get(1);
	}
	
	public String getPathProperty() {
		return map.get(src).get(2);
	}
	
	public String getPathStraightforward() {
		return map.get(src).get(3);
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	private ArrayList<String> add(String path, boolean [] arr){
		ArrayList<String> list = new ArrayList<String>();
		list.add(path + EXT);
		if(arr[0]) list.add(path + "Default" + EXT); else list.add("/pages/Blank/BlankDefault"  + EXT);
		if(arr[1]) list.add(path + "Property" + EXT); else list.add("/pages/Blank/BlankProperty"  + EXT);
		if(arr[2]) list.add(path + "Straightforward" + EXT); else list.add("/pages/Blank/BlankStraightforward" + EXT);
		return list;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public List<SelectItem> getList(){
		return list;
	}
	
	public String getSrcContainer() {
		return srcContainer;
	}

	public void setSrcContainer(String srcContainer) {
		this.srcContainer = srcContainer;
	}
	
	public String getPathComponentContainer() {
		return map.get(srcContainer).get(0);
	}
	
	public List<SelectItem> getListContainer() {
		Iterator<String> iterator = map.getSet().iterator();
		List<SelectItem> l = new ArrayList<SelectItem>();
		String str;
		while(iterator.hasNext()){
			str = iterator.next();
			if(!str.equals(src))
			l.add(new SelectItem(str));
		}
		return l;
	}
}
