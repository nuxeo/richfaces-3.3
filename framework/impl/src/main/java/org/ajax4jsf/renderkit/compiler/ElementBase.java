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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.ajax4jsf.Messages;
import org.ajax4jsf.renderkit.RendererBase;
import org.apache.commons.beanutils.MethodUtils;
import org.richfaces.skin.SkinFactory;
import org.xml.sax.SAXException;


/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:44 $
 *
 */
public abstract class ElementBase implements PreparedTemplate {

	private List childrens = new ArrayList();
	private Map breaks = new HashMap();
	
	protected ValueGetter valueGetter = new ValueGetter();
	private Object _value = null;
	private Object _default = null;
	private Object _context = null;
	private String _property = null;
	private String _skin ;
	private String _baseSkin ;
//	private String _skinConfiguration ;
	private String _call = null;
	private PreparedTemplate parent;
	private ValueBinding _valueBinding;

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.CompiledXML#encode(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encode(RendererBase renderer, FacesContext context,
			UIComponent component) throws IOException {
		encode(new TemplateContext(renderer,context,component));
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		if (breaks.containsKey(breakPoint)) {
			// start after last breakpoint.
			int start = ((Integer)breaks.get(breakPoint)).intValue();
			Iterator iter = getChildren().listIterator(start);
			((PreparedTemplate) iter.next()).encode(context,breakPoint);
			// perform childrens - other not contain this breakpoint !
			while ( iter.hasNext()) {
				PreparedTemplate element = (PreparedTemplate) iter.next();
				element.encode(context);
			}
			encodeEnd(context);
		} else {
			throw new FacesException(Messages.getMessage(Messages.BREAKPOINT_NOT_REGISTERED_ERROR, breakPoint));
		}
		
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#encode(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public void encode(TemplateContext context) throws IOException {
		// perform childrens.
		encodeBegin(context);
		for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
			PreparedTemplate element = (PreparedTemplate) iter.next();
			element.encode(context);
		}
		encodeEnd(context);
	}

	protected void encodeBegin(TemplateContext context) throws IOException {
		
	}
	
	protected void encodeEnd(TemplateContext context) throws IOException {
		
	}
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.CompiledXML#getChildren()
	 */
	public List getChildren() {
		// TODO Auto-generated method stub
		return childrens;
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.CompiledXML#addChild(org.ajax4jsf.renderkit.compiler.CompiledXML)
	 */
	public void addChild(PreparedTemplate child) throws SAXException {
		Class[] allowedClasses = getAllowedClasses();
		for (int i = 0; i < allowedClasses.length; i++) {
			if(allowedClasses[i].isInstance(child)){
				child.setParent(this);
				getChildren().add(child);
				return;
			}
		}
		// element don't allowed as child for this.
		throw new SAXException(Messages.getMessage(Messages.CHILD_NOT_ALLOWED_ERROR, child.getTag(), getTag()));
	}

	/**
	 * 
	 * @return array of classes, allowed as childs for this element.
	 */
	protected Class[] getAllowedClasses(){
		return new Class[]{PreparedTemplate.class};
	}
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#setParent(org.ajax4jsf.renderkit.compiler.PreparedTemplate)
	 */
	public void setParent(PreparedTemplate parent) throws SAXException {
		this.parent = parent;
		// Translate all registered breakpoints to parent
		if (parent instanceof ElementBase ) {
			for (Iterator iter = breaks.keySet().iterator(); iter.hasNext();) {
				((ElementBase) parent).addBreakPoint((String) iter.next());				
			}
		}
	}
	
	public void addBreakPoint(String name){
		Integer num = new Integer(getChildren().size());
		breaks.put(name,num);
//		if (null != parent && parent instanceof ElementBase) {
//			((ElementBase) parent).addBreakPoint(name);
//		}
	}
	
	

	/* (non-Javadoc)
	 * Build this class name with childrens.
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// 
		StringBuffer buf = new StringBuffer(this.getClass().getName());
		buf.append(" [\n");
		for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
			Object child = (Object) iter.next();
			buf.append("    ").append(child.toString()).append(" ;\n");
		}
		buf.append("]\n");
		return buf.toString();
	}

	/**
	 * @return Returns the value. If is EL expression valueBinding - evaluate in.
	 */
	public Object getValue(TemplateContext context) {
		return valueGetter.getValue(context);
	}

	public String getString(TemplateContext context) throws FacesException {
		StringBuffer string = new StringBuffer();
		string.append(valueGetter.getStringOrDefault(context));
		for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof ElementBase) {
				ElementBase stringElement = (ElementBase) element;
				string.append(stringElement.getString(context));
			}
		}
		return string.toString();
	}
	/**
	 * @param value The value to set. if EL-expression ( #{...} ) - compile it.
	 */
	public void setValue(Object value) {
		if (value instanceof String) {
			String stringValue = (String) value;
			if(isValueReference(stringValue)){
				_value = value;
				try {
					Application app = ((ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY)).getApplication();
					_valueBinding = app.createValueBinding(stringValue);
				} catch (Exception e) {
					// Do nothing - create at first call 
				}
				this.valueGetter = new ValueGetter(){

					public /* (non-Javadoc)
					 * @see org.ajax4jsf.renderkit.compiler.RootElement.ValueGetter#getValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
					 */
					Object getValue(TemplateContext context) {
						// TODO Auto-generated method stub
						if(null==_valueBinding) {
							Application  app = context.getFacesContext().getApplication();
							_valueBinding = app.createValueBinding((String) _value);							
						}
						return _valueBinding.getValue(context.getFacesContext());
					}
					
				};
				return;
			}
		}
		this._value = value;
		this.valueGetter = new ValueGetter(){

			public /* (non-Javadoc)
			 * @see org.ajax4jsf.renderkit.compiler.RootElement.ValueGetter#getValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
			 */
			Object getValue(TemplateContext context) {
				// TODO Auto-generated method stub
				return _value;
			}
			
		};
	}

	/**
	 * @return Returns the default.
	 */
	public Object getDefault() {
		return _default;
	}

	/**
	 * @param default1 The default to set.
	 */
	public void setDefault(Object default1) {
		_default = default1;
	}

	/**
	 * @param property The property to set.
	 */
	public void setProperty(String property) {
		_property = property;
		valueGetter = new ValueGetter(){

			/* (non-Javadoc)
			 * @see org.ajax4jsf.renderkit.compiler.RootElement.ValueGetter#getValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
			 */
			Object getValue(TemplateContext context) {
				Object value = context.getComponent().getAttributes().get(_property);
				if (context.getRenderer().getUtils().isValidProperty(value)) {
					return value; 
				} else {
					return null;
				}
			}
			
		};
	}

	/**
	 * @param skin The skin to set.
	 */
	public void setBaseSkin(String baseSkin) {
		_baseSkin = baseSkin;
//		if( null == _skinConfiguration ){
			// Only Skin parameter is used.
		valueGetter = new SkinValueGetter();
	}
	
	/**
	 * @param skin The skin to set.
	 */
	public void setSkin(String skin) {
		_skin = skin;
//		if( null == _skinConfiguration ){
			// Only Skin parameter is used.
		valueGetter = new SkinValueGetter();
//		} else {
//			valueGetter = new ConfigurationAndSkinValueGetter();			
//		}
	}

	/**
	 * Set name of skin parameter in "mandatory" list ( default value for fine-tuning parameter ).
	 * @param mandatorySkin
	 */
