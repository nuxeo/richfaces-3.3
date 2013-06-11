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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ajax4jsf.application.AjaxViewHandler;
import org.ajax4jsf.webapp.BaseXMLFilter;
import org.ajax4jsf.webapp.HtmlParser;
import org.ajax4jsf.xml.serializer.Method;
import org.ajax4jsf.xml.serializer.OutputPropertiesFactory;
import org.ajax4jsf.xml.serializer.Serializer;
import org.ajax4jsf.xml.serializer.SerializerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.cyberneko.html.HTMLAugmentations;
import org.cyberneko.html.HTMLConfiguration;
import org.cyberneko.html.filters.DefaultFilter;
import org.cyberneko.html.filters.ElementRemover;
import org.cyberneko.html.filters.Purifier;
import org.cyberneko.html.filters.Writer;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author shura
 * 
 */
public class NekkoParser implements HtmlParser {

	private static final Log _log = LogFactory.getLog(NekkoParser.class);

	private HtmlSAXParser _parser;

	// private HtmlWriter _writer= new HtmlWriter();

	private ElementRemover remover = new ElementRemover();

	private XMLDocumentFilter[] _filters = {
			new ViewStateFilter()/* ,remover */, new HtmlCorrectionFilter(),
			new Purifier() /* , _writer */};

	private DOMFragmentParser viewStateParser;// = new DOMFragmentParser();

	private Document viewStateDocument;// = new HTMLDocumentImpl();

	private DocumentFragment fragment = null;

	private Node[] headEvents;
	
	private String _viewState;

	private String _encoding;

	private Serializer _serializer;

	private String _publicId = "-//W3C//DTD XHTML 1.0 Transitional//EN";
	private String _systemid = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd";
	private String _namespace = "http://www.w3.org/1999/xhtml";

	private String _outputEncoding;

	private Serializer _XHTMLserializer;

	private Serializer _XMLserialiser;

	private Serializer _HTMLserialiser;

	/**
	 * 
	 */
	public NekkoParser() {
	}

	/**
	 * 
	 */
	public void init() {
		_parser = new HtmlSAXParser(getHtmlConfig());
		Properties properties = OutputPropertiesFactory
				.getDefaultMethodProperties(Method.XHTML);
		_XHTMLserializer = SerializerFactory.getSerializer(properties);
		properties = OutputPropertiesFactory.getDefaultMethodProperties(Method.XML);
		_XMLserialiser = SerializerFactory.getSerializer(properties);
		properties = OutputPropertiesFactory.getDefaultMethodProperties(Method.HTML);
		_HTMLserialiser = SerializerFactory.getSerializer(properties);
		_serializer = _HTMLserialiser;
		// serializer.setOutputStream(output);
		// _parser.setContentHandler(serializer.asContentHandler());
		viewStateParser = new DOMFragmentParser();
		// Set parser features
		try {
			viewStateParser
					.setProperty(
							"http://cyberneko.org/html/properties/names/elems",
							"lower");
			viewStateParser
					.setProperty(
							"http://cyberneko.org/html/properties/names/attrs",
							"lower");
		} catch (SAXException e) {
			_log.error("Exception in DOM parser configuration", e);
		}
		try {
			// Create Document Builder Factory
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			// Create Document Builder
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			viewStateDocument = docBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			viewStateDocument = new HTMLDocumentImpl();
			_log
					.error(
							"Error on create DOM Document by JAXP, use Xerxes implementation. Check JAXP configuration ",
							e);
		}
		// viewStateDocument = new HTMLDocumentImpl();
		remover.removeElement("style");
	}

	/**
	 * Reset parser state
	 */
	public void reset() {
		headEvents = null;
		_viewState = null;
		_parser.reset();
		_XMLserialiser.reset();
		_HTMLserialiser.reset();
		_XHTMLserializer.reset();
		_serializer = _HTMLserialiser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.webapp.HtmlParser#parseHtml(java.io.InputStream,
	 *      java.io.OutputStream)
	 */
	public void parseHtml(InputStream input, java.io.Writer output)
			throws IOException {
		InputSource src = new InputSource(input);
		parseSAXSource(src, output);
	}

