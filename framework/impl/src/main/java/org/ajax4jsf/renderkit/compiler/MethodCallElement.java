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

package org.ajax4jsf.renderkit.compiler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.el.MethodNotFoundException;

import org.ajax4jsf.Messages;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;


/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:47 $
 *
 */
public  class MethodCallElement extends ElementBase {

	public static final String UTILS_PREFIX = "utils.";
	
	static final Log _log = LogFactory.getLog(MethodCallElement.class);

	private String _name = null;
	
	private List parameters = new ArrayList();
	
	private Invoker invoker = new Invoker();
	
	private MethodCacheState state = new MethodCacheState();
	
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.CompiledXML#encode(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encode(TemplateContext context ) throws IOException {
			getValue(context);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		// Text not contain breakpoints.
		encode(context);
	}
	
	
	public Object getValue(TemplateContext context) throws FacesException {
		// prepare method params. we attempt to call 3 signatures :
		// a) name(FacesContext,UIComponent [, param0...])
		// b) name(TempalateContext [,param0...])
		// c) name([param0...])
		state.init(parameters);
		Object[] values = state.computeParameterValues(context);
		
		InvokeData data = null;
		synchronized (state) {
			state.update(context, values, invoker);
			data = invoker.invokeMethod(context, state);
		}
		return invoker.invokeMethod(data);
		// perform childrens.
		// super.encode(renderer,context,component);
	}



	public void addParameter(MethodParameterElement parameter){
		parameters.add(parameter);
	}
	/**
	 * @return Returns the methodName.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param methodName The methodName to set.
	 */
	public void setName(String methodName) {
		if(methodName.startsWith(UTILS_PREFIX)){
			this._name = methodName.substring(UTILS_PREFIX.length());
			this.invoker = getRendererUtilsInvoker(_name);
		} else if(methodName.indexOf('.') >= 0) {
			this._name = methodName;
			this.invoker = getStaticInvoker(_name);
		} else {
			this._name = methodName;
			this.invoker = getRendererInvoker(_name);
		}
	}

	static Map staticInvokers = new HashMap();
	
	public StaticInvoker getStaticInvoker(String methodName) {
		StaticInvoker invoker = (StaticInvoker)staticInvokers.get(methodName);
		if(invoker == null) {
			invoker = new StaticInvoker(methodName);
			staticInvokers.put(methodName, invoker);
		}
		return invoker;		
	}

	static Map rendererInvokers = new HashMap();
	
	public RendererInvoker getRendererInvoker(String methodName) {
		RendererInvoker invoker = (RendererInvoker)rendererInvokers.get(methodName);
		if(invoker == null) {
			invoker = new RendererInvoker(false, methodName);
			rendererInvokers.put(methodName, invoker);
		}
		return invoker;
	}
	
	static Map utilsInvokers = new HashMap();

	public RendererInvoker getRendererUtilsInvoker(String methodName) {
		RendererInvoker invoker = (RendererInvoker)utilsInvokers.get(methodName);
		if(invoker == null) {
			invoker = new RendererInvoker(true, methodName);
			utilsInvokers.put(methodName, invoker);
		}
		return invoker;
	}
	
	public String getTag() {
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.CALL_TAG;
	}

	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#setParent(org.ajax4jsf.renderkit.compiler.PreparedTemplate)
	 */
	public void setParent(PreparedTemplate parent) throws SAXException {		
		super.setParent(parent);
		if (getName()==null) {
			throw new SAXException(Messages.getMessage(Messages.NO_NAME_ATTRIBUTE_ERROR, getTag()));
		}
	}

	
/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#getString(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public String getString(TemplateContext context) throws FacesException {
		Object result = getValue(context);
		if (null == result || result.toString().length()==0) {
				result = "";
		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#getAllowedClasses()
	 */
	protected Class[] getAllowedClasses() {
		// TODO Auto-generated method stub
		return new Class[]{
				MethodParameterElement.class,
				ResourceElement.class
		};
	}

}

class InvokeData {
	TemplateContext context;
	Method method;
	Object object;
	Object[] arguments;
	InvokeData(TemplateContext context, Method method, Object object, Object[] arguments) {
		this.context = context;
		this.method = method;
		this.object = object;
		this.arguments = (Object[]) arguments.clone();
	}
}

class Invoker {
	String methodName;

	InvokeData invokeMethod(TemplateContext context, MethodCacheState state) {
		throw new FacesException(Messages.getMessage(Messages.RENDERER_METHOD_NOT_SET_ERROR));
	}

	void handleInvocationTargetException(TemplateContext context, InvocationTargetException e) {}
	void handleIllegalAccessException(TemplateContext context, IllegalAccessException e) {}
	void handleMethodNotFoundException(TemplateContext context) throws MethodNotFoundException {}

	InvokeData invokeMethod(TemplateContext context, Map methods, Class cls, Object object, MethodCacheState state) {
		Method method = provideMethod(methods, cls, object, state);
		return new InvokeData(context, method, object, state.current.arguments);
	}
	
