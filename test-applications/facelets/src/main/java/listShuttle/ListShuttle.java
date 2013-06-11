package listShuttle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlListShuttle;

import util.componentInfo.ComponentInfo;
import util.data.Data;

public class ListShuttle {
	private int first;
	private int lenght;
	private String[] statusIcon = { "/pics/error.gif", "/pics/fatal.gif",
			"/pics/info.gif", "/pics/passed.gif", "/pics/warn.gif" };
	private boolean fastMoveControlsVisible;
	private boolean fastOrderControlsVisible;
	private boolean moveControlsVisible;
	private boolean orderControlsVisible;
	private boolean showButtonLabels;
	private boolean showSelect;
	private boolean showAllSourceData;
	private boolean showAllTargetData;
	private boolean switchByClick;
	private boolean sourceRequired;
	private boolean targetRequired;
	private ArrayList<Data> sourceValue;
	private ArrayList<Data> targetValue;
	private ArrayList<String> info;
	private Collection sourceSelection;
	private Collection targetSelection;
	private String bottomControlLabel;
	private String copyAllControlLabel;
	private String copyControlLabel;
	private String downControlLabel;
	private String removeAllControlLabel;
	private String removeControlLabel;
	private String topControlLabel;
	private String upControlLabel;
	private String targetListWidth;
	private String sourceListWidth;
	private String listsHeight;
	private String sourceCaptionLabel;
	private String targetCaptionLabel;
	private HtmlListShuttle htmlListShuttle = null;

	public HtmlListShuttle getHtmlListShuttle() {
		return htmlListShuttle;
	}

	public void setHtmlListShuttle(HtmlListShuttle htmlListShuttle) {
		this.htmlListShuttle = htmlListShuttle;
	}

