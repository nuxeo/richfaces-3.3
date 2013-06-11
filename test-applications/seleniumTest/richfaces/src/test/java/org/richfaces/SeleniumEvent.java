/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.richfaces;

import java.util.ArrayList;
import java.util.List;

public enum SeleniumEvent {
	
	ONCLICK("onclick"),
	ONDBLCLICK("ondblclick"),
	ONMOUSEOVER("onmouseover"),
	ONMOUSEMOVE("onmousemove"),
	ONMOUSEOUT("onmouseout"),
	ONMOUSEDOWN("onmousedown"),
	ONMOUSEUP("onmouseup"),
	ONDRAGANDDROP("ondraganddrop"),
	ONKEYDOWN("onkeydown"),
	ONKEYUP("onkeyup"),
	ONKEYPRESS("onkeypress"),
	ONFOCUS("onfocus"),
	ONBLUR("onblur"),
	ONCHANGE("onchange");
	
	
	private String name;
	
	@SuppressWarnings("serial")
	public static final List<SeleniumEvent> STANDARD_HTML_EVENTS = new ArrayList<SeleniumEvent>() {
		@Override
		public void clear() {

		}
	};
	static {
		STANDARD_HTML_EVENTS.add(ONCLICK);
		//STANDARD_HTML_EVENTS.add(ONDBLCLICK);
		STANDARD_HTML_EVENTS.add(ONMOUSEOVER);
		STANDARD_HTML_EVENTS.add(ONMOUSEMOVE);
		STANDARD_HTML_EVENTS.add(ONMOUSEOUT);
		STANDARD_HTML_EVENTS.add(ONMOUSEDOWN);
		STANDARD_HTML_EVENTS.add(ONMOUSEUP);
		STANDARD_HTML_EVENTS.add(ONKEYDOWN);
		STANDARD_HTML_EVENTS.add(ONKEYUP);
		STANDARD_HTML_EVENTS.add(ONKEYPRESS);
		//STANDARD_HTML_EVENTS.add(ONBLUR);
		//STANDARD_HTML_EVENTS.add(ONFOCUS);
		//STANDARD_HTML_EVENTS.add(ONCHANGE);
	}
	
	private SeleniumEvent(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
