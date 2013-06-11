package org.richfaces.convert.seamtext;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.jboss.seam.text.SeamTextParser;
import org.richfaces.convert.seamtext.tags.HtmlTag;
import org.richfaces.convert.seamtext.tags.TagFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import antlr.SemanticException;
import antlr.Token;


/**
 * @user: akolonitsky
 * Date: Mar 24, 2009
 */
public class HtmlToSeamSAXParser extends DefaultHandler {
    private static final TagFactory TAG_FACTORY = new TagFactory();

    public static final String ROOT_TAG_NAME = "root";
    public final HtmlTag rootTag = TAG_FACTORY.getInstance(ROOT_TAG_NAME);
    
    public HtmlTag getRootTag() {
        return rootTag;
    }
    
    private SeamTextParser.Sanitizer sanitizer = new SeamTextParser.DefaultSanitizer();
    private HtmlToSeamTransformer transformer;

    private Stack<HtmlTag> tagStack;

    public HtmlToSeamSAXParser() {
        tagStack = new Stack<HtmlTag>();

        transformer = new HtmlToSeamTransformer();
        transformer.setHtmlElementStack(tagStack);
    }

    public static String convertHtmlToSeamText(final String html) throws IOException, SAXException {
        final HtmlToSeamSAXParser parser = new HtmlToSeamSAXParser();
        final XMLReader p = XMLReaderFactory.createXMLReader();
        p.setContentHandler(parser);
        p.setEntityResolver(parser);
        
        try {
            p.setFeature("http://xml.org/sax/features/namespaces", false);
            p.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
        } catch (SAXException e) {
            e.printStackTrace();  // TODO
        }
        final StringBuilder str = new StringBuilder(html.length() + 2*ROOT_TAG_NAME.length() + 5);

        str.append("<!DOCTYPE " + ROOT_TAG_NAME + " PUBLIC \"RichFaces W3C xHTML 1.1 Entities\" \"xhtml-entities-11.dtd\"><").append(ROOT_TAG_NAME).append('>').append(html).append("</").append(ROOT_TAG_NAME).append('>');

        p.parse(new InputSource(new StringReader(str.toString())));

        return parser.getRootTag().toString();
    }

	private InputSource resolveClassloaderResource(String name) throws IOException {
		URL result = null;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			result = classLoader.getResource(name);
		}
		
		if (result == null) {
			classLoader = getClass().getClassLoader();
    		if (classLoader != null) {
    			result = classLoader.getResource(name);
    		}
		}
		