//	public void setSkinConfiguration(String mandatorySkin) {
//		_skinConfiguration = mandatorySkin;
//		if(null == _skin){
//			valueGetter =  new ConfigurationValueGetter();
//		} else {
//			valueGetter = new ConfigurationAndSkinValueGetter();
//		}
//	}

	/**
	 * @param context The context to set.
	 */
	public void setContext(Object context) {
		_context = context;
		//System.out.println("CTX:"  + context);
		valueGetter = new ValueGetter(){

			/* (non-Javadoc)
			 * @see org.ajax4jsf.renderkit.compiler.RootElement.ValueGetter#getValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
			 */
			Object getValue(TemplateContext context) {
				// TODO Auto-generated method stub
				return context.getParameter(_context);
			}
			
		};
	}

	/**
	 * @param call The call to set.
	 */
	public void setCall(String call) {
		this._call = call;
		valueGetter = new ValueGetter(){

			/* (non-Javadoc)
			 * @see org.ajax4jsf.renderkit.compiler.RootElement.ValueGetter#getValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
			 */
			Object getValue(TemplateContext context) {
				try {
					return MethodUtils.invokeMethod(context
							.getRenderer(), _call, new Object[]{context});
				} catch (Exception e) {
					throw new FacesException(Messages.getMessage(Messages.INVOKE_RENDERER_METHOD_ERROR, this.getClass().getName()), e);
				}
			}
			
		};
	}

	/**
	 * Get EL-enabled value. Return same string, if not el-expression.
	 * Otherthise, return parsed and evaluated expression.
	 * @param context - current Faces Context.
	 * @param value - string to parse.
	 * @return - interpreted el or unmodified value.
	 */
	protected boolean isValueReference( String value) {
		if (value == null)
			return false;

		int start = value.indexOf("#{");
		if (start >= 0) {
			int end = value.lastIndexOf('}');
			if (end >= 0 && start < end) {
				return true;
			}
		}
		return false;
	}

