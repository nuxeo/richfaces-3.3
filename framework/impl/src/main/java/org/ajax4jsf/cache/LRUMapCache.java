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

import java.util.HashMap;
import java.util.Map;

import org.ajax4jsf.util.LRUMap;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 01.05.2007
 * 
 */
public class LRUMapCache extends LRUMap<Object, Object> implements Cache {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3236528957956574490L;
	private CacheLoader cacheLoader;

	public LRUMapCache(CacheLoader cacheLoader, int initialSize) {
		super(initialSize);
		this.cacheLoader = cacheLoader;
	}

	public LRUMapCache(CacheLoader cacheLoader) {
		super();
		this.cacheLoader = cacheLoader;
	}

	public void addListener(CacheListener listener) {
		// TODO Auto-generated method stub

	}

	public void evict() {
		// TODO Auto-generated method stub

	}

	private Map<Object, LRUMapFuture> futures = new HashMap<Object, LRUMapFuture>();

	public synchronized Object get(Object key, Object context) throws CacheException {

		try {
			LRUMapFuture activeFuture = null;
			LRUMapFuture future = null;

			synchronized (futures) {
				future = (LRUMapFuture) futures.get(key);
				if (future == null) {
					activeFuture = new LRUMapFuture();
					futures.put(key, activeFuture);
				}

			}

			if (future != null) {
				synchronized (future) {
					future.wait();
					return future.getResult();
				}
			} else {
				try {
					if (!containsKey(key)) {
						load(key, context);
					}

					Object result = peek(key);
					activeFuture.setResult(result);

					return result;
				} finally {
					synchronized (futures) {
						this.futures.remove(key);

						synchronized (activeFuture) {
							activeFuture.notifyAll();
						}
					}
				}
			}

		} catch (InterruptedException e) {
			throw new CacheException(e.getMessage(), e);
		}
	}

	public CacheEntry getCacheEntry(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized void load(Object key, Object context) throws CacheException {
		put(key, cacheLoader.load(key, context));
	}

	public synchronized Object peek(Object key) {
		return this.get(key);
	}

	public void removeListener(CacheListener listener) {
		// TODO Auto-generated method stub

	}

}

class LRUMapFuture {
	private Object result;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}