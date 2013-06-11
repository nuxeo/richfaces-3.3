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

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 01.05.2007
 * 
 */
public class OSCacheCacheFactory implements CacheFactory {

    private static final Log log = LogFactory.getLog(OSCacheCacheFactory.class);

    /* (non-Javadoc)
	 * @see org.ajax4jsf.resource.cache.CacheFactory#createCache(java.util.Map, org.ajax4jsf.resource.cache.CacheLoader)
	 */
	public Cache createCache(Map env, CacheLoader cacheLoader, CacheConfigurationLoader cacheConfigurationloader)
			throws CacheException {
	    // Load our implementation properties
	    Properties cacheProperties = cacheConfigurationloader.loadProperties("oscache.properties");
	    cacheProperties.putAll(cacheConfigurationloader.loadProperties("/oscache.properties"));
	    cacheProperties.putAll(env);
	    
	    log.info("Creating OSCache cache instance using parameters: " + cacheProperties);	    
	    GeneralCacheAdministrator cacheAdministrator = new GeneralCacheAdministrator(cacheProperties);
		return new OSCacheCache(cacheAdministrator.getCache(), cacheLoader);
	}

}