	Object invokeMethod(InvokeData data) {
		if(data.method != null) {
			try {
				return data.method.invoke(data.object, data.arguments);
			} catch (InvocationTargetException e) {
				handleInvocationTargetException(data.context, e);
			} catch (IllegalAccessException e) {
				handleIllegalAccessException(data.context, e);
			}
		}
		handleMethodNotFoundException(data.context);
		return null;
	}
	
	public Class getInvokedClass(TemplateContext context) {
		return null;
	}
	
	private Method provideMethod(Map methods, Class cls, Object object, MethodCacheState state) {
		if(state.method != null) return state.method;

		if(methods.size() > 0) {
			for (int i = 0; i < state.signatures.length; i++) {
				state.method = (Method)methods.get(getClassesKey(state.signatures[i].arguments));
				if(state.method != null) {
					state.current = state.signatures[i];
					return state.method;
				}
			}
		}
		
		if(cls == null && object != null) cls = object.getClass();
		Method[] ms = cls.getMethods();
		for (int m = 0; m < ms.length; m++) {
			if(!ms[m].getName().equals(methodName)) continue;
			if(object == null && !Modifier.isStatic(ms[m].getModifiers())) continue;
			Class[] cs = ms[m].getParameterTypes();
			Signature s = getMatchingArguments(cs, state.signatures);
			if(s == null) continue;
			state.current = s;
			state.method = ms[m];
			methods.put(getClassesKey(s.arguments), ms[m]);
			return state.method;
		}

		return null;
	}

	private String getClassesKey(Object[] args) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			String dk = args[i] == null ? "null" : args[i].getClass().getName();
			sb.append(";").append(dk);
		}
		return sb.toString();
	}
	
	private Signature getMatchingArguments(Class[] cs, Signature[] sgs) {
		for (int i = 0; i < sgs.length; i++) {
			if(isMatching(cs, sgs[i].arguments)) return sgs[i];
		}
		return null;
	}

	static boolean isMatching(Class[] cs, Object[] args) {
		if(cs.length != args.length) return false;
		for (int i = 0; i < cs.length; i++) {
			if(args[i] != null && !cs[i].isAssignableFrom(args[i].getClass())) return false;
		}
		return true;
	}
}

class RendererInvoker extends Invoker {
	boolean utils = false;
	Map renderers = new HashMap();
	
	public RendererInvoker(boolean utils, String methodName) {
		this.utils = utils;
		this.methodName = methodName;
	}

	public Class getInvokedClass(TemplateContext context) {
		Object object = getInvokedObject(context);
		return (object != null) ? object.getClass() : null;
	}
	
	private Object getInvokedObject(TemplateContext context) {
		if(utils) {
			return context.getRenderer().getUtils();
		} else {
			return context.getRenderer();
		}
	}
	
	InvokeData invokeMethod(TemplateContext context, MethodCacheState state) {
		Object object = getInvokedObject(context);
		Map methods = getMethods(object);
		return invokeMethod(context, methods, null, object, state);
	}
	
	private Map getMethods(Object object) {
		if(object == null) return null;
		Map methods = (Map)renderers.get(object);
		if(methods == null) {
			methods = new HashMap();
			renderers.put(object, methods);
		}
		return methods;
	}

	void handleInvocationTargetException(TemplateContext context, InvocationTargetException e) {
		String logMessage = (utils) 
			? Messages.getMessage(Messages.METHOD_CALL_ERROR_1, methodName, context.getComponent().getId())
			: Messages.getMessage(Messages.METHOD_CALL_ERROR_1a, methodName, context.getComponent().getId());
		String excMessage = (utils)
			? Messages.getMessage(Messages.METHOD_CALL_ERROR_2, new Object[]{methodName, context.getComponent().getId(), e.getCause().getMessage()})
			: Messages.getMessage(Messages.METHOD_CALL_ERROR_2a, new Object[]{methodName, context.getComponent().getId(), e.getCause().getMessage()});
		MethodCallElement._log.error(logMessage, e);
		throw new FacesException(excMessage, e);
	}
	void handleIllegalAccessException(TemplateContext context, IllegalAccessException e) {
		String logMessage = (utils) 
			? Messages.getMessage(Messages.getMessage(Messages.METHOD_CALL_ERROR_3, methodName, context.getComponent().getId()))
			: Messages.getMessage(Messages.getMessage(Messages.METHOD_CALL_ERROR_3a, methodName, context.getComponent().getId()));
		String excMessage = (utils)
			? Messages.getMessage(Messages.getMessage(Messages.METHOD_CALL_ERROR_4, new Object[]{methodName, context.getComponent().getId(), e.getCause().getMessage()}))
			: Messages.getMessage(Messages.getMessage(Messages.METHOD_CALL_ERROR_4a, new Object[]{methodName, context.getComponent().getId(), e.getCause().getMessage()}));
		MethodCallElement._log.error(logMessage, e);
		throw new FacesException(excMessage, e);
	}
	
