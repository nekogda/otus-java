package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Objects;

class ListenersRefSet<K, V> {
    public static final Logger logger = LoggerFactory.getLogger(ListenersRefSet.class);
    private final HashMap<Integer, SoftReference<HwListener<K, V>>> hashesListeners = new HashMap<>();
    private final HashMap<SoftReference<HwListener<K, V>>, Integer> listenersHashes = new HashMap<>();

    synchronized void add(HwListener<K, V> listener,
                          ReferenceQueue<HwListener<K, V>> listenersRefQueue) {
        var hash = listener.hashCode();
        var ref = new SoftReference<>(listener, listenersRefQueue);
        hashesListeners.put(hash, ref);
        listenersHashes.put(ref, hash);
    }

    synchronized void remove(HwListener<K, V> listener) {
        var hash = listener.hashCode();
        var ref = hashesListeners.remove(hash);
        if (ref == null) {
            return;
        }
        listenersHashes.remove(ref);
    }

    synchronized void remove(Reference<? extends HwListener<K, V>> ref) {
        var hash = listenersHashes.remove(ref);
        hashesListeners.remove(hash);
    }

    synchronized void notify(K key, V value, String action) {
        logger.debug("notify listeners for key={}, value={}, action={}", key, value, action);
        listenersHashes
                .keySet()
                .stream()
                .map(SoftReference::get)
                .filter(Objects::nonNull)
                .forEach(listener -> listener.notify(key, value, action));
    }

    synchronized int size() {
        return listenersHashes.size();
    }
}
