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
package org.ajax4jsf.framework.ajax.xmlfilter.nekko;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ajax4jsf.webapp.nekko.HtmlCorrectionFilter;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentFragmentHandler;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.cyberneko.html.HTMLAugmentations;

import junit.framework.TestCase;

/**
 * @author asmirnov
 *
 */
public class HtmlCorrectionFilterTestCase extends TestCase {
    
    private List elements;
    private HtmlCorrectionFilter filter;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
	super.setUp();
	elements = new ArrayList();
	filter = new HtmlCorrectionFilter();
	filter.setDocumentHandler(new TestHandler());
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
	elements = null;
	filter = null;
	super.tearDown();	
    }

    private void startElement(String name){
        QName element = new QName(null,name,name,null);
	XMLAttributes attrs = new XMLAttributesImpl();
	Augmentations augs = new HTMLAugmentations();
	filter.startElement(element, attrs, augs);
    }

    private void endElement(String name){
        QName element = new QName(null,name,name,null);
	Augmentations augs = new HTMLAugmentations();
	filter.endElement(element, augs);
    }

    private void emptyElement(String name){
        QName element = new QName(null,name,name,null);
	XMLAttributes attrs = new XMLAttributesImpl();
	Augmentations augs = new HTMLAugmentations();
	filter.emptyElement(element, attrs, augs);
    }

    
    private void executeSequence(El[] seq){
	for (int i=0; i<seq.length;i++) {
	    El element = seq[i];
	    switch (element.type) {
	    case XNIElement.EMPTY:
		emptyElement(element.name);
		break;
	    case XNIElement.START:
		startElement(element.name);
		break;
	    case XNIElement.END:
		endElement(element.name);
		break;
	    default:
		break;
	    }
	}
    }
    
    
    private void assertSequence(El[] seq){
	for (int i = 0; i < seq.length; i++) {
	    assertFalse("Record less elements then expected",i>=elements.size());
	    El element = (El) seq[i];
	    XNIElement rec = (XNIElement) elements.get(i);
	    assertEquals("at position "+i,element.type, rec.type);
	    assertEquals("at position "+i,element.name, rec.element.localpart);
	}
    }
    /**
     * Test method for {@link org.ajax4jsf.webapp.nekko.HtmlCorrectionFilter#endElement(org.apache.xerces.xni.QName, org.apache.xerces.xni.Augmentations)}.
     */
    public void testEndElementQNameAugmentations() {
	startElement("html");//0
		startElement("body");
			startElement("table");// 2
				emptyElement("thead");
				startElement("tbody"); //4
					startElement("tr");
						startElement("td");//6
						endElement("td");
					endElement("tr");//8
				endElement("tbody");
			endElement("table");//10
		endElement("body");
	endElement("html");//12
	endElement("html");//overflow ?
	
	assertEquals(XNIElement.START, ((XNIElement)elements.get(0)).type);
	assertEquals("html", ((XNIElement)elements.get(0)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(1)).type);
	assertEquals("body", ((XNIElement)elements.get(1)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(2)).type);
	assertEquals("table", ((XNIElement)elements.get(2)).element.rawname);
	assertEquals(XNIElement.EMPTY, ((XNIElement)elements.get(3)).type);
	assertEquals("thead", ((XNIElement)elements.get(3)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(4)).type);
	assertEquals("tbody", ((XNIElement)elements.get(4)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(5)).type);
	assertEquals("tr", ((XNIElement)elements.get(5)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(6)).type);
	assertEquals("td", ((XNIElement)elements.get(6)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(7)).type);
	assertEquals("td", ((XNIElement)elements.get(7)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(8)).type);
	assertEquals("tr", ((XNIElement)elements.get(8)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(9)).type);
	assertEquals("tbody", ((XNIElement)elements.get(9)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(10)).type);
	assertEquals("table", ((XNIElement)elements.get(10)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(11)).type);
	assertEquals("body", ((XNIElement)elements.get(11)).element.rawname);
    }

    public void testInsertTbody() {
	startElement("html");//0
		startElement("body");
			startElement("table");// 2
				emptyElement("thead");
//				startElement("tbody"); //4
					startElement("tr");
						startElement("td");//6
						endElement("td");
					endElement("tr");//8
//				endElement("tbody");
			endElement("table");//10
		endElement("body");
	endElement("html");//12
	endElement("html");//overflow ?
	
	assertEquals(XNIElement.START, ((XNIElement)elements.get(0)).type);
	assertEquals("html", ((XNIElement)elements.get(0)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(1)).type);
	assertEquals("body", ((XNIElement)elements.get(1)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(2)).type);
	assertEquals("table", ((XNIElement)elements.get(2)).element.rawname);
	assertEquals(XNIElement.EMPTY, ((XNIElement)elements.get(3)).type);
	assertEquals("thead", ((XNIElement)elements.get(3)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(4)).type);
	assertEquals("tbody", ((XNIElement)elements.get(4)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(5)).type);
	assertEquals("tr", ((XNIElement)elements.get(5)).element.rawname);
	assertEquals(XNIElement.START, ((XNIElement)elements.get(6)).type);
	assertEquals("td", ((XNIElement)elements.get(6)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(7)).type);
	assertEquals("td", ((XNIElement)elements.get(7)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(8)).type);
	assertEquals("tr", ((XNIElement)elements.get(8)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(9)).type);
	assertEquals("tbody", ((XNIElement)elements.get(9)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(10)).type);
	assertEquals("table", ((XNIElement)elements.get(10)).element.rawname);
	assertEquals(XNIElement.END, ((XNIElement)elements.get(11)).type);
	assertEquals("body", ((XNIElement)elements.get(11)).element.rawname);
    }
    
    
    public void testEnclosedTables() throws Exception {
	El[] exec = {
		new El("html",1),
		new El("table",1),
		 new El("tbody",1),
		  new El("tr",1),
		   new El("td",1),
		    new El("table",1),
		     new El("tr",1),
		      new El("td",1),
		      new El("td",2),
		     new El("tr",2),
		    new El("table",2),
		   new El("td",2),
		  new El("tr",2),
		 new El("tbody",2),
		new El("table",2),
		new El("html",2)
	};
	executeSequence(exec);
	El[] expect = {
		new El("html",1),
		new El("table",1),
		 new El("tbody",1),
		  new El("tr",1),
		   new El("td",1),
		    new El("table",1),
			 new El("tbody",1),
		     new El("tr",1),
		      new El("td",1),
		      new El("td",2),
		     new El("tr",2),
			 new El("tbody",2),
		    new El("table",2),
		   new El("td",2),
		  new El("tr",2),
		 new El("tbody",2),
		new El("table",2),
		new El("html",2)
	};
	assertSequence(expect);
    }

    public void testEnclosedTwoTables() throws Exception {
	El[] exec = {
		new El("html",1),
		new El("table",1),
		 new El("tbody",1),
		  new El("tr",1),
		   new El("td",1),
		    new El("table",1),
		     new El("tr",1),
		      new El("td",1),
			    new El("table",1),
			     new El("tr",1),
			      new El("td",1),
			      new El("td",2),
			     new El("tr",2),
			    new El("table",2),
		      new El("td",2),
		     new El("tr",2),
		    new El("table",2),
		   new El("td",2),
		  new El("tr",2),
		 new El("tbody",2),
		new El("table",2),
		new El("html",2)
	};
	executeSequence(exec);
	El[] expect = {
		new El("html",1),
		new El("table",1),
		 new El("tbody",1),
		  new El("tr",1),
		   new El("td",1),
		    new El("table",1),
			 new El("tbody",1),
		     new El("tr",1),
		      new El("td",1),
			    new El("table",1),
				 new El("tbody",1),
			     new El("tr",1),
			      new El("td",1),
			      new El("td",2),
			     new El("tr",2),
				 new El("tbody",2),
			    new El("table",2),
		      new El("td",2),
		     new El("tr",2),
			 new El("tbody",2),
		    new El("table",2),
		   new El("td",2),
		  new El("tr",2),
		 new El("tbody",2),
		new El("table",2),
		new El("html",2)
	};
	assertSequence(expect);
    }
    /**
     * Test method for {@link org.ajax4jsf.webapp.nekko.HtmlCorrectionFilter#startElement(org.apache.xerces.xni.QName, org.apache.xerces.xni.XMLAttributes, org.apache.xerces.xni.Augmentations)}.
     */
