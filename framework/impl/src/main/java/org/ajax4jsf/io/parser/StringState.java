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

public class StringState extends ParserState {
	
	protected char[] string;
	
	private ParserState reachedState;
	
	StringState(String toCompare, ParserState reachedState){
		super();
		this.string = toCompare.toCharArray();
		this.reachedState = reachedState;
	}

	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.io.parser.ParserState#getNextState(char, org.ajax4jsf.io.parser.ParsingContext)
	 */
	ParserState getNextState(char c, ParsingContext context) {
		if (context.getCurrentStatePosition()==string.length) {
			// String matched - switch to reached state
			context.setCurrentStatePosition(0);
			return this.reachedState.getNextState(c,context);
		} else if(isAcceptChar(c,context)){
			// Continue reaching.
			return this;
		}
		return super.getNextState(c, context);
	}


	boolean isAcceptChar(char c, ParsingContext context) {
		boolean matched = false;
		int currentStatePosition = context.getCurrentStatePosition();
		if(currentStatePosition<string.length){
			matched = c==string[currentStatePosition];
		}
		if(matched){
			context.incCurrentStatePosition(); 
		}
		else {
			context.setCurrentStatePosition(0);
		}
		return matched;
	}

}
