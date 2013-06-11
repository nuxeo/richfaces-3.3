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
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

import org.ajax4jsf.Messages;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.skin.Skin;

/**
 * Resolve Skin propertyes.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:59:41 $
 *
 */
public class SkinPropertyResolver extends PropertyResolver {
	
	private static final Log log = LogFactory.getLog(SkinPropertyResolver.class);
	private PropertyResolver parent = null;

	/**
	 * @param parent
	 */
	public SkinPropertyResolver(PropertyResolver parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#getType(java.lang.Object, int)
	 */
	public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		if (base instanceof Skin) {
			if(log.isDebugEnabled()){
				log.debug(Messages.getMessage(Messages.ACESSING_SKIN_PROPERTY_AS_ARRAY_ERROR));
			}
			return null;
		}
		return parent.getType(base, index);
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#getType(java.lang.Object, java.lang.Object)
	 */
	public Class getType(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		if (base instanceof Skin) {
			Skin skin = (Skin) base;
			if(property instanceof String){
				return skin.getParameter(FacesContext.getCurrentInstance(),(String) property).getClass();
			}
			if(log.isDebugEnabled()){
				log.debug(Messages.getMessage(Messages.ACESSING_SKIN_PROPERTY_ERROR));
			}
			return null;
		}
		return parent.getType(base, property);
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#getValue(java.lang.Object, int)
	 */
	public Object getValue(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		if (base instanceof Skin) {
			if(log.isDebugEnabled()){
				log.debug(Messages.getMessage(Messages.ACESSING_SKIN_PROPERTY_AS_ARRAY_ERROR));
			}
			return null;
		}
		return parent.getValue(base, index);
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#getValue(java.lang.Object, java.lang.Object)
	 */
	public Object getValue(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		if (base instanceof Skin) {
			Skin skin = (Skin) base;
			if(property instanceof String){
				return skin.getParameter(FacesContext.getCurrentInstance(),(String) property);
			}
			if(log.isDebugEnabled()){
				log.debug(Messages.getMessage(Messages.ACESSING_SKIN_PROPERTY_ERROR));
			}
			return null;
		}
		return parent.getValue(base, property);
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#isReadOnly(java.lang.Object, int)
	 */
	public boolean isReadOnly(Object base, int arg1) throws EvaluationException, PropertyNotFoundException {
		if (base instanceof Skin) {
			return true;
		}
		return parent.isReadOnly(base, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#isReadOnly(java.lang.Object, java.lang.Object)
	 */
	public boolean isReadOnly(Object base, Object arg1) throws EvaluationException, PropertyNotFoundException {
		if (base instanceof Skin) {
			return true;
		}
		return parent.isReadOnly(base, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#setValue(java.lang.Object, int, java.lang.Object)
	 */
	public void setValue(Object base, int index, Object value) throws EvaluationException, PropertyNotFoundException {
		if (base instanceof Skin) {
			throw new EvaluationException(Messages.getMessage(Messages.SKIN_PROPERTIES_READ_ONLY_ERROR));
		}
		parent.setValue(base, index, value);
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#setValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object base, Object property, Object value) throws EvaluationException, PropertyNotFoundException {
		if (base instanceof Skin) {
			throw new EvaluationException(Messages.getMessage(Messages.SKIN_PROPERTIES_READ_ONLY_ERROR));
		}
		parent.setValue(base, property, value);
	}


}
