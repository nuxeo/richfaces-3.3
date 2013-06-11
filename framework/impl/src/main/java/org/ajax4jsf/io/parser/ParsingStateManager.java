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


public class ParsingStateManager {
	
	 ParserState DOCUMENT = new AnyCharState();
	
	 ParserState LT = new BufferedSingleCharState('<');

	 ParserState ENDELEMENT = new BufferedSingleCharState('>');

	 ParserState CLOSINGELEMENT = new BufferedSingleCharState('/');

	ParserState EXCLAM = new SingleCharState('!');
	

	ParserState DOCTYPE = new AnyCharState();

	ParserState DOCTYPESTART = new StringState("DOCTYPE",DOCTYPE);

	ParserState DOCTYPEEND = new SingleCharState('>');

	ParserState COMMENT = new AnyCharState();

	ParserState COMMENTSTART = new StringState("--",COMMENT);
	
	
	ParserState COMMENTEND = new StringState("-->",DOCUMENT);

	ParserState CDATA = new AnyCharState();

	ParserState CDATASTART = new StringState("[CDATA[",CDATA);
	
	ParserState CDATAEND = new StringState("]]>",DOCUMENT);

	ParserState PISTART = new SingleCharState('?');
	
	ParserState PI = new AnyCharState();

	ParserState PIEND = new StringState("?>",DOCUMENT);
	
	ParserState ELEMENT = new XmlIdentifierState();
	
	ParserState INELEMENT = new BufferedBlankState();

	ParserState INELEMENTCONTENT = new BufferedAnyCharState();

	ParserState LT_ELEMENT = new BufferedSingleCharState('<');
	
	ParserState EXCLAM_ELEMENT = new BufferedSingleCharState('!');
	

	ParserState COMMENT_ELEMENT = new BufferedAnyCharState();
	
	ParserState COMMENTSTART_ELEMENT = new BufferedStringState("--",COMMENT_ELEMENT);
	
	ParserState COMMENTEND_ELEMENT = new BufferedStringState("-->",INELEMENTCONTENT);

	ParserState CDATA_ELEMENT = new BufferedAnyCharState();

	ParserState CDATASTART_ELEMENT = new BufferedStringState("[CDATA[",CDATA_ELEMENT);
	
	ParserState CDATAEND_ELEMENT = new BufferedStringState("]]>",INELEMENTCONTENT);

	ParserState PISTART_ELEMENT = new BufferedSingleCharState('?');
	
	ParserState PI_ELEMENT = new BufferedAnyCharState();

	ParserState PIEND_ELEMENT = new BufferedStringState("?>",INELEMENTCONTENT);
	
    ParserState H = new KeywordCharState('h');
	 
	// Head stuff
	
	ParserState HEAD = new KeywordState("ead",ELEMENT);

	ParserState TITLE = new KeywordState("title",ELEMENT);

	ParserState BASE = new KeywordState("base",ELEMENT);
	
	// Html content

	ParserState HTML = new KeywordState("tml",ELEMENT);