	public String add() {
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlListShuttle);
		return null;
	}

	public ListShuttle() {
		this.first = 1;
		this.lenght = 10;
		this.showSelect = true;
		this.showAllSourceData = true;
		this.showAllTargetData = true;
		this.fastMoveControlsVisible = true;
		this.fastOrderControlsVisible = true;
		this.moveControlsVisible = true;
		this.orderControlsVisible = true;
		this.showButtonLabels = true;
		this.sourceRequired = false;
		this.targetRequired = false;
		this.bottomControlLabel = "bottom";
		this.copyAllControlLabel = "copy all";
		this.copyControlLabel = "copy";
		this.downControlLabel = "down";
		this.removeAllControlLabel = "remove all";
		this.removeControlLabel = "remove";
		this.topControlLabel = "top";
		this.upControlLabel = "up";
		this.info = new ArrayList<String>();
		this.switchByClick = false;
		this.targetListWidth = "450";
		this.sourceListWidth = "450";
		this.listsHeight = "300";
		this.sourceCaptionLabel = "sourceCaptionLabel";
		this.targetCaptionLabel = "targetCaptionLabel";
		this.targetValue = new ArrayList<Data>();
		this.sourceValue = new ArrayList<Data>();
		addNewItem();
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public boolean isFastMoveControlsVisible() {
		return fastMoveControlsVisible;
	}

	public void setFastMoveControlsVisible(boolean fastMoveControlsVisible) {
		this.fastMoveControlsVisible = fastMoveControlsVisible;
	}

	public boolean isFastOrderControlsVisible() {
		return fastOrderControlsVisible;
	}

	public void setFastOrderControlsVisible(boolean fastOrderControlsVisible) {
		this.fastOrderControlsVisible = fastOrderControlsVisible;
	}

	public boolean isMoveControlsVisible() {
		return moveControlsVisible;
	}

	public void setMoveControlsVisible(boolean moveControlsVisible) {
		this.moveControlsVisible = moveControlsVisible;
	}

	public boolean isOrderControlsVisible() {
		return orderControlsVisible;
	}

	public void setOrderControlsVisible(boolean orderControlsVisible) {
		this.orderControlsVisible = orderControlsVisible;
	}

	public boolean isShowButtonLabels() {
		return showButtonLabels;
	}

	public void setShowButtonLabels(boolean showButtonLabels) {
		this.showButtonLabels = showButtonLabels;
	}

	public ArrayList<Data> getSourceValue() {
		return sourceValue;
	}

	public void setSourceValue(ArrayList<Data> sourceValue) {
		this.sourceValue = sourceValue;
	}

	public ArrayList getTargetValue() {
		return targetValue;
	}

	public String getBottomControlLabel() {
		return bottomControlLabel;
	}

	public void setBottomControlLabel(String bottomControlLabel) {
		this.bottomControlLabel = bottomControlLabel;
	}

	public String getCopyAllControlLabel() {
		return copyAllControlLabel;
	}

	public void setCopyAllControlLabel(String copyAllControlLabel) {
		this.copyAllControlLabel = copyAllControlLabel;
	}

	public String getCopyControlLabel() {
		return copyControlLabel;
	}

	public void setCopyControlLabel(String copyControlLabel) {
		this.copyControlLabel = copyControlLabel;
	}

	public String getDownControlLabel() {
		return downControlLabel;
	}

	public void setDownControlLabel(String downControlLabel) {
		this.downControlLabel = downControlLabel;
	}

	public String getRemoveAllControlLabel() {
		return removeAllControlLabel;
	}

	public void setRemoveAllControlLabel(String removeAllControlLabel) {
		this.removeAllControlLabel = removeAllControlLabel;
	}

	public String getRemoveControlLabel() {
		return removeControlLabel;
	}

	public void setRemoveControlLabel(String removeControlLabel) {
		this.removeControlLabel = removeControlLabel;
	}

	public String getTopControlLabel() {
		return topControlLabel;
	}

	public void setTopControlLabel(String topControlLabel) {
		this.topControlLabel = topControlLabel;
	}

	public String getUpControlLabel() {
		return upControlLabel;
	}

	public void setUpControlLabel(String upControlLabel) {
		this.upControlLabel = upControlLabel;
	}

	public void addNewItem() {
		if (lenght < 0)
			lenght = 0;
		if (sourceValue.size() > lenght)
			for (int i = lenght; i < sourceValue.size();)
				sourceValue.remove(i);
		else
			for (int i = sourceValue.size() + 1; i <= lenght; i++)
				sourceValue.add(new Data(i, "Button " + i, "Link " + i,
						"Select" + (i % 5), statusIcon[i % 5], "Input" + i));
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public ArrayList<String> getInfo() {
		info.clear();
		addSelection(getSourceSelection(), "1. sourceSelection");
		addSelection(getTargetSelection(), "2. targetSelection");
		return info;
	}

	public void setInfo(ArrayList<String> info) {
		this.info = info;
	}

	public void setTargetValue(ArrayList<Data> targetValue) {
		this.targetValue = targetValue;
	}

	public void cbAction() {
		info.clear();
		info.add("commandButton submit();");
	}

	public void clAction() {
		info.clear();
		info.add("commandLink submit();");
	}

	public boolean isShowSelect() {
		return showSelect;
	}

	public void setShowSelect(boolean showSelect) {
		this.showSelect = showSelect;
	}

	public boolean isShowAllSourceData() {
		return showAllSourceData;
	}

	public void setShowAllSourceData(boolean showAllSourceData) {
		this.showAllSourceData = showAllSourceData;
	}

	public boolean isShowAllTargetData() {
		return showAllTargetData;
	}

	public void setShowAllTargetData(boolean showAllTargetData) {
		this.showAllTargetData = showAllTargetData;
	}

	public Collection getSourceSelection() {
		return sourceSelection;
	}

	public void setSourceSelection(Collection sourceSelection) {
		this.sourceSelection = sourceSelection;
	}

	public Collection getTargetSelection() {
		return targetSelection;
	}

	public void setTargetSelection(Collection targetSelection) {
		this.targetSelection = targetSelection;
	}

	public boolean isSwitchByClick() {
		return switchByClick;
	}

	public void setSwitchByClick(boolean switchByClick) {
		this.switchByClick = switchByClick;
	}

	public String getTargetListWidth() {
		return targetListWidth;
	}

	public void setTargetListWidth(String targetListWidth) {
		this.targetListWidth = targetListWidth;
	}

	public String getSourceListWidth() {
		return sourceListWidth;
	}

	public void setSourceListWidth(String sourceListWidth) {
		this.sourceListWidth = sourceListWidth;
	}

	public String getListsHeight() {
		return listsHeight;
	}

	public void setListsHeight(String listsHeight) {
		this.listsHeight = listsHeight;
	}

	public String getSourceCaptionLabel() {
		return sourceCaptionLabel;
	}

	public void setSourceCaptionLabel(String sourceCaptionLabel) {
		this.sourceCaptionLabel = sourceCaptionLabel;
	}

	public String getTargetCaptionLabel() {
		return targetCaptionLabel;
	}

	public void setTargetCaptionLabel(String targetCaptionLabel) {
		this.targetCaptionLabel = targetCaptionLabel;
	}
		
	public boolean isSourceRequired() {
		return sourceRequired;
	}

	public void setSourceRequired(boolean sourceRequired) {
		this.sourceRequired = sourceRequired;
	}

	public boolean isTargetRequired() {
		return targetRequired;
	}

	public void setTargetRequired(boolean targetRequired) {
		this.targetRequired = targetRequired;
	}

	private void addSelection(Collection<Data> selection, String description) {
		if (selection == null)
			return;
		Iterator<Data> inter = selection.iterator();
		Data data = new Data();
		while (inter.hasNext()) {
			data = inter.next();
			info.add(description + ": " + data.getInt0() + "; "
					+ data.getStr0() + "; " + data.getStr0() + "submit(); "
					+ data.getStr1() + "; " + data.getStr1() + "submit(); "
					+ data.getStr2() + "; " + data.getStr3());
		}
	}
}
