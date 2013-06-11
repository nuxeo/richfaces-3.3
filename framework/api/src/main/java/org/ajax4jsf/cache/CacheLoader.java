/*
 * Copyright [yyyy] [name of copyright owner].
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ajax4jsf.cache;


/**
 * User should implement this CacheLoader interface to
 * provide a loader object to load the objects into cache.
 */
public interface CacheLoader
{
    /**
     * loads an object. Application writers should implement this
     * method to customize the loading of cache object. This method is called
     * by the caching service when the requested object is not in the cache.
     * <P>
     *
     * @param key the key identifying the object being loaded
     *
     * @return The object that is to be stored in the cache.
     * @throws CacheException
     *
     */
    public Object load(Object key, Object context) throws CacheException;

}
