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

package org.ajax4jsf.renderkit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.el.MethodNotFoundException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.Messages;
import org.apache.commons.beanutils.MethodUtils;

/**
 * 	Utils for working with tempates
 *  @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 *  @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:58:52 $
 */
public class ComponentUtils {
	private final static String UTILS_PREFIX = "util.";
	/**
	 * 
	 * @param className
	 * @param functionName
	 * @param parameters
	 * @return
	 */
	public static Object callFunction( FacesContext context, UIComponent component, RendererBase renderer, String functionName, Object[] parameters ) {

		Object returnObject = null;
		
		int sizeParameters = 0;
		int sizeArrayParameters = 2;
		if ( parameters != null ) {
			sizeParameters = parameters.length;
			sizeArrayParameters = 3;
		}
		
		Object[][] arrayParameters = new Object[sizeArrayParameters][];
		arrayParameters[0] = new Object[sizeParameters+2];
		arrayParameters[1] = new Object[sizeParameters+1];
		
		if ( sizeParameters != 0 ) {
			arrayParameters[2] = new Object[sizeParameters];
		}
		
		arrayParameters[0][0] = context;
		arrayParameters[0][1] = component;
		arrayParameters[1][0] = component;
		
		if ( parameters != null ) {
			for (int iParameter=0;iParameter<parameters.length;iParameter++) {
				arrayParameters[0][iParameter+2] = parameters;
				arrayParameters[1][iParameter+1] = parameters;
				arrayParameters[2][iParameter] = parameters;
			}
		}
		
		String methodName;
		Object object;
		if(functionName.startsWith(UTILS_PREFIX)){
			methodName = functionName.substring(UTILS_PREFIX.length());			
			object = renderer.getUtils();
		} else {
			object = renderer;
			methodName = functionName;			
		}


		returnObject = invokeMethod(object, methodName, arrayParameters );
	
		return returnObject;
	}

	/**
	 * Invoke a named method whose parameter type matches the object type.
	 * @param objects - invoke method on this object
	 * @param methodName - get method with this name
	 * @param arrayParameters - use these arguments - treat null as empty array
	 * @return
	 */
	private static Object invokeMethod(Object object, String methodName, Object[][] arrayParameters) {
		
		try {
			for (int iParameter = 0; iParameter < arrayParameters.length; iParameter++) {
				try {
					return MethodUtils.invokeMethod(object, methodName, arrayParameters[iParameter]);
				} catch (NoSuchMethodException e) {
					continue;
				}
			}
		} catch (InvocationTargetException e) {
			throw new FacesException(Messages.getMessage(Messages.METHOD_CALL_ERROR_2b, methodName, e.getCause().getMessage()), e);
		} catch (IllegalAccessException e) {
			throw new FacesException(Messages.getMessage(Messages.METHOD_CALL_ERROR_4b, methodName, e.getMessage()), e);
		}
		throw new MethodNotFoundException(Messages.getMessage(Messages.METHOD_CALL_ERROR_6b, methodName, object));
	}
	
	/**
	 * Write html-attribute
	 * @param writer
	 * @param attribute
	 * @param value
	 * @throws IOException
	 */
	public static void writeAttribute(ResponseWriter writer, String attribute, Object value ) throws IOException {
		if ( (value != null) && (value.toString().length()!=0) ) {
			writer.writeAttribute(attribute, value.toString(), attribute );
		}
	}
}	