//    public void testResVoid() {
//	fail("Not yet implemented");
//    }

    private class TestHandler implements XMLDocumentHandler{

	public void characters(XMLString text, Augmentations augmentations)
		throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void comment(XMLString text, Augmentations augmentations)
		throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void emptyElement(QName element, XMLAttributes attributes,
		Augmentations augmentations) throws XNIException {
	    elements.add(new XNIElement(element,XNIElement.EMPTY));
	    
	}

	public void endCDATA(Augmentations augmentations) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void endDocumentFragment(Augmentations augmentations)
		throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void endElement(QName element, Augmentations augmentations)
		throws XNIException {
	    elements.add(new XNIElement(element,XNIElement.END));
	    
	}

	public void endGeneralEntity(String name, Augmentations augmentations)
		throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void ignorableWhitespace(XMLString text,
		Augmentations augmentations) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void processingInstruction(String target, XMLString data,
		Augmentations augmentations) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void startCDATA(Augmentations augmentations) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void startDocumentFragment(XMLLocator locator,
		NamespaceContext namespaceContext, Augmentations augmentations)
		throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void startElement(QName element, XMLAttributes attributes,
		Augmentations augmentations) throws XNIException {
	    elements.add(new XNIElement(element,XNIElement.START));
	    
	}

	public void startGeneralEntity(String name,
		XMLResourceIdentifier identifier, String encoding,
		Augmentations augmentations) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void textDecl(String version, String encoding,
		Augmentations augmentations) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void doctypeDecl(String rootElement, String publicId,
		String systemId, Augmentations augs) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void endDocument(Augmentations augs) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public XMLDocumentSource getDocumentSource() {
	    // TODO Auto-generated method stub
	    return null;
	}

	public void setDocumentSource(XMLDocumentSource source) {
	    // TODO Auto-generated method stub
	    
	}

	public void startDocument(XMLLocator locator, String encoding,
		NamespaceContext namespaceContext, Augmentations augs)
		throws XNIException {
	    // TODO Auto-generated method stub
	    
	}

	public void xmlDecl(String version, String encoding, String standalone,
		Augmentations augs) throws XNIException {
	    // TODO Auto-generated method stub
	    
	}
	
    }
    
    private static class XNIElement {
	static final int EMPTY=0;
	static final int START=1;
	static final int END=2;
	QName element;
	int type;
	public XNIElement(QName element, int type) {
	    super();
	    this.element = element;
	    this.type = type;
	}
	
    }
    
    private static class El{
	String name;
	int type;
	/**
	 * @param name
	 * @param type
	 */
	public El(String name, int type) {
	    this.name = name;
	    this.type = type;
	}
	
    }
}
