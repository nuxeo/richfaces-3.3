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
package org.richfaces.ui.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ajax4jsf.model.KeepAlive;

/**
 * @author asmirnov
 * 
 */
@KeepAlive
public class States implements State, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8593398262385876975L;
	
	private static final String DEFAULT_STATE = "DEFAULT";

	private final Map<String, State> states;

	private State currentState;
	
	private String currentStateName;

	public States() {
		states = new HashMap<String, State>();
	}
	
	/**
	 * HACK - bean property setter for a initialisation from faces-config.xml.
	 * @param stateConfig
	 */
	public void setConfig(String stateConfig){
		// TODO - parse configuration.
	}
	
	/**
	 * Copy all states from an initial state configuration. Use to init state bean from faces-config.xml
	 * @param initial
	 */
	public void setStates(States initial){
		this.states.clear();
		this.states.putAll(initial.states);
		this.currentState = initial.currentState;
		this.currentStateName = initial.currentStateName;
	}

	public void setCurrentState(String name) {
		State state = states.get(name);
		if (null == state) {
			state = new StateImpl();
			states.put(name, state);
		}
		currentStateName = name;
		currentState = state;
	}

	public String getCurrentState(){
		return currentStateName;		
	}
	
	public void setState(String name, State state) {
		states.put(name, state);
		currentStateName = name;
		currentState = state;
	}

	public void clear() {
		currentState.clear();
	}

	public boolean containsKey(Object key) {
		return currentState.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return currentState.containsValue(value);
	}

	public Set<Entry<String, Object>> entrySet() {
		return currentState.entrySet();
	}

	public Object get(Object key) {
		return currentState.get(key);
	}

	public String getNavigation(String outcome) {
		return currentState.getNavigation(outcome);
	}

	/**
	 * @param outcome
	 * @param navigation
	 * @see org.richfaces.ui.model.State#setNavigation(java.lang.String, java.lang.String)
	 */
	public void setNavigation(String outcome, String navigation) {
		currentState.setNavigation(outcome, navigation);
	}

	public boolean isEmpty() {
		return currentState.isEmpty();
	}

	public Set<String> keySet() {
		return currentState.keySet();
	}

	public Object put(String key, Object value) {
		return currentState.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		currentState.putAll(t);
	}

	public Object remove(Object key) {
		return currentState.remove(key);
	}

	public int size() {
		return currentState.size();
	}

	public Collection<Object> values() {
		return currentState.values();
	}

}
