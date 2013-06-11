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

/**
 * Interface for curent parsing state in {@link org.ajax4jsf.io.parser.FastHtmlParser}
 * @author shura
 *
 */
public abstract class ParserState {
	
	/**
	 * Array of states allowed to transition from this state.
	 */
	ParserState nextStates[]={};

	ParserState getNextState(char c,ParsingContext context){
		for (int i = 0; i < nextStates.length; i++) {
			ParserState parserState = nextStates[i];
			if(parserState.isAcceptChar(c,context)){
				return parserState;
			}
		}
		return context.getBaseState();
	}
	
	/**
	 * Ask parser for allowed this state for given char.
	 * @param c
	 * @param context
	 * @return
	 */
	abstract boolean isAcceptChar(char c, ParsingContext context);
	
	/**
	 * Send current char or buffer content to output stream.
	 * @return
	 * @throws IOException 
	 */
	 void send(char c, ParsingContext context) throws IOException{
		 context.send(c);
	 }
	 
}
