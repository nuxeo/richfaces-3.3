package org.richfaces.helloworld.domain.progressBar;

import javax.faces.event.ActionEvent;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.richfaces.helloworld.domain.util.data.Data;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlProgressBar;

@Name("progressBar")
@Scope(ScopeType.SESSION)
public class ProgressBar {
	private boolean ajaxSingle;
	private Integer value;
	private boolean enabled;
	private String mode;
	private String interval;
	private String maxValue;
	private String minValue;
	private boolean dualColoredLabel;
	private boolean rendered;
	private String listnerInfo;
	private boolean ignoreDupResponses;
	private boolean permanent;
	private HtmlProgressBar htmlProgressBar = null;
	
	public HtmlProgressBar getHtmlProgressBar() {
		return htmlProgressBar;
	}

	public void setHtmlProgressBar(HtmlProgressBar htmlProgressBar) {
		this.htmlProgressBar = htmlProgressBar;
	}

	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlProgressBar);
		return null;
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
		ajaxSingle = false;
		value = 0;
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

	public Integer getValue() {
		// without a vodka bottle you will not understand :)
		return enabled ? (permanent ? new Integer(maxValue) : value++) : value;
	}

	public void setValue(Integer value) {
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
}
