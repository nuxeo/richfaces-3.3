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

package org.ajax4jsf.io.parser;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FastParserTest extends TestCase {

	
	/*
	 * Test method for 'org.ajax4jsf.io.parser.FastHtmlParser.parse(Reader, Writer)'
	 */
	public void testParse() throws Exception {

		String toParse="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + 
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
		"<!-- Test for page comment -->\n" + 
//		"<f:view  contentType=\"application/xhtml+xml\" \n" + 
//		"    xmlns:f=\"http://java.sun.com/jsf/core\"\n" + 
//		"	xmlns:h=\"http://java.sun.com/jsf/html\" \n" + 
//		"	xmlns:a4j=\"https://ajax4jsf.dev.java.net/ajax\" >\n" + 
		"<html xmlns=\"http://www.w3.org/1999/xhtml\" >\n" + 
		"<body>";
		String resultString = parseString(toParse);
		int indexOf = resultString.indexOf("<html");
		assertTrue(indexOf>=0);
		int indexOf2 = resultString.indexOf("<head>");
		assertTrue(indexOf2>=0);
		assertTrue(indexOf2>indexOf);
		int indexOf3 = resultString.indexOf("</head>");
		assertTrue(indexOf3>=0);
		assertTrue(indexOf3>indexOf2);
	}

	public void testParseWithHead() throws Exception {

		String toParse="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + 
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
		"<!-- Test for page comment -->\n" + 
		"<html xmlns=\"http://www.w3.org/1999/xhtml\" ><head></head>\n" + 
		"<body>";
		String resultString = parseString(toParse);
		assertTrue(resultString.indexOf("<html")>=0);
		assertTrue(resultString.indexOf("<head>")>0);
		assertTrue(resultString.indexOf("</head>")>0);
		assertTrue(resultString.indexOf("<head>")==resultString.lastIndexOf("<head>"));
		assertTrue(resultString.indexOf("</head>")==resultString.lastIndexOf("</head>"));
	}

	public void testParseWithHeadTitle() throws Exception {

		String toParse="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + 
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
		"<!-- Test for page comment -->\n" + 
		"<html xmlns=\"http://www.w3.org/1999/xhtml\" ><head><title>Title</title></head>\n" + 
		"<body>";
		String resultString = parseString(toParse);
		assertTrue(resultString.indexOf("<html")>=0);
		assertTrue(resultString.indexOf("<head>")>0);
		assertTrue(resultString.indexOf("</head>")>0);
		assertTrue(resultString.indexOf("<script")>0);
		assertTrue(resultString.indexOf("</script>")>0);
		assertTrue(resultString.indexOf("<head>")==resultString.lastIndexOf("<head>"));
		assertTrue(resultString.indexOf("</head>")==resultString.lastIndexOf("</head>"));
		assertTrue(resultString.indexOf("<head>")<resultString.lastIndexOf("<title>"));
		assertTrue(resultString.indexOf("<title>")<resultString.lastIndexOf("<script"));
		assertTrue(resultString.indexOf("<title>")<resultString.lastIndexOf("<link"));
		assertTrue(resultString.indexOf("</head>")>resultString.lastIndexOf("</script"));
		assertTrue(resultString.indexOf("</head>")>resultString.lastIndexOf("<link"));
	}

	public void testParseWithHeadTitleMeta() throws Exception {

		String toParse="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + 
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
		"<!-- Test for page comment -->\n" + 
		"<html xmlns=\"http://www.w3.org/1999/xhtml\" ><head><title>Title</title><meta name='xxx' content='yyy'/> </head>\n" + 
		"<body>";
		String resultString = parseString(toParse);
		assertTrue(resultString.indexOf("<html")>=0);
		assertTrue(resultString.indexOf("<head>")>0);
		assertTrue(resultString.indexOf("</head>")>0);
		assertTrue(resultString.indexOf("<script")>0);
		assertTrue(resultString.indexOf("</script>")>0);
		assertTrue(resultString.indexOf("<head>")==resultString.lastIndexOf("<head>"));
		assertTrue(resultString.indexOf("</head>")==resultString.lastIndexOf("</head>"));
		assertTrue(resultString.indexOf("<head>")<resultString.lastIndexOf("<title>"));
		assertTrue(resultString.indexOf("<title>")<resultString.lastIndexOf("<script"));
		assertTrue(resultString.indexOf("<title>")<resultString.lastIndexOf("<link"));
		assertTrue(resultString.indexOf("</head>")>resultString.lastIndexOf("</script"));
		assertTrue(resultString.indexOf("</head>")>resultString.lastIndexOf("<link"));
		assertTrue(resultString.indexOf("<meta")>resultString.lastIndexOf("</script"));
		assertTrue(resultString.indexOf("<meta")>resultString.lastIndexOf("<link"));
	}

	/**
	 * @param toParse
	 * @return
	 */
	private String parseString(String toParse) throws Exception {
		FastHtmlParser parser = new FastHtmlParser();
		StringReader in = new StringReader(toParse);
		StringWriter out = new StringWriter();
		Document document = DocumentBuilderFactory.newInstance().
			newDocumentBuilder().newDocument();

		Element scriptNode = document.createElement("script");
		scriptNode.setAttribute("src", "/some/script.js");
		
		Element linkNode = document.createElement("link");
		linkNode.setAttribute("href", "/some/script.css");

		parser.setHeadNodes(new Node[] {
			scriptNode, linkNode	
		});
		try {
			parser.parse(in,out);
		} catch (IOException e) {
			assertFalse("Exception in parsing",true);
			e.printStackTrace();
		}
		String resultString = out.toString();
		System.out.println(resultString);
		return resultString;
	}

}
