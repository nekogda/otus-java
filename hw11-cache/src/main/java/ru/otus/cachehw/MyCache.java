package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    public static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private final Set<HwListener<K, V>> listeners = new HashSet<>();

    public MyCache() {
        logger.info("constructor called");
    }

    private void notifyListeners(K key, V value, String action) {
        logger.debug("notify listeners for key={}, value={}, action={}", key, value, action);
        listeners.forEach((listener) -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                logger.error("exception occurred during notification", e);
            }
        });
    }

    @Override
    public void put(K key, V value) {
        logger.debug("putting key={}, value={} to the cache", key, value);
        notifyListeners(key, value, "PUT");
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        logger.debug("removing key={}", key);
        notifyListeners(key, null, "REMOVE");
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        logger.debug("getting key={}", key);
        notifyListeners(key, null, "GET");
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        logger.info("adding listener={}", listener);
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        logger.info("removing listener={}", listener);
        listeners.remove(listener);
    }
}
