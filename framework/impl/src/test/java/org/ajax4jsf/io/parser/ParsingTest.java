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
import java.io.StringWriter;

import junit.framework.TestCase;

public class ParsingTest extends TestCase {
	
	ParserState initialState;
	ParsingStateManager instance;
	StringWriter out;

	protected void setUp() throws Exception {
		super.setUp();
        instance = ParsingStateManager.getInstance();
		initialState = instance.getInitialState();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected ParserState parseString(String toParse) {
		char[] buff = toParse.toCharArray();
        out = new StringWriter();
		ParsingContext context = new ParsingContext(out);
		context.setBaseState(initialState);
		ParserState state = initialState;
		for (int i = 0; i < buff.length; i++) {
			char c = buff[i];
			state = state.getNextState(c,context);
			try {
				state.send(c,context);
			} catch (IOException e) {
				assertTrue("Exception on send char",false);
				e.printStackTrace();
			}
		}
		try {
			context.send();
		} catch (IOException e) {
			assertTrue("Exception on send char",false);
			e.printStackTrace();
		}
		return state;
	}
	/*
	 * Test method for 'org.ajax4jsf.io.parser.ParsingStateManager.getInitialState()'
	 */
	public void testGetInitialState() {
		assertSame(initialState,instance.DOCUMENT);
	}
	
	public void testParseLt(){
		ParserState state = parseString(" <");
		assertSame(state,instance.LT);
		state = parseString(" < ");
		assertSame(state,instance.DOCUMENT);
	}

	public void testParsePI(){
		ParserState state = parseString("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		assertSame(state,instance.PIEND);
		state = parseString("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ");
		assertSame(state,instance.DOCUMENT);
		state = parseString("<?xml version=\"1.0\" encoding=\"UTF-8\" ? ");
		assertSame(state,instance.PI);
	}

	public void testParseDOCTYPE(){
		ParserState state = parseString("<!DOC ");
		assertSame(state,instance.DOCUMENT);
		state = parseString("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		assertSame(state,instance.DOCTYPEEND);
		state = parseString("<!DOCTYPE html <");
		assertSame(state,instance.DOCTYPE);
		state = parseString("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> ");
		assertSame(state,instance.DOCUMENT);
	}

	public void testParseCDATA(){
		ParserState state = parseString("<![CDATA []]>");
		assertSame(state,instance.DOCUMENT);
		state = parseString("<![CDATA[]]>");
		assertSame(state,instance.CDATAEND);
		state = parseString("<![CDATA[ ] ]>");
		assertSame(state,instance.CDATA);
		state = parseString("<![CDATA[<?xml <!DOCTYPE html  ]]> ");
		assertSame(state,instance.DOCUMENT);
	}

	public void testParseComment(){
		ParserState state = parseString("<!-- -->");
		assertSame(state,instance.COMMENTEND);
		state = parseString("<!-- --> ");
		assertSame(state,instance.DOCUMENT);
		state = parseString("<!-- -- >");
		assertSame(state,instance.COMMENT);
		state = parseString("<!- - -->");
		assertSame(state,instance.DOCUMENT);
	}

	public void testParseElement(){
		ParserState state = parseString("<foo -->");
		assertSame(state,instance.ENDELEMENT);
		state = parseString("<foo   ");
		assertSame(state,instance.INELEMENT);
		state = parseString("<foo --");
		assertSame(state,instance.INELEMENTCONTENT);
		state = parseString("<foo x='<!-- ");
		assertSame(state,instance.COMMENT_ELEMENT);
		state = parseString("<foo x='<![CDATA[ ] ]> ");
		assertSame(state,instance.CDATA_ELEMENT);
		state = parseString("<foo x='<![CDATA[ ]]>' <!-- --> >");
		assertSame(state,instance.ENDELEMENT);
	}

	public void testParseHtml0(){
		ParserState state = parseString("<html -->");
		assertSame(state,instance.ENDELEMENT);
	}
		public void testParseHtml1(){
		ParserState state = parseString("<htmlfo");
		assertSame(state,instance.ELEMENT);
		}
		public void testParseHtml2(){
		ParserState state = parseString("<htm");
		assertSame(state,instance.HTML);
		}
		public void testParseHtml3(){
		ParserState state = parseString("<html x='<!-- ");
		assertSame(state,instance.COMMENT_ELEMENT);
		}
		public void testParseHtml4(){
		ParserState state = parseString("<html x='<![CDATA[ ] ]> ");
		assertSame(state,instance.CDATA_ELEMENT);
		}
		public void testParseHtml5(){
		ParserState state = parseString("<html x='<![CDATA[ ]]>' <!-- --> >");
		assertSame(state,instance.ENDELEMENT);
	}
	
		public void testParseHtml6(){
			ParserState state = parseString("<html --><");
			assertSame(state,instance.LT);
		}
		
		
	public void testSendParsed(){
		String toParse;
		ParserState state;
		toParse="xxx";
		state = parseString(toParse);
		assertEquals(toParse,out.toString());
		toParse="<xxx";
		state = parseString(toParse);
		assertEquals(toParse,out.toString());
		assertSame(state, instance.ELEMENT);
		toParse="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + 
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
				"<!-- Test for page comment -->\n" + 
				"<f:view  contentType=\"application/xhtml+xml\" \n" + 
				"    xmlns:f=\"http://java.sun.com/jsf/core\"\n" + 
				"	xmlns:h=\"http://java.sun.com/jsf/html\" \n" + 
				"	xmlns:a4j=\"https://ajax4jsf.dev.java.net/ajax\" >\n" + 
				"<html xmlns=\"http://www.w3.org/1999/xhtml\" >\n" + 
				"<body>";
		state = parseString(toParse);
		assertEquals(toParse,out.toString());
	}
}
