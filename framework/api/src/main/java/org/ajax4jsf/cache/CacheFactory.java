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

import java.util.Map;

/**
 * CacheFactory is a service provider specific interface.
 * Service provider should implement CacheFactory to provide
 * the functionality to create a new implementation specific Cache object.
 */
public interface CacheFactory
{
    /**
     * creates a new implementation specific Cache object using the env parameters.
     * @param env implementation specific environment parameters passed to the
     * CacheFactory.
     * @param cacheLoader implementation of the {@link CacheLoader} to use
     * @param cacheConfigurationloader TODO
     * @return an implementation specific Cache object.
     * @throws CacheException if any error occurs.
     */
    public Cache createCache(Map env, CacheLoader cacheLoader, CacheConfigurationLoader cacheConfigurationloader) throws CacheException;
}
