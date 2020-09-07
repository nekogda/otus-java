package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        MyCache<Integer, Integer> cache = new MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        HwListener<Integer, Integer> listener = new HwListener<Integer, Integer>() {
            @Override
            public void notify(Integer key, Integer value, String action) {
                throw new RuntimeException("AAA");
            }
        };

        HwListener<Integer, Integer> listener2 = new HwListener<Integer, Integer>() {
            @Override
            public void notify(Integer key, Integer value, String action) {
                logger.info("key: {}, value: {}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        cache.addListener(listener2);
        cache.put(1, 1);

        logger.info("getValue: {}", cache.get(1));

        cache.remove(1);
        cache.removeListener(listener);

        cache.get(1);
    }
}