	void handleMethodNotFoundException(TemplateContext context) throws MethodNotFoundException {
		String logMessage = (utils) 
			? Messages.getMessage(Messages.getMessage(Messages.METHOD_CALL_ERROR_5, methodName, context.getComponent().getId()))
			: Messages.getMessage(Messages.getMessage(Messages.METHOD_CALL_ERROR_5a, methodName, context.getComponent().getId()));
		String excMessage = (utils)
			? Messages.getMessage(Messages.getMessage(Messages.METHOD_CALL_ERROR_6, methodName))
			: Messages.getMessage(Messages.getMessage(Messages.METHOD_CALL_ERROR_6a, methodName));
			MethodCallElement._log.error(logMessage);
		throw new FacesException(excMessage);
	}
}

class StaticInvoker extends Invoker {
	String className;
	Class cls;
	Map methods = new HashMap();
	
	StaticInvoker(String methodName) {
		this.methodName = methodName;
		int i = methodName.lastIndexOf('.');
		className = methodName.substring(0, i);
		this.methodName = methodName.substring(i + 1);
		try {
			cls = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			//ignore, throw exception when invoking
		}
	}

	InvokeData invokeMethod(TemplateContext context, MethodCacheState state) {
		if(cls == null) throw new FacesException(className, new ClassNotFoundException(className));
		return invokeMethod(context, methods, cls, null, state);
	}

	void handleInvocationTargetException(TemplateContext context, InvocationTargetException e) {
		MethodCallElement._log.error(Messages.getMessage(Messages.METHOD_CALL_ERROR_1a, methodName, context.getComponent().getId()), e);
		throw new FacesException(Messages.getMessage(Messages.METHOD_CALL_ERROR_2a, new Object[]{methodName, context.getComponent().getId(), e.getCause().getMessage()}), e);
	}
	void handleIllegalAccessException(TemplateContext context, IllegalAccessException e) {
		MethodCallElement._log.error(Messages.getMessage(Messages.METHOD_CALL_ERROR_3a, methodName, context.getComponent().getId()), e);
		throw new FacesException(Messages.getMessage(Messages.METHOD_CALL_ERROR_4a, new Object[]{methodName, context.getComponent().getId(), e.getCause().getMessage()}), e);
	}		
	void handleMethodNotFoundException(TemplateContext context) throws MethodNotFoundException {
		MethodCallElement._log.error(Messages.getMessage(Messages.METHOD_CALL_ERROR_5a, methodName, context.getComponent().getId()));
		throw new MethodNotFoundException(Messages.getMessage(Messages.METHOD_CALL_ERROR_6a, methodName));
	}
}

class MethodCacheState {
	private MethodParameterElement[] elements;
	private Class[] parameterClasses;
	private Object[] parameterValues;
	private Class lastCallingClass = null;
	public Method method;
	public Signature current;
	public Signature[] signatures;
	
	public void init(List parameters) {
		if(elements != null) return;
		synchronized (this) {
			if(elements != null) return;
			int size = parameters.size();
			parameterClasses = new Class[size];
			parameterValues = new Object[size];
			signatures = new Signature[3];
			signatures[0] = new Signature2(size);
			signatures[1] = new Signature1(size);
			signatures[2] = new Signature0(size);		
			elements = (MethodParameterElement[])parameters.toArray(new MethodParameterElement[0]);
		}
	}
	
	public Object[] computeParameterValues(TemplateContext context) {
		Object[] ps = new Object[elements.length];
		for (int i = 0; i < elements.length; i++) {
			ps[i] = elements[i].valueGetter.getValueOrDefault(context);
		}
		return ps;
	}
	
	public void update(TemplateContext context, Object[] values, Invoker invoker) {
		boolean changed = false;
		for (int i = 0; i < elements.length; i++) {
			Object parametr = values[i];
			Class c = (parametr == null) ? null : parametr.getClass();
			if(c != parameterClasses[i]) {
				parameterClasses[i] = c;
				changed = true;
			}
			parameterValues[i] = parametr;			
		}
		if(method != null && !changed && lastCallingClass != invoker.getInvokedClass(context)) {
			lastCallingClass = invoker.getInvokedClass(context);
			changed = true;
		}
		if(changed || current == null) {
			for (int i = 0; i < signatures.length; i++) {
				signatures[i].update(context, parameterValues);
			}
			method = null;
			current = null;
		} else {
			current.update(context, parameterValues);
		}
	}	
	
}

abstract class Signature {
	Object[] arguments;	

	Signature() {}

	void update(TemplateContext context, Object[] parameters) {}
}

class Signature0 extends Signature {
	Signature0(int size) {
		arguments = new Object[size];
	}

	void update(TemplateContext context, Object[] parameters) {
		System.arraycopy(parameters, 0, arguments, 0, parameters.length);
	}
	
}

class Signature1 extends Signature {
	Signature1(int size) {
		arguments = new Object[size + 1];
	}
	void update(TemplateContext context, Object[] parameters) {
		arguments[0] = context;
		System.arraycopy(parameters, 0, arguments, 1, parameters.length);
	}
	
}

class Signature2 extends Signature {
	Signature2(int size) {
		arguments = new Object[size + 2];
	}
	void update(TemplateContext context, Object[] parameters) {
		arguments[0] = context.getFacesContext();
		arguments[1] = context.getComponent();
		System.arraycopy(parameters, 0, arguments, 2, parameters.length);
	}	
}