	private void parseSAXSource(InputSource src, java.io.Writer output)
			throws IOException {
		// PrintWriter printWriter = null;
		fragment = null;
		if (null != _viewState) {
			fragment = viewStateDocument.createDocumentFragment();
			try {
				viewStateParser.parse(new InputSource(new StringReader(
						_viewState)), fragment);
			} catch (Exception e) {
				fragment = null;
			}
			// TODO - parse view state to DOM Fragment.
		}
		try {
			if (null != _encoding) {
				_parser
						.setProperty(
								"http://cyberneko.org/html/properties/default-encoding",
								_encoding);

			}
			Properties properties = _serializer.getOutputFormat();
			if (null != _outputEncoding) {
				properties.put("encoding", _outputEncoding);

			}
			_serializer.setOutputFormat(properties);
			_serializer.setWriter(output);
			// _serializer.setOutputStream(new OutputStream(){
			//
			// public void write(int b) throws IOException {
			// // TODO Auto-generated method stub
			//					
			// }});
			_parser.setContentHandler(_serializer.asContentHandler());
			// printWriter = new PrintWriter(output);
			// _writer.setWriter(printWriter);
			// _writer.setEncoding(_encoding);
			_parser.parse(src);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// _writer.setWriter(null);
			// if(null != printWriter){
			// printWriter.flush();
			// printWriter.close();
			// }

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.webapp.HtmlParser#parseHtml(java.io.Reader,
	 *      java.io.OutputStream)
	 */
	public void parseHtml(Reader input, java.io.Writer output)
			throws IOException {
		InputSource src = new InputSource(input);
		parseSAXSource(src, output);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.webapp.HtmlParser#setEncoding(java.lang.String)
	 */
	public void setInputEncoding(String encoding) {
		_encoding = encoding;
	}

	public void setOutputEncoding(String encoding) {
		_outputEncoding = encoding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.webapp.HtmlParser#setMoveElements(boolean)
	 */
	public void setMoveElements(boolean move) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.webapp.HtmlParser#setDoctype(java.lang.String)
	 */
	public void setDoctype(String doctype) {
		this._publicId = doctype;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.webapp.HtmlParser#setViewState(java.lang.String)
	 */
	public void setViewState(String viewState) {
		_viewState = viewState;

	}

	private static class HtmlSAXParser extends AbstractSAXParser {
		/**
		 * Default constructor.
		 * 
		 * @throws ServletException
		 */
		public HtmlSAXParser(HTMLConfiguration config) {
			super(config);

		}
	}

	private class ViewStateFilter extends DefaultFilter {
		private boolean haveHtml = false;
		private boolean haveHead = false;
		private boolean headParsed = false;
		private int stateMarkerLevel = -1;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#reset(org.apache.xerces.xni.parser.XMLComponentManager)
		 */
		public void reset(XMLComponentManager componentManager)
				throws XMLConfigurationException {
			haveHead = false;
			haveHtml = false;
			headParsed = false;
			stateMarkerLevel = -1;
			super.reset(componentManager);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#startElement(org.apache.xerces.xni.QName,
		 *      org.apache.xerces.xni.XMLAttributes,
		 *      org.apache.xerces.xni.Augmentations)
		 */
		public void startElement(QName element, XMLAttributes attributes,
				Augmentations augs) throws XNIException {
			if (stateMarkerLevel >= 0) {
				stateMarkerLevel++;
				if (null != fragment) {
					return;
				}
			}
			if (!headParsed) {
				if ("html".equalsIgnoreCase(element.rawname)) {
					haveHtml = true;
				} else if ("head".equalsIgnoreCase(element.rawname)) {
					haveHead = true;
					super.startElement(element, attributes, augs);
					// insertResources();
					return;
				} else if (haveHead) {
					// <title> or <base> in the head
					if (!("title".equalsIgnoreCase(element.rawname) || "base"
							.equalsIgnoreCase(element.rawname))) {
						insertResources(element.prefix, element.uri);
					}
				} else {
					if (!haveHtml) {
						insertStartElement("html");
					}
					insertStartElement("head");
					insertResources(element.prefix, element.uri);
					insertEndElement("head");
				}

			}
			if (stateMarkerLevel <0 && isStateMarker(element, attributes)) {
				stateMarkerLevel = 0;
				return;
			}
			;
			super.startElement(element, attributes, augs);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#characters(org.apache.xerces.xni.XMLString,
		 *      org.apache.xerces.xni.Augmentations)
		 */
		public void characters(XMLString text, Augmentations augs)
				throws XNIException {
			if (stateMarkerLevel >= 0) {
				if (null != fragment) {
					return;
				}
			}
			super.characters(text, augs);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#endElement(org.apache.xerces.xni.QName,
		 *      org.apache.xerces.xni.Augmentations)
		 */
		public void endElement(QName element, Augmentations augs)
				throws XNIException {
			if (stateMarkerLevel >= 0) {
				stateMarkerLevel--;
				if (null != fragment || stateMarkerLevel == -1) {
					return;
				}
			}
			if (!headParsed && "head".equalsIgnoreCase(element.rawname)) {
				insertResources(element.prefix, element.uri);
			}
			super.endElement(element, augs);
		}

		private XMLString createXMLString(String string) {
			char[] cs = string.toCharArray();
			return new XMLString(cs, 0, cs.length);
		}
		
		private void addNode(Node node, String prefix, String uri, Augmentations augs) {
			switch (node.getNodeType()) {
			case Node.TEXT_NODE:
				String charData = ((CharacterData) node).getData();
				super.characters(createXMLString(charData), augs);
				
				break;

			case Node.COMMENT_NODE:
				charData = ((CharacterData) node).getData();
				super.comment(createXMLString(charData), augs);
				
				break;
				
			case Node.CDATA_SECTION_NODE:
				charData = ((CharacterData) node).getData();
				super.startCDATA(augs);
				super.characters(createXMLString(charData), augs);
				super.endCDATA(augs);

				break;

			case Node.ELEMENT_NODE:
				QName name = new QName(prefix, node.getNodeName(), node.getNodeName(), uri);
				XMLAttributes attrs = new XMLAttributesImpl();
				
				NamedNodeMap attributes = node.getAttributes();
				if (attributes != null) {
					int l = attributes.getLength();
	
					for (int i = 0; i < l; i++) {
						Node attributeNode = attributes.item(i);
						attrs.addAttribute(new QName(prefix, attributeNode.getNodeName(), 
								attributeNode.getNodeName(), uri), "CDATA", 
								attributeNode.getNodeValue());
					}
					
				}

				super.startElement(name, attrs, augs);

				NodeList childNodes = node.getChildNodes();
				int nodesLength = childNodes.getLength();
				for (int i = 0; i < nodesLength; i++) {
					addNode(childNodes.item(i), prefix, uri, augs);
				}
				
				super.endElement(name, augs);
				
				break;
				
			default:
				throw new IllegalArgumentException("Node type " + node.getNodeType() + " is not supported!");
			}
		}
		
		private void insertResources(String prefix, String uri) {
			headParsed = true;
			
			if (headEvents != null && headEvents.length > 0) {
				Augmentations augs = new HTMLAugmentations();

				for (Node node : headEvents) {
					addNode(node, prefix, uri, augs);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#emptyElement(org.apache.xerces.xni.QName,
		 *      org.apache.xerces.xni.XMLAttributes,
		 *      org.apache.xerces.xni.Augmentations)
		 */
		public void emptyElement(QName name, XMLAttributes attributes,
				Augmentations augmentation) throws XNIException {
			if (stateMarkerLevel >= 0) {
				if (null != fragment) {
					return;
				}
			}
			if (!headParsed) {
				if (haveHead) {
					// <title> or <base> in the head
					if (!("title".equalsIgnoreCase(name.rawname) || "base"
							.equalsIgnoreCase(name.rawname))) {
						insertResources(name.prefix, name.uri);
					}
				} else if ("head".equalsIgnoreCase(name.rawname)) {
					haveHead = true;
					super.startElement(name, attributes, augmentation);
					insertResources(name.prefix, name.uri);
					insertEndElement(name.rawname);
					return;
				}
			}
			if (isStateMarker(name, attributes)) {
				return;
			}
			;
			super.emptyElement(name, attributes, augmentation);
		}

		/**
		 * @param name
		 * @param attributes
		 */
		private boolean isStateMarker(QName name, XMLAttributes attributes) {
			if (name.rawname.equalsIgnoreCase("span")
					&& AjaxViewHandler.STATE_MARKER_KEY.equals(attributes
							.getValue("id"))) {
				// STATE marker element - out real content.
				if (null != fragment) {
					try {
						_serializer.asDOMSerializer().serialize(fragment);
					} catch (IOException e) {
						// Break output.
					}
				}
				return true;
			}
			return false;
		}

		void insertStartElement(String name) {
			QName element = new QName(null, name, name, null);
			XMLAttributes attrs = new XMLAttributesImpl();
			Augmentations augs = new HTMLAugmentations();
			super.startElement(element, attrs, augs);
		}

		void insertEndElement(String name) {
			QName element = new QName(null, name, name, null);
			// XMLAttributes attrs = new XMLAttributesImpl();
			Augmentations augs = new HTMLAugmentations();
			super.endElement(element, augs);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#endDocument(org.apache.xerces.xni.Augmentations)
		 */
		public void endDocument(Augmentations augs) throws XNIException {
			if (!haveHtml) {
				insertEndElement("html");
			}
			super.endDocument(augs);
		}

	}

	private static class HtmlWriter extends Writer {

		/**
		 * 
		 */
		public HtmlWriter() {
			super();
			fEncoding = "UTF-8";
		}

		public void setEncoding(String encoding) {
			this.fEncoding = encoding;
		}

		public void setWriter(PrintWriter writer) {
			this.fPrinter = writer;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.Writer#emptyElement(org.apache.xerces.xni.QName,
		 *      org.apache.xerces.xni.XMLAttributes,
		 *      org.apache.xerces.xni.Augmentations)
		 */
		public void emptyElement(QName element, XMLAttributes attributes,
				Augmentations augs) throws XNIException {
			// TODO Auto-generated method stub
			super.emptyElement(element, attributes, augs);
			printEndElement(element);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#endCDATA(org.apache.xerces.xni.Augmentations)
		 */
		public void endCDATA(Augmentations augs) throws XNIException {
			// TODO Auto-generated method stub
			super.endCDATA(augs);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#startCDATA(org.apache.xerces.xni.Augmentations)
		 */
		public void startCDATA(Augmentations augs) throws XNIException {
			// TODO Auto-generated method stub
			super.startCDATA(augs);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#textDecl(java.lang.String,
		 *      java.lang.String, org.apache.xerces.xni.Augmentations)
		 */
		public void textDecl(String version, String encoding, Augmentations augs)
				throws XNIException {
			// TODO Auto-generated method stub
			super.textDecl(version, encoding, augs);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cyberneko.html.filters.DefaultFilter#xmlDecl(java.lang.String,
		 *      java.lang.String, java.lang.String,
		 *      org.apache.xerces.xni.Augmentations)
		 */
		public void xmlDecl(String version, String encoding, String standalone,
				Augmentations augs) throws XNIException {
			// TODO Auto-generated method stub
			super.xmlDecl(version, encoding, standalone, augs);
		}

	}

	/**
	 * Factory method for create and configure HTML parser configuration. Create
	 * configuration for use in parsing, set nessesary features and properties
	 * 
	 * @return
	 * @throws ServletException
	 */
	protected HTMLConfiguration getHtmlConfig() {
		HTMLConfiguration _config = new HTMLConfiguration();
		try {
			if (this.getPublicid() != null || this.getSystemid() != null) {
				_config.setFeature(
						"http://cyberneko.org/html/features/insert-doctype",
						true);
				_config.setFeature(
						"http://cyberneko.org/html/features/override-doctype",
						true);
			}
			if (this.getPublicid() != null) {
				_config.setProperty(
						"http://cyberneko.org/html/properties/doctype/pubid",
						getPublicid());

			}
			if (this.getSystemid() != null) {
				_config.setProperty(
						"http://cyberneko.org/html/properties/doctype/sysid",
						getSystemid());

			}
			if (this.getNamespace() != null) {
				_config.setFeature("http://xml.org/sax/features/namespaces",
						true);
				_config
						.setFeature(
								"http://cyberneko.org/html/features/override-namespaces",
								true);
				_config.setFeature(
						"http://cyberneko.org/html/features/insert-namespaces",
						true);
				_config.setProperty(
						"http://cyberneko.org/html/properties/namespaces-uri",
						getNamespace());

			}
			// config
			// .setFeature(
			// "http://cyberneko.org/html/features/balance-tags/ignore-outside-content",
			// true);
			_config
					.setFeature(
							"http://cyberneko.org/html/features/scanner/cdata-sections",
							true);
			_config
					.setFeature(
							"http://cyberneko.org/html/features/scanner/script/strip-comment-delims",
							true);
			_config
					.setFeature(
							"http://cyberneko.org/html/features/scanner/style/strip-comment-delims",
							true);
			_config.setFeature(
					"http://cyberneko.org/html/features/insert-doctype", true);
			_config.setFeature(
					"http://cyberneko.org/html/features/insert-namespaces",
					true);
			//  
			// Set properties
			// http://cyberneko.org/html/features/insert-namespaces
			// _config
			// .setProperty(
			// "http://cyberneko.org/html/properties/default-encoding",
			// encoding);
			_config
					.setProperty(
							"http://cyberneko.org/html/properties/names/elems",
							"lower");
			_config
					.setProperty(
							"http://cyberneko.org/html/properties/names/attrs",
							"lower");
			_config.setProperty("http://cyberneko.org/html/properties/filters",
					_filters);
		} catch (XMLConfigurationException e) {
			// throw new ServletException("error set Neko feature ", e);
		}
		return _config;
	}

	private String getNamespace() {
		// TODO Auto-generated method stub
		return this._namespace;
	}

	private String getSystemid() {
		// TODO Auto-generated method stub
		return this._systemid;
	}

	private String getPublicid() {
		// TODO Auto-generated method stub
		return this._publicId;
	}

	/**
	 * @param namespace
	 *            The namespace to set.
	 */
	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	/**
	 * @param publicId
	 *            The publicId to set.
	 */
	public void setPublicId(String publicId) {
		_publicId = publicId;
	}

	/**
	 * @param systemid
	 *            The systemid to set.
	 */
	public void setSystemid(String systemid) {
		_systemid = systemid;
	}

	public boolean setMime(String mimeType) {
		if(null != mimeType) {
			if(mimeType.startsWith(BaseXMLFilter.TEXT_HTML)){
				_serializer = _HTMLserialiser;
			} else if (mimeType.startsWith(BaseXMLFilter.APPLICATION_XHTML_XML)) {
				_serializer = _XHTMLserializer;
			} else if (mimeType.startsWith("text/xml")) {
				_serializer = _XHTMLserializer;
//				_serializer = _XMLserialiser;
			} else {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public void setHeadNodes(Node[] headEvents) {
		this.headEvents = headEvents;
	}

}
