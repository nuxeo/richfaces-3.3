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

import java.util.EmptyStackException;

import javax.servlet.ServletException;

import org.ajax4jsf.io.parser.FastHtmlParser;
import org.ajax4jsf.webapp.BaseXMLFilter;
import org.ajax4jsf.webapp.HtmlParser;
import org.apache.commons.collections.ArrayStack;

public class NekkoXMLFilter extends BaseXMLFilter {
	private static final int STACK_SIZE = 100;
	private static ArrayStack _xhtmlParsersPool = new ArrayStack(STACK_SIZE);
	
	public NekkoXMLFilter() {}

	protected HtmlParser getParser(String mimetype, boolean isAjax, String viewId) {
		HtmlParser parser = null;
		if( isAjax ){
			parser = getXmlParser(mimetype);
		} else if (mimetype.startsWith(TEXT_HTML) || mimetype.startsWith(APPLICATION_XHTML_XML)) {
			parser = new FastHtmlParser();
		} else {
			return null;
		}
//		parser.setEncoding(characterEncoding);
		return parser;
	}

	/**
     * Peturn parser to pool
	 * @param parser
	 */
	protected void reuseParser(HtmlParser parser) {
		if (null != parser) {
			if (parser instanceof NekkoParser ) {
					synchronized (_xhtmlParsersPool) {
						if (_xhtmlParsersPool.size() < STACK_SIZE) {
							((NekkoParser) parser).reset();
							_xhtmlParsersPool.push(parser);
						}
					
				}
			} 
		}		
	}

    /**
     * Factory method for create parsing object - contain chain of parsing,
     * transformation and serialization of response output
     * @param string Encodings for parser
	 * @return
     * @throws ServletException 
	 */
	protected HtmlParser getXmlParser(String mime)  {
		// TODO make pool of parsers-transformers.
		NekkoParser parser ;
		try {
			synchronized (_xhtmlParsersPool) {
				parser = (NekkoParser) _xhtmlParsersPool.pop();
			}
		} catch (EmptyStackException e) {
			parser = new NekkoParser();
			parser.setPublicId(getPublicid());
			parser.setSystemid(getSystemid());
			parser.setNamespace(getNamespace());
			// If tidy not handle all requests, disable reorganising of html
//			parser.setMoveElements(isForcexml());
			parser.init();
		}
		parser.setMime(mime);
		// TODO - set header scripts/styles filter.
		return parser;
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.webapp.BaseFilter#getWrapper(javax.servlet.http.HttpServletResponse)
	 */
//	protected FilterServletResponseWrapper getWrapper(HttpServletResponse response) throws ServletException {
//		// TODO Auto-generated method stub
//		NekkoHtmlServletResponseWrapper wrapper = new NekkoHtmlServletResponseWrapper(response);
//		// TODO - create pool of parsers.
//		wrapper.setParser(getParser());
//		return wrapper;
//	}

}
