package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class CleanupManager<K, V> implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(CleanupManager.class);

    private final Thread listenerCleaner;
    private final ReferenceQueue<MyCache<K, V>> referenceQueue;
    private final PhantomReference<MyCache<K, V>> pRef;

    public CleanupManager(MyCache<K, V> referent, Thread listenerCleaner) {
        this.listenerCleaner = listenerCleaner;
        this.referenceQueue = new ReferenceQueue<>();
        this.pRef = new PhantomReference<>(referent, referenceQueue);
    }

    private void stopListenerCleaner() throws InterruptedException {
        logger.info("stopping listenerCleaner thread");
        listenerCleaner.interrupt();
        listenerCleaner.join();
    }

    @Override
    public void run() {
        logger.info("started");
        try {
            var ref = referenceQueue.remove();
            logger.info("ref removed from referenceQueue, ref = {}", ref);
            ref.clear();
            logger.info("ref cleared");
            stopListenerCleaner();
        } catch (InterruptedException e) {
            logger.error("unexpected interrupt", e);
            return;
        }
        logger.info("job done");
    }
}
