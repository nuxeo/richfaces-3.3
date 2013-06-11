/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package dfs;

import org.richfaces.component.html.HtmlDataFilterSlider;
import org.richfaces.event.DataFilterSliderEvent;

import util.componentInfo.ComponentInfo;


/**
 * @author $Autor$
 *
 */
public class DemoSliderBean {
	private boolean rendered;
    private DemoInventoryList demoInventoryList;
    private String action;
    private String actionListener;
    private boolean storeResults;
    private Integer startRange;
    private Integer endRange;
    private Integer increment;
    private String trailer;
    private Integer handleValue;
    private HtmlDataFilterSlider htmlDataFilterSlider = null;
    
//	" storeResults="true"
//		startRange="10000" endRange="60000" increment="10000"
//		manualInput="true" width="400px" 
//		trailer="true" handleValue="10000"
		
	public void addHtmlDataFilterSlider(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlDataFilterSlider);
	}
    
    public HtmlDataFilterSlider getHtmlDataFilterSlider(){
    	return htmlDataFilterSlider;
    }
    
    public void setHtmlDataFilterSlider(HtmlDataFilterSlider htmlDataFilterSlider){
    	this.htmlDataFilterSlider = htmlDataFilterSlider;
    }
    
    public DemoSliderBean() {
    	rendered = true;
    	action = "---";
    	actionListener = "---";
    }
    
    public void setDemoInventoryList(DemoInventoryList demoInventoryList) {
        this.demoInventoryList = demoInventoryList;
    }

    public void doSlide(DataFilterSliderEvent event) {

           Integer oldSliderVal = event.getOldSliderVal();
           Integer newSliderVal = event.getNewSliderVal();

           System.out.println("Old Slider Value = " + oldSliderVal.toString() + "  " + "New Slider Value = " + newSliderVal.toString());

    }

	public void doRendered()
	{
		if(rendered)
			rendered = false;
		else rendered = true;
	}

	public String getRenderedName() {
		if(rendered) return "Rendered on";
		else return "Rendered off";
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	
	public String act() {
		action = "action work!";
		return null;
	}

	public String actListener() {
		actionListener = "actionListener work!";
		return null;
	}

	public String getAction() {
		return action;
	}

	public String getActionListener() {
		return actionListener;
	}
}