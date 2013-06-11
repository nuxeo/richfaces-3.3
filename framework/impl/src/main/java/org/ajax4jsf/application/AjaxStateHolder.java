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
package org.ajax4jsf.application;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.ajax4jsf.context.ContextInitParameters;
import org.ajax4jsf.util.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author asmirnov
 * 
 */
public class AjaxStateHolder implements Serializable, StateHolder {

	private static final Log _log = LogFactory.getLog(AjaxStateHolder.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 6414488517358423537L;
	private static final String STATE_HOLDER = AjaxStateHolder.class.getName();

	private final LRUMap<String, LRUMap<String, StateReference>> views;

	private final int numberOfViews;

	private AjaxStateHolder(int capacity, int numberOfViews) {
		views = new LRUMap<String, LRUMap<String, StateReference>>(capacity+1);
		this.numberOfViews = numberOfViews;
	}

	public static StateHolder getInstance(FacesContext context) {
		if (null == context) {
			throw new NullPointerException(
					"FacesContext parameter for get view states object is null");
		}
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(true);
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		if (_log.isDebugEnabled()) {
			_log.debug("Request for a view states holder instance");
		}
		StateHolder instance = (StateHolder) sessionMap.get(STATE_HOLDER);
		if (instance == null) {
			synchronized (session) {
				instance = (StateHolder) sessionMap.get(STATE_HOLDER);
				if (null == instance) {
					// Create and store in session new state holder.
					int numbersOfViewsInSession = ContextInitParameters
							.getNumbersOfViewsInSession(context);
					int numbersOfLogicalViews = ContextInitParameters
							.getNumbersOfLogicalViews(context);
					if (_log.isDebugEnabled()) {
						_log
								.debug("No AjaxStateHolder instance in session, create new for hold "
										+ numbersOfViewsInSession
										+ " viewId and "
										+ numbersOfLogicalViews
										+ " logical views for each");
					}
					instance = new AjaxStateHolder(numbersOfViewsInSession,
							numbersOfLogicalViews);
					sessionMap.put(STATE_HOLDER, instance);
				}
			}
		}

		return instance;
	}

	/**
	 * Updates instance of AjaxStateHolder saved in session in order 
	 * to force replication in clustered environment
	 * 
	 * @param context
	 */
	protected void updateInstance(FacesContext context) {
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(true);
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		synchronized (session) {
			sessionMap.put(STATE_HOLDER, this);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.application.StateHolder#getState(java.lang.String,
	 * java.lang.Object)
	 */
	public Object[] getState(FacesContext context, String viewId, String sequence) {
		if (null == viewId) {
			throw new NullPointerException(
					"viewId parameter for get saved view state is null");
		}
		Object state[] = null;
		synchronized (views) {
			LRUMap<String, StateReference> viewVersions = views.get(viewId);
			if (null != viewVersions) {
				if (null != sequence) {
					StateReference stateReference = viewVersions.get(sequence);
					if (null != stateReference) {
						state = stateReference.getState();
					}
				}
				if (null == state) {
					if (_log.isDebugEnabled()) {
						_log.debug("No saved view state for sequence "
								+ sequence);
					}
					// state = viewVersions.getMostRecent();
				}
			} else if (_log.isDebugEnabled()) {
				_log.debug("No saved view states for viewId " + viewId);
			}
		}
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.application.StateHolder#saveState(java.lang.String,
	 * java.lang.Object, java.lang.Object)
	 */
	public void saveState(FacesContext context, String viewId, String sequence, Object[] state) {
		if (null == viewId) {
			throw new NullPointerException(
					"viewId parameter for  save view state is null");
		}
		if (null == sequence) {
			throw new NullPointerException(
					"sequence parameter for save view state is null");
		}
		if (null != state) {
			if (_log.isDebugEnabled()) {
				_log.debug("Save new viewState in session for viewId " + viewId
						+ " and sequence " + sequence);
			}
			synchronized (views) {
				LRUMap<String, StateReference> viewVersions = views.get(viewId);
				StateReference stateReference = null;
				if (null == viewVersions) {
					// TODO - make size parameter configurable
					viewVersions = new LRUMap<String, StateReference>(
							this.numberOfViews+1);
					views.put(viewId, viewVersions);
					stateReference = new StateReference(state);
					viewVersions.put(sequence, stateReference);						
				} else {
					stateReference = viewVersions.get(sequence);
					if(null == stateReference){
						stateReference = new StateReference(state);
						viewVersions.put(sequence, stateReference);						
					} else {
						stateReference.setState(state);
					}
				}
			}

			//serialization is synchronized in writeObject()
			updateInstance(context);
		}
	}

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {

		synchronized (views) {
			stream.defaultWriteObject();
		}
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {

		stream.defaultReadObject();
	}

	@SuppressWarnings("serial")
	private static class StateReference implements Serializable {
		private Object[] state;

		public Object[] getState() {
			return state;
		}

		public void setState(Object[] state) {
			this.state = state;
		}

		/**
		 * @param state
		 */
		public StateReference(Object[] state) {
			super();
			this.state = state;
		}
	}
}
