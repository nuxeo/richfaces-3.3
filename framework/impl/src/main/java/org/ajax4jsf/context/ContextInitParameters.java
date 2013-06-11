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
package org.ajax4jsf.context;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.ajax4jsf.application.AjaxStateManager;

/**
 * This class hold all methods for get application init parameters. Created for
 * single access point to all parameters - simplest for a documentation.
 * 
 * @author asmirnov
 * 
 */
public class ContextInitParameters {

	/**
	 * 
	 */
	private ContextInitParameters() {
		// this is a only static methods for a access to Web app Init
		// parameters. Do not Instantiate !
	}

	public static final String[] SERIALIZE_SERVER_STATE = new String[] {
		"org.ajax4jsf.SERIALIZE_SERVER_STATE", 

		/* detect MyFaces vs. RI */
		"com.sun.faces.serializeServerState",
		"org.apache.myfaces.SERIALIZE_STATE_IN_SESSION"
	};
	
	public static final String[] NUMBER_OF_VIEWS_IN_SESSION = {"com.sun.faces.numberOfViewsInSession"};
	public static final String[] NUMBER_OF_LOGICAL_VIEWS_IN_SESSION = {"com.sun.faces.numberOfLogicalViews"};

	/**
	 * This parameter define where {@link ViewExpiredException} should be handled.
	 * If is it equals "true" , framework should proparate exception to client-side. 
	 */
	public static final String HANDLE_VIEW_EXPIRED_ON_CLIENT="org.ajax4jsf.handleViewExpiredOnClient";
	
	public static final String[] DATATABLE_USES_VIEW_LOCALE = {"org.richfaces.datatableUsesViewLocale"};
	
	public static final boolean isSerializeServerState(FacesContext context) {
		return getBoolean(context, SERIALIZE_SERVER_STATE, false);
	} 
	
	/**
	 * Get number of views for store in session by {@link AjaxStateManager} 
	 * @param context - current faces context.
	 * @return
	 */
	public static int getNumbersOfViewsInSession(FacesContext context) {
		return getInteger(context, NUMBER_OF_VIEWS_IN_SESSION,
				AjaxStateManager.DEFAULT_NUMBER_OF_VIEWS);
	}

	/**
	 * Get number of logical views for store in session for every viewId by {@link AjaxStateManager} 
	 * @param context - current faces context.
	 * @return
	 */
	public static int getNumbersOfLogicalViews(FacesContext context) {
		return getInteger(context, NUMBER_OF_LOGICAL_VIEWS_IN_SESSION,
				AjaxStateManager.DEFAULT_NUMBER_OF_VIEWS);
	}

	/**
	 * Defines whether data table should use view root locale for sorting and filtering or the 
	 * default platform one
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isDatatableUsesViewLocale(FacesContext context) {
		return getBoolean(context, DATATABLE_USES_VIEW_LOCALE, false);
	}
	
	static int getInteger(FacesContext context, String[] paramNames,
			int defaulValue) {
		String initParameter = getInitParameter(context,paramNames);
		if (null == initParameter) {
			return defaulValue;
		} else {
			try {
				return Integer.parseInt(initParameter);

			} catch (NumberFormatException e) {
				throw new FacesException("Context parameter " + paramNames
						+ " must have integer value");
			}
		}
	}

	static String getString(FacesContext context, String[] paramNames,
			String defaulValue) {
		String initParameter = getInitParameter(context,paramNames);
		if (null == initParameter) {
			return defaulValue;
		} else {
			return initParameter;
		}
	}

	static boolean getBoolean(FacesContext context, String[] paramNames,
			boolean defaulValue) {
		String initParameter = getInitParameter(context,paramNames);
		if (null == initParameter) {
			return defaulValue;
		} else if("true".equalsIgnoreCase(initParameter) || "yes".equalsIgnoreCase(initParameter)) {
			return true;
		} else if("false".equalsIgnoreCase(initParameter) || "no".equalsIgnoreCase(initParameter)) {
			return false;
		} else {
			throw new FacesException("Illegal value ["+initParameter+"] for a init parameter +"+paramNames+", only logical values 'true' or 'false' is allowed");
		}
	}

	static String getInitParameter(FacesContext context,
			String[] paramNames) {
		ExternalContext externalContext = context.getExternalContext();
		String value = null;
		for (int i = 0; i < paramNames.length && null == value; i++) {
			value = externalContext.getInitParameter(paramNames[i]);
		}
		return value;
	}


}
