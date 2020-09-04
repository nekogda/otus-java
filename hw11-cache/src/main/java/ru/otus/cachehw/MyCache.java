package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.ReferenceQueue;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    public static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private final ListenersRefSet<K, V> listeners = new ListenersRefSet<>();
    private final ReferenceQueue<HwListener<K, V>> listenersRefQueue = new ReferenceQueue<>();

    public MyCache() {
        logger.info("constructor called");

        var cleaner = new Thread(new ListenerCleaner<>(listeners, listenersRefQueue));
        cleaner.start();

        var cleanupMgr = new CleanupManager<>(this, cleaner);
        new Thread(cleanupMgr).start();
    }

    @Override
    public void put(K key, V value) {
        logger.debug("putting key={}, value={} to the cache", key, value);
        listeners.notify(key, value, "PUT");
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        logger.debug("removing key={}", key);
        listeners.notify(key, null, "REMOVE");
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        logger.debug("getting key={}", key);
        listeners.notify(key, null, "GET");
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        logger.info("adding listener={}", listener);
        listeners.add(listener, listenersRefQueue);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        logger.info("removing listener={}", listener);
        var old_size = listeners.size();
        listeners.remove(listener);
        logger.info("size before={}, after={}", old_size, listeners.size());
    }
}
