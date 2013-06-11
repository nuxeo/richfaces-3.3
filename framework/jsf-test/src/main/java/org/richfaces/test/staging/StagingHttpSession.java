/**
 * 
 */
package org.richfaces.test.staging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;

/**
 * @author asmirnov
 * 
 */
@SuppressWarnings("deprecation")
abstract class StagingHttpSession implements HttpSession {

	private static final int DEFAULT_INACTIVE_TIME = 30;

	public static final String SESSION_ID = "1234567890";

	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

	private final long creationTime;

	private volatile int inactiveTime = DEFAULT_INACTIVE_TIME;
	
	private boolean valid;

	public StagingHttpSession() {
		this.creationTime = System.currentTimeMillis();
		this.valid = true;
	}

	/**
	 * 
	 */
	void destroy() {
		// Destroy all session attributes.
		unboundAttributes();
	}

	private void unboundAttributes() {
		for (String name : getValueNames()) {
			removeAttribute(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		checkValid();
		return attributes.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getAttributeNames() {
		checkValid();
		return Collections.enumeration(attributes.keySet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	public long getCreationTime() {
		checkValid();
		return creationTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	public String getId() {
		return SESSION_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		checkValid();
		// TODO fix accessing time
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		return inactiveTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	public HttpSessionContext getSessionContext() {
		throw new NotImplementedException("Session context is not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	public Object getValue(String name) {
		return getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	public String[] getValueNames() {
		checkValid();
		ArrayList<String> names = new ArrayList<String>(attributes.keySet());
		return names.toArray(new String[names.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	public void invalidate() {
		checkValid();
		destroy();
		this.valid=false;

	}

	protected void checkValid(){
		if(!valid){
			throw new IllegalStateException("Session have been invalidated");
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	public boolean isNew() {
		checkValid();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
	 * java.lang.Object)
	 */
	public void putValue(String name, Object value) {
		setAttribute(name, value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		checkValid();
		Object removed = this.attributes.remove(name);
		if (null != removed) {
			removedFromSession(name, removed);
		}

	}

	protected void removedFromSession(String name, Object removed) {
		HttpSessionBindingEvent sessionBindingEvent = new HttpSessionBindingEvent(
				this, name, removed);
		if (removed instanceof HttpSessionBindingListener) {
			HttpSessionBindingListener listener = (HttpSessionBindingListener) removed;
			listener.valueUnbound(sessionBindingEvent);
		}
		valueUnbound(sessionBindingEvent);
	}

	protected abstract void valueUnbound(
			HttpSessionBindingEvent sessionBindingEvent);

	protected void boundToSession(String name, Object value) {
		HttpSessionBindingEvent sessionBindingEvent = new HttpSessionBindingEvent(
				this, name, value);
		if (value instanceof HttpSessionBindingListener) {
			HttpSessionBindingListener listener = (HttpSessionBindingListener) value;
			listener.valueBound(sessionBindingEvent);
		}
		valueBound(sessionBindingEvent);
	}

	protected abstract void valueBound(
			HttpSessionBindingEvent sessionBindingEvent);

	protected void replacedInSession(String name, Object oldValue, Object value) {
		HttpSessionBindingEvent sessionBindingEvent = new HttpSessionBindingEvent(
				this, name, value);
		if (oldValue instanceof HttpSessionBindingListener) {
			HttpSessionBindingListener listener = (HttpSessionBindingListener) oldValue;
			listener.valueUnbound(sessionBindingEvent);
		}
		if (value instanceof HttpSessionBindingListener) {
			HttpSessionBindingListener listener = (HttpSessionBindingListener) value;
			listener.valueBound(sessionBindingEvent);
		}
		valueReplaced(sessionBindingEvent);
	}
	
	protected abstract void valueReplaced(
			HttpSessionBindingEvent sessionBindingEvent);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	public void removeValue(String name) {
		removeAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
	 * java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		checkValid();
		if (null == value) {
			removeAttribute(name);
		} else {
			Object oldValue = attributes.put(name, value);
			if (null != oldValue) {
				replacedInSession(name, oldValue, value);
			} else {
				boundToSession(name, value);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int interval) {
		this.inactiveTime = interval;

	}

}
