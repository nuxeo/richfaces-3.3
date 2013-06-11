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

import java.util.Random;

import junit.framework.TestCase;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 02.05.2007
 * 
 */
public class LRUMapCacheThreadedTest extends TestCase {
	private CacheLoader cacheLoader = new CacheLoader() {

		public Object load(Object key, Object context) throws CacheException {
			try {
				Thread.sleep(new Random().nextInt(100) + 1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return key;
		}
	};
	
	private static final int COUNT = 500;
	private static final int PASS_COUNT = 2;
	

	public void testCache() throws Exception {
		long millis = System.currentTimeMillis();
		
		for (int k = 0; k < PASS_COUNT; k++) {
			Cache cache = new LRUMapCache(cacheLoader, COUNT);
			Thread[] threads = new Thread[COUNT];
			
			try {
				for (int i = 0; i < COUNT; i++) {
					try {
					threads[i] = new LRUMapCacheTestThread(cache, new Integer(new Random().nextInt(10)));
					threads[i].start();
					} catch (OutOfMemoryError e) {
						System.out.println("Out of memory pass:"+k+" thread: "+i);
						throw e;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			for (int i = 0; i < COUNT; i++) {
				threads[i].join();
			}
		}

		System.out.println((double) (System.currentTimeMillis() - millis) / PASS_COUNT);
	}
}

class LRUMapCacheTestThread extends Thread {
	private Cache cache;
	private Integer idx;
	
	public LRUMapCacheTestThread(Cache cache, Integer idx) {
		super();
		this.cache = cache;
		this.idx = idx;
	}
	
	public void run() {
		super.run();

		try {
			if (!idx.equals(cache.get(idx, null))) {
				throw new IllegalStateException();
			} else {
			//	System.out.println("TestThread.run(): " + idx);
			}
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}