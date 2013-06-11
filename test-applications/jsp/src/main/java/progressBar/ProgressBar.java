package progressBar;

import javax.faces.event.ActionEvent;

import org.richfaces.component.html.HtmlProgressBar;

import util.componentInfo.ComponentInfo;
import util.data.Data;

public class ProgressBar {
	private boolean ajaxSingle;
	private double value;
	private boolean enabled;
	private String mode;
	private String interval;
	private String maxValue;
	private String minValue;
	private String label;
	private boolean dualColoredLabel;
	private boolean rendered;
	private String listnerInfo;
	private boolean ignoreDupResponses;
	private boolean permanent;
	private boolean immediate;
	private HtmlProgressBar htmlProgressBar = null;
	
	public HtmlProgressBar getHtmlProgressBar() {
		return htmlProgressBar;
	}

	public void setHtmlProgressBar(HtmlProgressBar htmlProgressBar) {
		this.htmlProgressBar = htmlProgressBar;
	}

	public void addHtmlProgressBar(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlProgressBar);
	}
	
	public boolean isIgnoreDupResponses() {
		return ignoreDupResponses;
	}

	public void setIgnoreDupResponses(boolean ignoreDupResponses) {
		this.ignoreDupResponses = ignoreDupResponses;
	}

	public String getListnerInfo() {
		return listnerInfo;
	}

	public void setListnerInfo(String listnerInfo) {
		this.listnerInfo = listnerInfo;
	}

	public String getLoadInfo() {
		return Data.cityAfrica[Math.abs((int)value) % 62];
	}

	public ProgressBar() {
		immediate = false;
		label = "label";
		ajaxSingle = false;
		value = 0L;
		enabled = false;
		mode = "ajax";
		interval = "500";
		maxValue = "100";
		minValue = "0";
		dualColoredLabel = false;
		rendered = true;
		ignoreDupResponses = false;
		permanent = false;
 	}
	
	public String action() {
		System.out.println("action");
		return null;
	}
	
	public void actionListener(ActionEvent actionEvent){
		System.out.println("actionEvent");
	}

	public boolean isAjaxSingle() {
		return ajaxSingle;
	}

	public void setAjaxSingle(boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public double getValue() {
		// without a vodka bottle you will not understand :)
		return enabled ? (permanent ? new Integer(maxValue) : value++) : value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isDualColoredLabel() {
		return dualColoredLabel;
	}

	public void setDualColoredLabel(boolean dualColoredLabel) {
		this.dualColoredLabel = dualColoredLabel;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}
}
