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
package org.richfaces.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DialogContext implements Map, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String dialogId;
	private Map map = new HashMap();
	private Map parameters = new HashMap();
	private String dialogPath = null;
	private boolean locked = true;
	
	DialogContext parent = null;
	DialogContext child = null;
	
	public void setDialogId(String s) {
		this.dialogId = s;
	}
	
	public String getDialogId() {
		return dialogId;
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set entrySet() {
		return map.entrySet();
	}

	public Object get(Object key) {
		return findMapForParameter(key).get(key);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set keySet() {
		return map.keySet();
	}

	public Object put(Object key, Object value) {
		if(locked) return findMapForParameter(key).get(key);
		if(value == null) return findMapForParameter(key).remove(key);
		return findMapForParameter(key).put(key, value);
	}

	public void putAll(Map t) {
		if(locked) return;
		map.putAll(t);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection values() {
		return map.values();
	}
	
	public String getDialogPath() {
		return dialogPath;
	}
	
	public void setDialogPath(String dialogPath) {
		this.dialogPath = dialogPath;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean b) {
		locked = b;
	}
	
	public void setParentContext(DialogContext parent) {
		this.parent = parent;
	}
	
	public DialogContext getParentContext() {
		return parent;
	}
	
	public void addChildContext(DialogContext child) {
		this.child = child;
		child.parent = this;
		// hack! common map for all dialogs
		child.map = map;
	}
	
	public void removeChildContext(DialogContext child) {
		if(this.child == child) {
			this.child = null;
			child.parent = null;
		}
	}
	
	public DialogContext getChildContext(int index) {
		return child;
	}
	
	public int getChildContextCount() {
		return child == null ? 0 : 1;
	}
	
	public void removeFromParent() {
		if(parent != null) parent.removeChildContext(this);
	}
	
	public void deactivate() {
		setLocked(true);
        setDialogPath(null);
        if(child != null) {
        	child.deactivate();
        }
        removeFromParent();
	}
	
	public void setParameter(String key, Object value) {
		if(value == null) {
			parameters.remove(key);
		} else {
			if(!(value instanceof Serializable)) {
				value = value.toString();
			}
			parameters.put(key, value);
		}
	}
	
	public Object getParameter(String key) {
		return parameters.get(key);
	}
	
	private Map findMapForParameter(Object key) {
		if(key == null) return map;
		if(parameters.containsKey(key)) return parameters;
		if(parent == null) return map;
		return parent.findMapForParameter(key); 
	}

}
