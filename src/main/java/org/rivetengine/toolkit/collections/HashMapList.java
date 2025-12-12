package org.rivetengine.toolkit.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapList<K, V> extends HashMap<K, List<V>> {
    public void add(K key, V value) {
        List<V> list = get(key);
        if (list == null) {
            list = new ArrayList<>();
            put(key, list);
        }
        list.add(value);
    }

    public boolean remove(Object key, Object value) {
        List<V> list = get(key);
        if (list != null) {
            return list.remove(value);
        }
        return false;
    }

    public void remove(K key, int index) {
        List<V> list = get(key);
        if (list != null) {
            list.remove(index);
        }
    }

    public V get(K key, int index) {
        List<V> list = get(key);
        if (list != null) {
            return list.get(index);
        }
        return null;
    }

    public boolean contains(K key, V value) {
        List<V> list = get(key);
        if (list != null) {
            return list.contains(value);
        }
        return false;
    }

    public List<V> getList(K key) {
        return get(key);
    }

    public void setList(K key, List<V> list) {
        put(key, list);
    }

    public void clearList(K key) {
        List<V> list = get(key);
        if (list != null) {
            list.clear();
        }
    }

    public void clearAll() {
        clear();
    }

    public boolean isEmpty(K key) {
        List<V> list = get(key);
        if (list != null) {
            return list.isEmpty();
        }
        return true;
    }

    public int size(K key) {
        List<V> list = get(key);
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public int sizeAll() {
        int size = 0;
        for (List<V> list : values()) {
            size += list.size();
        }
        return size;
    }
}