		if (result != null) {
			return new InputSource(URLToStreamHelper.urlToStream(result));
		} else {
			return null;
		}
	}
	
	private static final String PACKAGE_NAME = "org/richfaces/convert/seamtext/";

	private static final String XHTML_ENTITIES11_NAME = PACKAGE_NAME + "xhtml-entities-11.dtd";
	
	private static final String W3ORG_ADDRESS = "http://www.w3.org/TR/xhtml1/DTD/";
	
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		
		InputSource result = null;
		
		if ("RichFaces W3C xHTML 1.1 Entities".equals(publicId)) {
			result = resolveClassloaderResource(XHTML_ENTITIES11_NAME);
		} else if (systemId.startsWith(W3ORG_ADDRESS)) {
			result = resolveClassloaderResource(PACKAGE_NAME + systemId.substring(W3ORG_ADDRESS.length()));
		}
		
		if (result == null) {
			result = super.resolveEntity(publicId, systemId);
		}
		
		return result;
	}
	
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (!tagStack.isEmpty()){
            tagStack.peek().setNotEmpty();
        }

        if (ROOT_TAG_NAME.equalsIgnoreCase(qName)) {
            tagStack.push(rootTag);
            transformer.setCurrentTag(rootTag);
        } else if (hasInvalidParentTag()) {
            if (!isValidTag(qName)) {
                tagStack.push(TAG_FACTORY.getInstance(qName, processAttr(qName, attributes)));
            }

        } else {
            final HtmlTag tag = TAG_FACTORY.getInstance(qName, processAttr(qName, attributes)); // process attr invoked when it is invalid tag? is is can be optimized
            if (isValidTag(tag)) {
                transformer.openTag(tag);
            } else {
                setFirstInvalidTag(tag);
            }

            tagStack.push(tag); // 'push' must be after transformer.openTag(tag); 
        }
    }

    private Map<String, String> processAttr(final String tag, Attributes attributes) {
        final Map<String, String> map = new HashMap<String, String>(attributes.getLength());
        for (int i = 0; i < attributes.getLength(); i++) {
            if (isValidAttr(tag, attributes.getQName(i), attributes.getValue(i))) {
                map.put(attributes.getQName(i), attributes.getValue(i));
            }
        }

        return map;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        final HtmlTag tag;

        if (ROOT_TAG_NAME.equalsIgnoreCase(qName)) {
            tag = tagStack.pop();
            if (!ROOT_TAG_NAME.equalsIgnoreCase(tag.getName())) {
                throw new IllegalStateException("Last tag must be '"+ ROOT_TAG_NAME +"', you have " + qName);
            }
        } else if (hasInvalidParentTag()) {
            if (!isValidTag(qName)) {
                tag = tagStack.pop();
                if (tag == getFirstInvalidTag()) { // it is must be same tag(object)
                    cleanInvalidTag();
                }
            }

        } else {
            tag = tagStack.pop();

            if (isValidTag(tag.getName())) {
                transformer.closeTagWithBody(tag);
            } else {
                throw new IllegalStateException("Sometning wrong! You can't have invalid tag at here!");
            }
        }
    }
    
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) {
        while (start < length) {
            switch (ch[start]) {
                case ' ': case '\t':
                    transformer.space(ch, start, length);
                    break;

                case '\r': case '\n':
                    transformer.newline(ch, start, length);
                    break;

                default:
                    throw new IllegalStateException("Unknow char : '" + ch[start] + '\'');
            }
            start++;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (!tagStack.isEmpty()){
            tagStack.peek().setNotEmpty();
        }

        transformer.text(ch, start, length);
    }

    /*
     * Vlidation by sanitizer
     * */
    private static final class FakeToken extends Token {
        private String text;

        private FakeToken(final int t, final String txt) {
            super(t, txt);
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public void setText(final String t) {
            this.text = t;
        }
    }

    private boolean isValidAttr(String tag, String name, String value) {
        try {
            final FakeToken tagToken = new FakeToken(0, tag);
            final FakeToken attrToken = new FakeToken(0, name);
            sanitizer.validateHtmlAttribute(tagToken, attrToken);
            sanitizer.validateHtmlAttributeValue(tagToken, attrToken, value);
        } catch (SemanticException e) {
            return false;
        }
        return true;
    }

    private boolean isValidTag(String tagName) {
        try {
            sanitizer.validateHtmlElement(new FakeToken(0, tagName));
        } catch (SemanticException e) {
            return false;
        }
        return true;
    }

    private boolean isValidTag(HtmlTag tag) {
        return isValidTag(tag.getName());
    }

    /*
     * Handling invalid tags by sanitizer  
     * */
    private HtmlTag firstInvalidTag = null;

    private boolean hasInvalidParentTag() {
        return firstInvalidTag != null;
    }

    private void cleanInvalidTag() {
        this.firstInvalidTag = null;
    }

    private void setFirstInvalidTag(HtmlTag firstInvalidTag) {
        this.firstInvalidTag = firstInvalidTag;
    }

    private HtmlTag getFirstInvalidTag() {
        return this.firstInvalidTag;
    }

    /*
    * Getters and Setters
    * */

    public HtmlToSeamTransformer getTransformer() {
        return transformer;
    }

    public void setTransformer(HtmlToSeamTransformer transformer) {
        this.transformer = transformer;
    }
    
}
