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

package org.ajax4jsf.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.opensymphony.oscache.base.NeedsRefreshException;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 01.05.2007
 * 
 */
public class OSCacheCache implements Cache {

	private com.opensymphony.oscache.base.Cache cache;
	private CacheLoader cacheLoader;

	public OSCacheCache(com.opensymphony.oscache.base.Cache cache,
			CacheLoader cacheLoader) {
		super();
		this.cache = cache;
		this.cacheLoader = cacheLoader;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#addListener(org.ajax4jsf.resource.cache.CacheListener)
	 */
	public void addListener(CacheListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#clear()
	 */
	public void clear() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#entrySet()
	 */
	public Set<Entry<Object, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#evict()
	 */
	public void evict() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#get(java.lang.Object, java.lang.Object)
	 */
	public Object get(Object key, Object context) throws CacheException {
		String keyString = key.toString();

		try {
			return cache.getFromCache(keyString);
		} catch (NeedsRefreshException e) {
			Object object = cacheLoader.load(key, context);
			try {
				cache.putInCache(keyString, object);
			} catch (Exception e1) {
				cache.cancelUpdate(keyString);
				throw new CacheException(e1.getMessage(), e1);
			}
			return object;
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#getCacheEntry(java.lang.Object)
	 */
	public CacheEntry getCacheEntry(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#isEmpty()
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#keySet()
	 */
	public Set<Object> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#load(java.lang.Object, java.lang.Object)
	 */
	public void load(Object key, Object context) throws CacheException {
		cacheLoader.load(key, context);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#peek(java.lang.Object)
	 */
	public Object peek(Object key) {
		String keyString = key.toString();

		try {
			return cache.getFromCache(keyString);
		} catch (NeedsRefreshException e) {
			cache.cancelUpdate(keyString);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
		cache.putInCache(key.toString(), value);
		return value;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends Object, ? extends Object> t) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#remove(java.lang.Object)
	 */
	public Object remove(Object key) {
		String keyString = key.toString();

		try {
			return this.peek(keyString);
		} finally {
			cache.removeEntry(keyString);
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#removeListener(org.ajax4jsf.resource.cache.CacheListener)
	 */
	public void removeListener(CacheListener listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#size()
	 */
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.Cache#values()
	 */
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
