package hw04.gc.metrics;

import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanServer;
import javax.management.NotificationListener;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.*;

public class MetricCollector {
    private final MetricConsumer<GcLogRecord> consumer;
    private Future<?> consumerFuture;

    public MetricCollector(MetricConsumer<GcLogRecord> consumer) {
        this.consumer = consumer;
    }

    public void start() throws InstanceNotFoundException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        for (var bean : ManagementFactory.getGarbageCollectorMXBeans()) {
            var listener = getBeanListener(bean);
            mbs.addNotificationListener(bean.getObjectName(), listener, new GcEventFilter(), null);
        }
        var executor = Executors.newSingleThreadExecutor();
        consumerFuture = executor.submit(consumer);
    }

    public void stop() throws ExecutionException, InterruptedException {
        if (consumerFuture == null) {
            throw new IllegalStateException("you must call start first");
        }

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        for (var bean : ManagementFactory.getGarbageCollectorMXBeans()) {
            var listener = getBeanListener(bean);
            try {
                mbs.removeNotificationListener(bean.getObjectName(), listener);
            } catch (InstanceNotFoundException | ListenerNotFoundException e) {
                continue;
            }
        }

        if (consumerFuture.isDone()) {
            consumerFuture.get();
            return;
        }

        if (consumerFuture.cancel(true)) {
            try {
                consumerFuture.get();
            } catch (InterruptedException | CancellationException e) {
                return;
            }
        }
        throw new IllegalThreadStateException("can't stop MetricConsumer thread");
    }

    private NotificationListener getBeanListener(GarbageCollectorMXBean bean) {
        switch (bean.getObjectName().getCanonicalName()) {
            case "java.lang:name=PS MarkSweep,type=GarbageCollector":
            case "java.lang:name=PS Scavenge,type=GarbageCollector":
            case "java.lang:name=Copy,type=GarbageCollector":
            case "java.lang:name=MarkSweepCompact,type=GarbageCollector":
            case "java.lang:name=G1 Young Generation,type=GarbageCollector":
            case "java.lang:name=G1 Old Generation,type=GarbageCollector":
                return new JMXGCListener(consumer);
            default:
                throw new IllegalStateException("Unexpected value: " + bean.getObjectName().getCanonicalName());
        }
    }
}
