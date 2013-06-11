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

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.richfaces.VersionBean;
import org.richfaces.skin.SkinFactory;

/**
 * Resolve current skin as EL Variable. e.g. #{chameleonSkin['color'] } #{chameleonSkin.color}
 * must be evaluated as Skin.getProperty(context,"color");
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:59:41 $
 *
 */
public class SkinVariableResolver extends VariableResolver {


	public static final String SKIN_VARIABLE_NAME = "vcpSkin";

	public static final String CHAMELEON_VARIABLE_NAME = "vcp";

	private VariableResolver parent = null;

	public SkinVariableResolver(VariableResolver parent){
		this.parent = parent;
	}
	/* (non-Javadoc)
	 * @see javax.faces.el.VariableResolver#resolveVariable(javax.faces.context.FacesContext, java.lang.String)
	 */
	public Object resolveVariable(FacesContext context, String name)
			throws EvaluationException {
		
		// TODO: Why do we need this?
		if(SKIN_VARIABLE_NAME.equals(name)){
			return SkinFactory.getInstance().getSkin(context);
		} else if(CHAMELEON_VARIABLE_NAME.equals(name)){
			return new VersionBean();
		}
		if (parent!=null) {
			Object ret = null;
			ret = parent.resolveVariable(context,name);
			return ret;
		} else {
			return null;
		}
	}

}
