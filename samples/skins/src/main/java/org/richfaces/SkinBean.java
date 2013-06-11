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

package org.richfaces;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectOne;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 09.02.2007
 * 
 */
public class SkinBean {
	
	private String[] skinsArray = new String[] {
		"blueSky",
		"classic",
		"deepMarine",
		"DEFAULT",
		"emeraldTown",
		"japanCherry",
		"ruby",
		"wine",
		"plain",
		"NULL"
	};

	private String defaultSkin = "blueSky";
	
	private String skin = defaultSkin;
	
	private UISelectOne createComponent() {
		UISelectOne selectOne = new HtmlSelectOneRadio();
		selectOne.setValue(skin);
		
		for (int i = 0; i < skinsArray.length; i++) {
			String skinName = skinsArray[i];
			
			UISelectItem item = new UISelectItem();
			item.setItemLabel(skinName);
			item.setItemValue(skinName);
			item.setId("skinSelectionFor_" + skinName);
			
			selectOne.getChildren().add(item);
		}

		return selectOne;
	}
	
	public String getSkin() {
		return skin;
	}
	
	public UIComponent getComponent() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map requestMap = facesContext.getExternalContext().getRequestMap();
		Object object = requestMap.get("SkinBean");
		if (object != null) {
			return (UISelectOne) object;
		}
		
		UISelectOne selectOne = createComponent();
		requestMap.put("SkinBean", selectOne);
		return selectOne;
	}

	public void setComponent(UIComponent component) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map requestMap = facesContext.getExternalContext().getRequestMap();
		requestMap.put("SkinBean", component);
	}
	
	public String change() {
		UISelectOne selectOne = (UISelectOne) getComponent();
		skin = (String) selectOne.getValue();
		return null;
	}
}
