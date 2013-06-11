package org.richfaces.renderkit;

import java.util.Set;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.ScriptUtils;
import org.richfaces.component.UIColorPicker;
import org.richfaces.component.util.MessageUtil;

public abstract class ColorPickerRendererBase  extends InputRendererBase {

	public void addPopupToAjaxRendered(FacesContext context, UIColorPicker component) {
		AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
		Set<String> ajaxRenderedAreas = ajaxContext.getAjaxRenderedAreas();
		String clientId = component.getClientId(context);
		if (ajaxContext.isAjaxRequest() && ajaxRenderedAreas.contains(clientId)) {
			ajaxRenderedAreas.add(clientId + "-colorPicker-popup");
			ajaxRenderedAreas.add(clientId + "-colorPicker-script");
		}
	}
	
	private static final String[] EVENT_ATTRIBUTES = {
		"onchange", "onbeforeshow", "onshow", "onhide", "onselect"
	};
	
	public String encodeEvents(FacesContext context, UIComponent component) {
		StringBuilder builder = new StringBuilder();
		for (String eventName: EVENT_ATTRIBUTES) {
			JSFunctionDefinition handler = getUtils().getAsEventHandler(context, component, eventName, null);
			if (handler != null) {
				builder.append(".bind(");

				builder.append("'");
				builder.append("colorPicker");
				builder.append(eventName.substring(2));
				builder.append("', ");
				builder.append(ScriptUtils.toScript(handler));
				builder.append(")");
			}
		}
		return builder.toString();
	}
	
	private static final Pattern[] COLOR_PATTERNS = {
		Pattern.compile("(?:rgb|hsb)(?:\\D+\\d+){3}"),
		Pattern.compile("^#[0-9A-Fa-f]{6}")
	};
	
	public void validateColorString(FacesContext context, UIComponent component, String value) {
		for (Pattern colorPattern : COLOR_PATTERNS) {
			if (colorPattern.matcher(value).find()) {
				return ;
			}
		}

		throw new IllegalArgumentException("Illegal color value: [" + value + "] for component " + 
			MessageUtil.getLabel(context, component));
	}
}