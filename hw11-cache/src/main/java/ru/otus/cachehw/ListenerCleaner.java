package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Queue;

public class ListenerCleaner<K, V> implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(ListenerCleaner.class);

    private final Queue<SoftReference<HwListener<K, V>>> listeners;
    private final ReferenceQueue<HwListener<K, V>> referenceQueue;

    public ListenerCleaner(Queue<SoftReference<HwListener<K, V>>> listeners,
                           ReferenceQueue<HwListener<K, V>> referenceQueue) {
        this.listeners = listeners;
        this.referenceQueue = referenceQueue;
    }

    @Override
    public void run() {
        logger.info("started");
        while (true) {
            Reference<? extends HwListener<K, V>> ref;
            try {
                ref = referenceQueue.remove();
                logger.info("ref removed from referenceQueue, ref = {}", ref);
            } catch (InterruptedException e) {
                logger.info("interrupted");
                return;
            }
            listeners.remove(ref);
        }
    }
}
