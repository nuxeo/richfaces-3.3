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

package org.ajax4jsf.builder.config;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created 20.03.2008
 * @author Nick Belaevski
 * @since 3.2
 */

public class FunctionBean extends ComponentBaseBean {

    private String method;
    
    private String signature;
    
    public String getMethod() {
	return method;
    }
    
    public void setMethod(String method) {
	this.method = method;
    }
    
    private static final Pattern SIGNATURE = Pattern.compile("\\s*((?:\\[\\s*\\]\\s*)*)(\\.\\.\\.)?\\s*$");
    
    private static final int getArrayDepth(Class<?> clazz) {
	if (clazz.isArray()) {
	    return getArrayDepth(clazz.getComponentType()) + 1;
	} else {
	    return 0;
	}
    }
    
    private static final int getArrayDepth(String signature) {
	Matcher matcher = SIGNATURE.matcher(signature);
	if (matcher.find()) {
	    int depth = 0;
	    
	    int groupCount = matcher.groupCount();
	    for (int i = 1; i <= groupCount; i++) {
		String string = matcher.group(i);
		if (string != null) {
		    String group = matcher.group(i).trim();
		    if ("...".equals(group)) {
			depth++;
		    } else {
			int l = group.length();
			for (int j = 0; j < l; j++) {
			    if (group.charAt(j) == '[') {
				depth++;
			    }
			}
		    }
		}
	    }
	    
	    return depth;
	} else {
	    return 0;
	}
    }
    
    private static final Class<?> getComponentType(Class<?> clazz) {
	if (clazz.isArray()) {
	    return getComponentType(clazz.getComponentType());
	} else {
	    return clazz;
	}
    }
    
    private static final String getComponentType(String signature) {
	return SIGNATURE.matcher(signature).replaceFirst("");
    }

    private void findMethod() {
	if (method == null || method.length() == 0) {
	    throw new IllegalArgumentException("Method " + getName() + " is null or empty!");
	}

	int i = method.indexOf('(');
	if (i == -1 || method.charAt(method.length() - 1) != ')') {
	    throw new IllegalArgumentException("Method " + method + ": brace(s) are missing!");
	}
	
	String fqMethodName = method.substring(0, i);
	String signature = method.substring(i + 1, method.length() - 1);
	
	int j = fqMethodName.lastIndexOf('.');
	int k = fqMethodName.lastIndexOf('#');
	
	int sepIdx = Math.max(j, k);
	if (sepIdx == -1) {
	    throw new IllegalArgumentException("Method: " + method + ": doesn't have '.' or '#' method name separator char!");
	}
	
	String classname = fqMethodName.substring(0, sepIdx);
	Class<?> clazz;
	
	try {
	    clazz = getLoader().loadClass(classname);
	} catch (ClassNotFoundException e) {
	    throw new IllegalStateException(e.getLocalizedMessage(), e);
	}

	setClassname(classname);

	String methodName = fqMethodName.substring(sepIdx + 1);

	String[] parameters = signature.split("\\s*,\\s*");
	
	Method[] methods = clazz.getMethods();
	for (Method method : methods) {
		if (!methodName.equals(method.getName())) {
			continue;
		}
		
		if ((method.getModifiers() & Modifier.STATIC) == 0) {
			continue;
		}
		
		Class<?>[] parameterTypes = method.getParameterTypes();

	    if (parameters.length == parameterTypes.length) {
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append('(');
		boolean matches = true;

		for (int l = 0; l < parameterTypes.length; l++) {
		    String parameter = parameters[l];
		    int depth = getArrayDepth(parameter);
		    String componentType = getComponentType(parameter);
		    
		    Class<?> type = getComponentType(parameterTypes[l]);
		    int arrayDepth = getArrayDepth(parameterTypes[l]);
		    
		    if (depth != arrayDepth || (!componentType.equals(type.getSimpleName()) 
			    && !componentType.equals(type.getName()))) {
			
			matches = false;
			break;
		    }
		
		    signatureBuilder.append(parameterTypes[l].getCanonicalName());
		    if (l != parameterTypes.length - 1) {
			signatureBuilder.append(", ");
		    } else {
			signatureBuilder.append(')');
		    }
		}
		
		if (matches) {
		    this.signature = method.getReturnType().getCanonicalName() + " " + methodName + signatureBuilder.toString();
		    
		    break;
		}
	    }
	}

	if (this.signature == null) {
	    throw new IllegalStateException("Method: " + method + " not found!");
	}
    }

    public String getMethodClassname() {
	if (getClassname() == null) {
	    findMethod();
	}
	
	return getClassname();
    }

    public String getMethodSignature() {
	if (signature == null) {
	    findMethod();
	}
	
	return signature;
    }

}