	private static ParsingStateManager _instance=new ParsingStateManager();
	/**
	 * 
	 */
	private ParsingStateManager() {
		// Inicialize parsing states
		DOCUMENT.nextStates = new ParserState[]{LT,DOCUMENT};
		// after < possible ! & / or start element 
		LT.nextStates = new ParserState[]{EXCLAM,PISTART,CLOSINGELEMENT,H,TITLE,BASE,ELEMENT,DOCUMENT};
		//
		EXCLAM.nextStates = new ParserState[]{COMMENTSTART,CDATASTART,DOCTYPESTART,DOCUMENT};
		// doctype declaration
		DOCTYPESTART.nextStates = new ParserState[]{DOCTYPESTART,DOCUMENT};
		DOCTYPE.nextStates = new ParserState[]{DOCTYPEEND,DOCTYPE};
		DOCTYPEEND.nextStates = new ParserState[]{LT,DOCUMENT};
		// cdata section
		CDATASTART.nextStates = new ParserState[]{LT,DOCUMENT};
		CDATA.nextStates = new ParserState[]{CDATAEND,CDATA};
		CDATAEND.nextStates = new ParserState[]{CDATA};
		//
		COMMENTSTART.nextStates = new ParserState[]{LT,DOCUMENT};
		COMMENT.nextStates = new ParserState[]{COMMENTEND,COMMENT};
		COMMENTEND.nextStates = new ParserState[]{COMMENT};
		//
		PISTART.nextStates = new ParserState[]{PIEND,PI};
		PI.nextStates = new ParserState[]{PIEND,PI};
		PIEND.nextStates = new ParserState[]{PI};
		// Any element or closing element.
		CLOSINGELEMENT.nextStates = new ParserState[]{H,TITLE,BASE,ELEMENT,LT,DOCUMENT};
		ELEMENT.nextStates = new ParserState[]{ELEMENT,INELEMENT,ENDELEMENT,LT_ELEMENT};
		INELEMENT.nextStates = new ParserState[]{ENDELEMENT,INELEMENT,LT_ELEMENT,INELEMENTCONTENT};
		INELEMENTCONTENT.nextStates = new ParserState[]{LT_ELEMENT,ENDELEMENT,INELEMENTCONTENT};
		LT_ELEMENT.nextStates = new ParserState[]{EXCLAM_ELEMENT,PISTART_ELEMENT,INELEMENTCONTENT};
		//
		EXCLAM_ELEMENT.nextStates = new ParserState[]{COMMENTSTART_ELEMENT,CDATASTART_ELEMENT,INELEMENTCONTENT};
		// cdata section
		CDATASTART_ELEMENT.nextStates = new ParserState[]{CDATASTART_ELEMENT,INELEMENTCONTENT};
		CDATA_ELEMENT.nextStates = new ParserState[]{CDATAEND_ELEMENT,CDATA_ELEMENT};
		CDATAEND_ELEMENT.nextStates = new ParserState[]{CDATAEND_ELEMENT,CDATA_ELEMENT};
		//
		COMMENTSTART_ELEMENT.nextStates = new ParserState[]{COMMENTEND_ELEMENT,COMMENT_ELEMENT,INELEMENTCONTENT};
		COMMENT_ELEMENT.nextStates = new ParserState[]{COMMENTEND_ELEMENT,COMMENT_ELEMENT};
		COMMENTEND_ELEMENT.nextStates = new ParserState[]{COMMENTEND_ELEMENT,COMMENT_ELEMENT};
		//
		PISTART_ELEMENT.nextStates = new ParserState[]{PIEND_ELEMENT,PI_ELEMENT,INELEMENTCONTENT};
		PI_ELEMENT.nextStates = new ParserState[]{PIEND_ELEMENT,PI_ELEMENT};
		PIEND_ELEMENT.nextStates = new ParserState[]{PIEND_ELEMENT,PI_ELEMENT};
		
		ENDELEMENT.nextStates = new ParserState[]{LT,DOCUMENT};

		H.nextStates = new ParserState[]{HEAD,HTML,ENDELEMENT,INELEMENT,ELEMENT,LT,DOCUMENT};
		HEAD.nextStates = new ParserState[]{HEAD,ENDELEMENT,INELEMENT,ELEMENT};
		HTML.nextStates = new ParserState[]{HTML,ENDELEMENT,INELEMENT,ELEMENT};
		TITLE.nextStates = new ParserState[]{TITLE,ENDELEMENT,INELEMENT,ELEMENT};
		BASE.nextStates = new ParserState[]{BASE,ENDELEMENT,INELEMENT,ELEMENT};
	}
	/**
	 * @return Returns the instance.
	 */
	public static ParsingStateManager getInstance() {
		return _instance;
//		return new ParsingStateManager();
	}
	
	public ParserState getInitialState(){
		return DOCUMENT;
	}
	

}
