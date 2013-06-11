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

package org.ajax4jsf.webapp.nekko;

import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.cyberneko.html.HTMLAugmentations;
import org.cyberneko.html.filters.DefaultFilter;

/**
 * Fix common DHTML incompabilites for html - insert ommited tbody tag in tables.
 * @author shura
 *
 */
public class HtmlCorrectionFilter extends DefaultFilter{
	
	private HtmlCorrectionState _state;
	

	/**
	 * 
	 */
	public HtmlCorrectionFilter() {
		super();
		_state = new BaseHtmlCorrectionState(null);
	}
	
	public void reset(XMLComponentManager componentManager)
	        throws XMLConfigurationException {
	    super.reset(componentManager);
	    _state = new BaseHtmlCorrectionState(null);
	}

	/* (non-Javadoc)
	 * @see org.cyberneko.html.filters.DefaultFilter#endElement(org.apache.xerces.xni.QName, org.apache.xerces.xni.Augmentations)
	 */
	public void endElement(QName element, Augmentations augs) throws XNIException {
		_state.endElement(element,augs);
		super.endElement(element, augs);
	}

	/* (non-Javadoc)
	 * @see org.cyberneko.html.filters.DefaultFilter#startElement(org.apache.xerces.xni.QName, org.apache.xerces.xni.XMLAttributes, org.apache.xerces.xni.Augmentations)
	 */
	public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
		_state.startElement(element,attributes,augs);
		super.startElement(element, attributes, augs);
	}
	
	 void insertStartElement(String name, QName base) {
	        QName element = createQName(name, base);
		XMLAttributes attrs = new XMLAttributesImpl();
		Augmentations augs = new HTMLAugmentations();
		super.startElement(element,attrs,augs);
	}

	/**
	 * @param name
	 * @param base
	 * @return
	 */
	private QName createQName(String name, QName base) {
	    String prefix = base.prefix;
	    String rawname = null != prefix && (!"".equals(prefix))? prefix+':'+name:name; 
	    QName element = new QName(prefix,name,rawname,base.uri);
	    return element;
	}
	
	
	 void  insertEndElement(String name, QName base) {
	        QName element = createQName(name, base);
		Augmentations augs = new HTMLAugmentations();
		super.endElement(element,augs);
	}
	
	private class BaseHtmlCorrectionState implements HtmlCorrectionState {
		private int depth = 0;
		private HtmlCorrectionState previsiosState;
		/**
		 * @param previsiosState
		 */
		public BaseHtmlCorrectionState(HtmlCorrectionState previsiosState) {
			super();
			// TODO Auto-generated constructor stub
			this.previsiosState = previsiosState;
		}
		public void endElement(QName element, Augmentations augs) throws XNIException {
			depth--;
			if(depth<0 && previsiosState != null){
				_state = previsiosState;
			}
		}
		
		public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
			if(element.rawname.equalsIgnoreCase("table")){
				_state = new TableHtmlCorrectionState(this);
			} else {
			    depth++;
			}
		}
		
	
	}
	private class TableHtmlCorrectionState implements HtmlCorrectionState {
		private int depth = 0;
		private boolean inTbody = false;
		private HtmlCorrectionState previsiosState;
		/**
		 * @param previsiosState
		 */
		public TableHtmlCorrectionState(HtmlCorrectionState previsiosState) {
			super();
			// TODO Auto-generated constructor stub
			this.previsiosState = previsiosState;
		}
		
		public void endElement(QName element, Augmentations augs) throws XNIException {
				if (inTbody) {
					insertEndElement("tbody", element);
				}
				_state = previsiosState;
		}
		
		public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
			if(element.rawname.equalsIgnoreCase("tr")){
				if(!inTbody){
					inTbody = true;
					insertStartElement("tbody", element);
				}
			} else {
				if (inTbody) {
					insertEndElement("tbody", element);
					inTbody = false;
				}
			}
			_state = new BaseHtmlCorrectionState(this);
		}
		
	
	}

}