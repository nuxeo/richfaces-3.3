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
package org.ajax4jsf.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.EmptyStackException;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.ajax4jsf.Messages;
import org.ajax4jsf.io.parser.FastHtmlParser;
import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.ajax4jsf.webapp.nekko.NekkoParser;
import org.ajax4jsf.webapp.tidy.TidyParser;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author asmirnov
 * 
 */
public class ConfigurableXMLFilter extends BaseXMLFilter {

    public static final String NONE = "NONE";

	public static final String NEKO = "NEKO";

	public static final String TIDY = "TIDY";

	private static final Log log = LogFactory
	    .getLog(ConfigurableXMLFilter.class);

    private static final String PARSERS_LIST_PARAMETER = "org.ajax4jsf.xmlparser.ORDER";

    private static final String VIEW_ID_PATTERN_PARAMETER = "org.ajax4jsf.xmlparser.";

    /**
     * map of the parsers names and viewId patterns for all parser type.
     */
    private ParserConfig parsers = new TidyParserConfig();

    // private ParserConfig passParserConfig = new PassParserConfig();

    public void init(FilterConfig config) throws ServletException {
	super.init(config);
	ServletContext servletContext = config.getServletContext();
	String parsersParameter = servletContext
		.getInitParameter(PARSERS_LIST_PARAMETER);
	if (null != parsersParameter) {
	    configureParsers(servletContext, parsersParameter);
	}
    }

    /**
     * @param servletContext
     * @param parsersParameter
     * @throws ServletException
     */
    public void configureParsers(ServletContext servletContext,
	    String parsersParameter) throws ServletException {
	String[] parsersNames = parsersParameter.split("\\s*,\\s*");
	for (int i = parsersNames.length - 1; i >= 0; i--) {
	    String parserName = parsersNames[i];
	    ParserConfig parserConfig;
	    if (TIDY.equals(parserName)) {
		parserConfig = new TidyParserConfig();
	    } else if (NEKO.equals(parserName)) {
		parserConfig = new NekoParserConfig();
	    } else if (NONE.equals(parserName)) {
		parserConfig = new PassParserConfig();
	    } else {
		throw new ServletException(
			"Unknown XML parser type in config parameter "
				+ parserName);
	    }
	    parserConfig.setNext(parsers);
	    if (null != servletContext) {
		try {
		    String parserViewPattern = servletContext
			    .getInitParameter(VIEW_ID_PATTERN_PARAMETER
				    + parserName);
		    parserConfig.setPatterns(parserViewPattern);

		} catch (PatternSyntaxException e) {
		    throw new ServletException("Invalid pattern for a parser "
			    + parserName + " :" + e.getMessage());
		}
	    }
	    parsers = parserConfig;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.webapp.BaseXMLFilter#getParser(java.lang.String,
     *      boolean, java.lang.String)
     */
    protected HtmlParser getParser(String mimetype, boolean isAjax,
	    String viewId) {

    	HtmlParser parser = null;
		
    	if (isAjax || mimetype.startsWith(TEXT_HTML) || mimetype.startsWith(APPLICATION_XHTML_XML)) {
			if (isAjax || isForcexml()) {
			    parser = parsers.getParser(viewId, mimetype);
			} else {
			    parser = new FastHtmlParser();
			}
		}

    	return parser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.webapp.BaseXMLFilter#reuseParser(org.ajax4jsf.webapp.HtmlParser)
     */
    protected void reuseParser(HtmlParser parser) {
	parsers.reuseParser(parser);

    }

    private static final int STACK_SIZE = 100;

    private ArrayStack _xhtmlParsersPool = new ArrayStack(STACK_SIZE);

    private class NekoParserConfig extends ParserConfig {

	protected HtmlParser createParser(String mime) {
			NekkoParser parser;
			try {
				synchronized (_xhtmlParsersPool) {
					parser = (NekkoParser) _xhtmlParsersPool.pop();
				}
			} catch (EmptyStackException e) {
				parser = new NekkoParser();
				parser.setPublicId(getPublicid());
				parser.setSystemid(getSystemid());
				parser.setNamespace(getNamespace());
				// If tidy not handle all requests, disable reorganising
				// of html
				// parser.setMoveElements(isForcexml());
				parser.init();
			}
			parser.setMime(mime);
			// TODO - set header scripts/styles filter.
			return parser;
		}

	boolean storeParser(HtmlParser parser) {
	    if (null != parser && parser instanceof NekkoParser) {
		synchronized (_xhtmlParsersPool) {
		    if (_xhtmlParsersPool.size() < STACK_SIZE) {
			((NekkoParser) parser).reset();
			_xhtmlParsersPool.push(parser);
		    }

		}
		return true;
	    }
	    return false;
	}
    }

    private class TidyParserConfig extends ParserConfig {

	protected HtmlParser createParser(String mime) {
	    // TODO Auto-generated method stub
	    TidyParser tidyParser = new TidyParser(getTidyProperties());
	    tidyParser.setMoveElements(isForcexml());
	    tidyParser.setMime(mime);
	    return tidyParser;
	}

    }

    private class PassParserConfig extends ParserConfig {

	protected HtmlParser createParser(String mime) {
	    return new FastHtmlParser();
	}

    }

    private Properties _tidyProperties;

    private Properties getTidyProperties() {
	if (null == _tidyProperties) {
	    _tidyProperties = new Properties();
	    InputStream defaultprops = null;
	    InputStream props = null;
	    try {
		defaultprops = URLToStreamHelper.urlToStreamSafe(TidyParser.class
			.getResource("tidy.properties"));
		if (null != defaultprops) {
		    _tidyProperties.load(defaultprops);
		    if (log.isDebugEnabled()) {
			log.debug("default tidy parser properties loaded");
		    }
		} else if (log.isDebugEnabled()) {
			log.debug("No default tidy parser properties found");
		    }

		// Second part - user-defined properties.
		props = URLToStreamHelper.urlToStreamSafe(Thread.currentThread().getContextClassLoader()
			.getResource("tidy.properties"));
		if (null != props) {
		    _tidyProperties.load(props);
		    if (log.isDebugEnabled()) {
			log.debug("application-specific tidy parser properties loaded");
		    }
		}
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		log.warn(Messages
			.getMessage(Messages.READING_TIDY_PROPERTIES_ERROR), e);
	    } finally {
		if (null != props) {
		    try {
			props.close();
		    } catch (IOException e) {
			// can be ignored
		    }
		}
		if (null != defaultprops) {
		    try {
			defaultprops.close();
		    } catch (IOException e) {
			// can be ignored
		    }
		}
	    }
	}
	return _tidyProperties;
    }

    public ParserConfig getParsers() {
        return parsers;
    }

}
