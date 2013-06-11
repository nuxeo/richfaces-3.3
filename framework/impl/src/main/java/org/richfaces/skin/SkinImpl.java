/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
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

package org.richfaces.skin;

import java.util.Map;

import javax.faces.context.FacesContext;


/**
 * @author nick belaevski
 * @since 3.2
 */
public class SkinImpl extends AbstractChainableSkinImpl {

    private SkinFactoryImpl factoryImpl;

    SkinImpl(Map properties, SkinFactoryImpl factoryImpl) {
	super(properties);
	
	this.factoryImpl = factoryImpl;
    }

    protected Skin getBaseSkin(FacesContext context) {
	Object object = getLocalParameter(context, Skin.baseSkin);
	Skin skin = null;
	
	if (object != null) {
	    skin = factoryImpl.getSkinByName(context, object, false);
	}
	
	if (skin == null) {
	    skin = factoryImpl.getBaseSkin(context);
	}

	return skin;
    }
}
