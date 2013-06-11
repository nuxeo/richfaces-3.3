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

package org.ajax4jsf.gwt.client.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shura
 *
 */
public class MessageBundle {

	private Map _messages ;
	/**
	 * 
	 */
	public MessageBundle() {
		_messages = new HashMap();
	}
	
	public MessageBundle(String[][] defaultMessages){
		this();
	}
	
	public void setDefault(String[][] defaultMessages){
		for (int i = 0; i < defaultMessages.length; i++) {
			String[] message = defaultMessages[i];
			_messages.put(message[0],message[1]);
		}
	}
	
	public void setMessages(Map messages){
		_messages.putAll(messages);
	}
	
	/**
	 * Get localised message for given key. If message not present, return
	 * key string.
	 * @param key
	 * @return
	 */
	public String getMessage(String key){
		String msg = (String) _messages.get(key);
		if(null == msg){
			msg=key;
		}
		return msg;
	}
	
	/**
	 * Format message for given key . Works like {@link java.text.MessageFormat}, but
	 * very simple - only support {n} placeholders, and only with same order as arguments.
	 * @param key
	 * @param args
	 * @return
	 */
	public String formatMessage(String key, String[] args){
		String message = getMessage(key);
		String[] strings = message.split("\\{.?\\}");
		String result = "";
		for (int i = 0; i < strings.length; i++) {
			result = result + strings[i];
			if(i < args.length ){
				result = result + args[i];
			}
		}
		return result;
	}

}
