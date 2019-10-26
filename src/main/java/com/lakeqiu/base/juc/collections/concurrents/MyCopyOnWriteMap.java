package com.lakeqiu.base.juc.collections.concurrents;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lakeqiu
 */
public class MyCopyOnWriteMap<K, V> implements Map<K, V> {
    private final ReentrantLock lock;
    private volatile Map<K, V> map;

    public MyCopyOnWriteMap() {
        this.map = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        V val = null;
        lock.lock();
        try {
            HashMap<K, V> newMap = new HashMap<>(map);
            val = newMap.put(key, value);
            map = newMap;
        } finally {
            lock.unlock();
        }
        return val;
    }

    @Override
    public V remove(Object key) {
        V val = null;
        lock.lock();
        try {
            HashMap<K, V> newMap = new HashMap<>(map);
            val = newMap.remove(key);
            map = newMap;
        }finally {
            lock.unlock();
        }
        return val;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        lock.lock();
        try {
            HashMap<K, V> newMap = new HashMap<>(map);
            newMap.putAll(m);
            map = newMap;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public static void main(String[] args) throws InterruptedException {
        MyCopyOnWriteMap<Integer, Integer> map = new MyCopyOnWriteMap<>();
        new Thread(() -> {
            map.put(1, 2);
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            System.out.println(map.remove(1));
        }).start();

    }
}
