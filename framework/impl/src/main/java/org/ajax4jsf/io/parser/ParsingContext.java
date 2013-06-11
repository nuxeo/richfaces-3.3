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
import java.io.OutputStream;
import java.io.Writer;
import java.util.Stack;

/**
 * Context to keep current parsing state.
 * @author shura
 *
 */
public class ParsingContext {
	
	private int _currentStatePosition=0;
	
	private StringBuffer buff = new StringBuffer(64);
	
	private ParserState baseState;
	
	private Stack<ParserState> parsersStack = new Stack<ParserState>();
	
	private OutputStream _stream;
	
	private Writer _writer;

    private KeywordState _lastMatched;

	
	/**
	 * @param stream
	 */
	public ParsingContext(OutputStream stream) {
		super();
		// TODO Auto-generated constructor stub
		_stream = stream;
	}

	/**
	 * @param writer
	 */
	public ParsingContext(Writer writer) {
		super();
		// TODO Auto-generated constructor stub
		_writer = writer;
	}

	/**
	 * @return Returns the baseState.
	 */
	public ParserState getBaseState() {
		return baseState;
	}

	/**
	 * @param baseState The baseState to set.
	 */
	public void setBaseState(ParserState baseState) {
		this.baseState = baseState;
	}
	
	public void pushState(ParserState baseState){
		parsersStack.push(this.baseState);
		this.baseState = baseState;
	}
	
	public ParserState popState(){
		baseState = (ParserState) parsersStack.pop();
		return baseState;
	}

	/**
	 * @return Returns the currentStatePosition.
	 */
	public int getCurrentStatePosition() {
		return _currentStatePosition;
	}
	/**
	 * @return Returns the currentStatePosition.
	 */
	public int incCurrentStatePosition() {
		return _currentStatePosition++;
	}

	/**
	 * @param currentStatePosition The currentStatePosition to set.
	 */
	public void setCurrentStatePosition(int currentStatePosition) {
		_currentStatePosition = currentStatePosition;
	}
	
	public void putChar(char c){
		buff.append(c);
	}
	
	private void sendBuffer(Writer out) throws IOException {
		out.write(buff.toString());
		buff.setLength(0);
	}

	private void sendBuffer(OutputStream out) throws IOException {
		for (int i = 0; i < buff.length(); i++) {
			out.write(buff.charAt(i));
		}
		buff.setLength(0);
	}
	
	public void send() throws IOException {
		if(null != _writer){
			sendBuffer(_writer);
		} else {
			sendBuffer(_stream);
		}
	}

	public void send(char c) throws IOException {
		if(buff.length()>0){
			send();
		}
		if(null != _writer){
			_writer.write(c);
		} else {
			_stream.write(c);
		}
	}

    public void setLastMatched(KeywordState state) {
		this._lastMatched = state;
		
	}
    
    public boolean contains(String s) {
    	return buff.indexOf(s) >= 0;
    }
    
    public void insert(String s) {
    	buff.insert(buff.length() - 1, s);
    }
    
    /**
	 * @return Returns the _lastMatched.
	 */
	public KeywordState getLastMatched() {
		return _lastMatched;
	}
}
