/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lolstudio.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@SuppressWarnings("serial")
class MapUtil<K, V> extends HashMap<K, V> {

    /**
     * The underlying map we are managing.
     */
    private Map<K, V> map = null;

    /**
     * Are we currently operating in "fast" mode?
     */
    private boolean fast = false;

    // Constructors
    // ----------------------------------------------------------------------

    /**
     * Construct an empty map.
     */
    public MapUtil() {
        super();
        this.map = createMap();
    }

    /**
     * Construct an empty map with the specified capacity.
     *
     * @param capacity the initial capacity of the empty map
     */
    public MapUtil(int capacity) {
        super();
        this.map = createMap(capacity);
    }

    /**
     * Construct a new map with the same mappings as the specified map.
     *
     * @param map the map whose mappings are to be copied
     */
    public MapUtil(Map<? extends K, ? extends V> map) {
        super();
        this.map = createMap(map);
    }


    // Property access
    // ----------------------------------------------------------------------

    /**
     * Returns true if this map is operating in fast mode.
     *
     * @return true if this map is operating in fast mode
     */
    public boolean getFast() {
        return (this.fast);
    }

    /**
     * Sets whether this map is operating in fast mode.
     *
     * @param fast true if this map should operate in fast mode
     */
    public void setFast(boolean fast) {
        this.fast = fast;
    }


    // Map access
    // ----------------------------------------------------------------------
    // These methods can forward straight to the wrapped Map in 'fast' mode.
    // (because they are query methods)

    /**
     * Compare the specified object with this list for equality.  This
     * implementation uses exactly the code that is used to define the
     * list equals function in the documentation for the
     * <code>Map.equals</code> method.
     *
     * @param o the object to be compared to this list
     * @return true if the two maps are equal
     */
    @Override
    public boolean equals(Object o) {
        // Simple tests that require no synchronization
        if (o == this) {
            return (true);
        } else if (!(o instanceof Map)) {
            return (false);
        }
        Map<?, ?> mo = (Map<?, ?>) o;

        // Compare the two maps for equality
        if (fast) {
            if (mo.size() != map.size()) {
                return (false);
            }
            for (Entry<K, V> e : map.entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                if (value == null) {
                    if (!(mo.get(key) == null && mo.containsKey(key))) {
                        return (false);
                    }
                } else {
                    if (!value.equals(mo.get(key))) {
                        return (false);
                    }
                }
            }
            return (true);

        } else {
            synchronized (map) {
                if (mo.size() != map.size()) {
                    return (false);
                }
                for (Entry<K, V> e : map.entrySet()) {
                    K key = e.getKey();
                    V value = e.getValue();
                    if (value == null) {
                        if (!(mo.get(key) == null && mo.containsKey(key))) {
                            return (false);
                        }
                    } else {
                        if (!value.equals(mo.get(key))) {
                            return (false);
                        }
                    }
                }
                return (true);
            }
        }
    }

    /**
     * Return the hash code value for this map.  This implementation uses
     * exactly the code that is used to define the list hash function in the
     * documentation for the <code>Map.hashCode</code> method.
     *
     * @return suitable integer hash code
     */
    @Override
    public int hashCode() {
        if (fast) {
            int h = 0;
            for (Entry<K, V> e : map.entrySet()) {
                h += e.hashCode();
            }
            return (h);
        } else {
            synchronized (map) {
                int h = 0;
                for (Entry<K, V> e : map.entrySet()) {
                    h += e.hashCode();
                }
                return (h);
            }
        }
    }

    /**
     * Return a shallow copy of this <code>FastHashMap</code> instance.
     * The keys and values themselves are not copied.
     *
     * @return a clone of this map
     */
    @Override
    public Object clone() {
        MapUtil<K, V> results = null;
        if (fast) {
            results = new MapUtil<K, V>(map);
        } else {
            synchronized (map) {
                results = new MapUtil<K, V>(map);
            }
        }
        results.setFast(getFast());
        return (results);
    }

    /**
     * Return <code>true</code> if this map contains no mappings.
     *
     * @return is the map currently empty
     */
    @Override
    public boolean isEmpty() {
        if (fast) {
            return (map.isEmpty());
        } else {
            synchronized (map) {
                return (map.isEmpty());
            }
        }
    }