//	private final class ConfigurationValueGetter extends ValueGetter {
//		/* (non-Javadoc)
//		 * @see org.ajax4jsf.renderkit.compiler.RootElement.ValueGetter#getValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
//		 */
//		Object getValue(TemplateContext context) {
//			FacesContext facesContext = context.getFacesContext();
//			Object parameter = SkinFactory.getInstance().getSkinConfiguration(facesContext).getParameter(facesContext,_skinConfiguration);
//			if(null == parameter){
//				parameter = _default;
//			}
//			return parameter;
//		}
//	}
//
//	private final class ConfigurationAndSkinValueGetter extends ValueGetter {
//		/* (non-Javadoc)
//		 * @see org.ajax4jsf.renderkit.compiler.RootElement.ValueGetter#getValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
//		 */
//		Object getValue(TemplateContext context) {
//			FacesContext facesContext = context.getFacesContext();
//			return SkinFactory.getInstance().getSkinConfiguration(facesContext).getParameter(facesContext,_skinConfiguration,_skin,_default);
//		}
//	}
	
	
	protected class ValueGetter {
		Object getValue(TemplateContext context) {
			return null;
		}
		
		Object getValueOrDefault(TemplateContext context){
			Object result = getValue(context);
			if (null == result) {
				result = getDefault();
			}
			return result;
		}
		
		String getStringOrDefault(TemplateContext context){
			Object result = getValue(context);
			if (null == result || result.toString().length()==0) {
				result = getDefault();
				if (null == result) {
					result = "";
				}
			}
			return result.toString();
			
		}
	}
	
	protected class SkinValueGetter extends ValueGetter {
		Object getValue(TemplateContext context) {
			FacesContext facesContext = context.getFacesContext();
			SkinFactory skinFactory = SkinFactory.getInstance();
			Object result = null;
			
			if (_skin != null) {
				result = skinFactory.getSkin(facesContext).getParameter(facesContext,_skin,_default);
			} else {
				if (_baseSkin != null) {
					result = skinFactory.getSkin(facesContext).getParameter(facesContext,_baseSkin,_default);
				}
			}

			if (_baseSkin != null && (result == null || result.toString().length()==0)) {
				result = skinFactory.getBaseSkin(facesContext).getParameter(facesContext,_baseSkin,_default);
			}
			return result;
		}
	}
	
}
