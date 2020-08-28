package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Objects;
import java.util.Queue;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyCache<K, V> implements HwCache<K, V> {
    public static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private Queue<SoftReference<HwListener<K, V>>> listeners = new ConcurrentLinkedQueue<>();
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
        listeners.add(new SoftReference<>(listener, listenersRefQueue));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        logger.info("removing listener={}", listener);
        var old_size = listeners.size();
        listeners = listeners.stream()
                .map(SoftReference::get)
                .filter(Objects::nonNull)
                .filter(Predicate.not(listener::equals))
                .map(SoftReference::new).collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
        logger.info("size before={}, after={}", old_size, listeners.size());
    }

    private void notifyListeners(K key, V value, String action) {
        logger.debug("notify listeners for key={}, value={}, action={}", key, value, action);
        listeners.stream()
                .map(SoftReference::get)
                .filter(Objects::nonNull)
                .forEach(listener -> listener.notify(key, value, action));
    }
}
