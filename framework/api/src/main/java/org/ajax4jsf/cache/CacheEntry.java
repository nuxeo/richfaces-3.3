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
 * CacheEntry
 *
 * @author Brian Goetz
 */
public interface CacheEntry extends Map.Entry {

    int getHits();

    long getLastAccessTime();
    long getLastUpdateTime();
    long getCreationTime();
    long getExpirationTime();

    /**
     * Returns a version counter.
     * An implementation may use timestamps for this or an incrementing
     * number. Timestamps usually have issues with granularity and are harder
     * to use across clusteres or threads, so an incrementing counter is often safer.
     */
    long getVersion();

    boolean isValid();
    long getCost();
}