    /**
     * Return the number of key-value mappings in this map.
     *
     * @return the current size of the map
     */
    @Override
    public int size() {
        if (fast) {
            return (map.size());
        } else {
            synchronized (map) {
                return (map.size());
            }
        }
    }

    // Map modification
    // ----------------------------------------------------------------------
    // These methods perform special behaviour in 'fast' mode.
    // The map is cloned, updated and then assigned back.
    // See the comments at the top as to why this won't always work.

    /**
     * Return the value to which this map maps the specified key.  Returns
     * <code>null</code> if the map contains no mapping for this key, or if
     * there is a mapping with a value of <code>null</code>.  Use the
     * <code>containsKey()</code> method to disambiguate these cases.
     *
     * @param key the key whose value is to be returned
     * @return the value mapped to that key, or null
     */
    @Override
    public V get(Object key) {
        if (fast) {
            return (map.get(key));
        } else {
            synchronized (map) {
                return (map.get(key));
            }
        }
    }

    /**
     * Return <code>true</code> if this map contains a mapping for the
     * specified key.
     *
     * @param key the key to be searched for
     * @return true if the map contains the key
     */
    @Override
    public boolean containsKey(Object key) {
        if (fast) {
            return (map.containsKey(key));
        } else {
            synchronized (map) {
                return (map.containsKey(key));
            }
        }
    }

    /**
     * Return <code>true</code> if this map contains one or more keys mapping
     * to the specified value.
     *
     * @param value the value to be searched for
     * @return true if the map contains the value
     */
    @Override
    public boolean containsValue(Object value) {
        if (fast) {
            return (map.containsValue(value));
        } else {
            synchronized (map) {
                return (map.containsValue(value));
            }
        }
    }

    /**
     * Associate the specified value with the specified key in this map.
     * If the map previously contained a mapping for this key, the old
     * value is replaced and returned.
     *
     * @param key   the key with which the value is to be associated
     * @param value the value to be associated with this key
     * @return the value previously mapped to the key, or null
     */
    @Override
    public V put(K key, V value) {
        if (fast) {
            synchronized (this) {
                Map<K, V> temp = cloneMap(map);
                V result = temp.put(key, value);
                map = temp;
                return (result);
            }
        } else {
            synchronized (map) {
                return (map.put(key, value));
            }
        }
    }

    // Basic object methods
    // ----------------------------------------------------------------------

    /**
     * Copy all of the mappings from the specified map to this one, replacing
     * any mappings with the same keys.
     *
     * @param in the map whose mappings are to be copied
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> in) {
        if (fast) {
            synchronized (this) {
                Map<K, V> temp = cloneMap(map);
                temp.putAll(in);
                map = temp;
            }
        } else {
            synchronized (map) {
                map.putAll(in);
            }
        }
    }

    /**
     * Remove any mapping for this key, and return any previously
     * mapped value.
     *
     * @param key the key whose mapping is to be removed
     * @return the value removed, or null
     */
    @Override
    public V remove(Object key) {
        if (fast) {
            synchronized (this) {
                Map<K, V> temp = cloneMap(map);
                V result = temp.remove(key);
                map = temp;
                return (result);
            }
        } else {
            synchronized (map) {
                return (map.remove(key));
            }
        }
    }

    /**
     * Remove all mappings from this map.
     */
    @Override
    public void clear() {
        if (fast) {
            synchronized (this) {
                map = createMap();
            }
        } else {
            synchronized (map) {
                map.clear();
            }
        }
    }



    protected Map<K, V> createMap() {
        return new WeakHashMap<K, V>();
    }

    protected Map<K, V> createMap(int capacity) {
        return new WeakHashMap<K, V>(capacity);
    }

    protected Map<K, V> createMap(int capacity, float factor) {
        return new WeakHashMap<K, V>(capacity, factor);
    }

    protected Map<K, V> createMap(Map<? extends K, ? extends V> map) {
        return new WeakHashMap<K, V>(map);
    }

    protected Map<K, V> cloneMap(Map<? extends K, ? extends V> map) {
        return createMap(map);
    }

}
