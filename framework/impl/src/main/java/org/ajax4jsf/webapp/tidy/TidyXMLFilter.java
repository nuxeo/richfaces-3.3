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

package org.ajax4jsf.webapp.tidy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ajax4jsf.Messages;
import org.ajax4jsf.io.parser.FastHtmlParser;
import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.ajax4jsf.webapp.BaseXMLFilter;
import org.ajax4jsf.webapp.HtmlParser;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TidyXMLFilter extends BaseXMLFilter {
	static final Log log = LogFactory.getLog(TidyXMLFilter.class);
	private Properties _tidyProperties;
	private static final int STACK_SIZE = 100;

	/**
	 * Stack for handle parsers pool - to avoid instantiane on each request.
	 */
	private ArrayStack _parsersPool = new ArrayStack(STACK_SIZE);	

	public TidyXMLFilter() {}

	/**
     * Peturn parser to pool
	 * @param parser
	 */
	protected void reuseParser(HtmlParser parser) {
//		synchronized (_parsersPool) {
//			if (_parsersPool.size()<STACK_SIZE) {
//				if (log.isDebugEnabled()) {
//					log.debug("Push JTidy parser to pool");
//				}
//				_parsersPool.push(parser);
//			}
//		}		
	}

    /**
     * Factory method for create parsing object - contain chain of parsing,
     * transformation and serialization of response output
     * @param string Encodings for parser
     * @return
	 */
	protected HtmlParser getParser(String mime, boolean isAjax, String viewId) {
		// For non-ajax, parse only html types !
			if (isAjax || mime.startsWith(TEXT_HTML) || mime.startsWith(APPLICATION_XHTML_XML)) {
				if(isAjax || isForcexml()){
				TidyParser parser ;
				parser = new TidyParser(getTidyProperties());
				// If tidy not handle all requests, disable reorganising of html
				parser.setMoveElements(isForcexml());
				// Setup configuration.
				if (parser.setMime(mime)) {
					//			parser.setEncoding(encoding);
					if (log.isDebugEnabled()) {
						log.debug(Messages.getMessage(Messages.CREATE_JTIDY_INFO));
					}
					return parser;
				}
				} else {
					return new FastHtmlParser();
				}
			}
		return null;
	}
	
    private Properties getTidyProperties() {
		if (null == _tidyProperties) {
			_tidyProperties = new Properties();
			InputStream props = null;
			try {
				props = URLToStreamHelper.urlToStreamSafe(
						TidyXMLFilter.class.getResource("tidy.properties"));
				if (null != props) {
					_tidyProperties.load(props);
				}
				// Second part - user-defined properties.
				props = URLToStreamHelper.urlToStreamSafe(
						Thread.currentThread().getContextClassLoader().getResource("tidy.properties"));
				if (null != props) {
					_tidyProperties.load(props);
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.warn(Messages.getMessage(Messages.READING_TIDY_PROPERTIES_ERROR), e);
			} finally {
				if(null != props){
					try {
						props.close();
					} catch (IOException e) {
						// can be ignored
					}
				}
			}
		}
		return _tidyProperties;
		
	}	

}
