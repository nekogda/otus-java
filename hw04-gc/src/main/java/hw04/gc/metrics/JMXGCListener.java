package hw04.gc.metrics;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.UnixOperatingSystemMXBean;

import javax.management.Notification;
import javax.management.openmbean.CompositeData;
import java.lang.management.ManagementFactory;

class JMXGCListener implements javax.management.NotificationListener {
    private final MetricConsumer<GcLogRecord> consumer;
    private final UnixOperatingSystemMXBean osBean;

    JMXGCListener(MetricConsumer<GcLogRecord> consumer) {
        this.osBean = (UnixOperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        this.consumer = consumer;
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        String notificationType = notification.getType();
        if (notificationType.equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            // retrieve the garbage collection notification information
            CompositeData cd = (CompositeData) notification.getUserData();
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from(cd);

            var gcUsageInfo = GCUsageInfo.collectGCUsageInfo(info);
            var record = new GcLogRecord(
                    notification.getTimeStamp(),
                    gcUsageInfo.getGcType(),
                    info.getGcInfo().getDuration(),
                    gcUsageInfo.getEdenUsedBefore(),
                    gcUsageInfo.getEdenUsedAfter(),
                    gcUsageInfo.getSurvivorUsedBefore(),
                    gcUsageInfo.getSurvivorUsedAfter(),
                    gcUsageInfo.getTuneredUsedBefore(),
                    gcUsageInfo.getTuneredUsedAfter(),
                    osBean.getProcessCpuTime()
            );
            consumer.accept(record);
        }
    }

    private static class GCUsageInfo {
        private final GarbageCollectionNotificationInfo info;
        private final String edenName;
        private final String survivorName;
        private final String tenuredName;

        GCUsageInfo(GarbageCollectionNotificationInfo info, String edenName, String survivorName, String tenuredName) {
            this.info = info;
            this.edenName = edenName;
            this.survivorName = survivorName;
            this.tenuredName = tenuredName;
        }

        static GCUsageInfo collectGCUsageInfo(GarbageCollectionNotificationInfo info) {
            String edenName;
            String survivorName;
            String tenuredName;

            switch (info.getGcName()) {
                case "G1 Old Generation":
                case "G1 Young Generation":
                    edenName = "G1 Eden Space";
                    survivorName = "G1 Survivor Space";
                    tenuredName = "G1 Old Gen";
                    break;
                case "PS MarkSweep":
                case "PS Scavenge":
                    edenName = "PS Eden Space";
                    survivorName = "PS Survivor Space";
                    tenuredName = "PS Old Gen";
                    break;
                case "MarkSweepCompact":
                case "Copy":
                    edenName = "Eden Space";
                    survivorName = "Survivor Space";
                    tenuredName = "Tenured Gen";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + info.getGcName());
            }
            return new GCUsageInfo(info, edenName, survivorName, tenuredName);
        }

        public GcType getGcType() {
            if (info.getGcAction().contains("major")) {
                return GcType.Major;
            } else if (info.getGcAction().contains("minor")) {
                return GcType.Minor;
            }
            throw new IllegalStateException("Unexpected value: " + info.getGcAction());
        }

        public long getEdenUsedBefore() {
            var mu = info.getGcInfo().getMemoryUsageBeforeGc();
            if (!mu.containsKey(edenName)) {
                throw new IllegalStateException("expected key: " + edenName);
            }
            return mu.get(edenName).getUsed();
        }

        public long getEdenUsedAfter() {
            var mu = info.getGcInfo().getMemoryUsageAfterGc();
            if (!mu.containsKey(edenName)) {
                throw new IllegalStateException("expected key: " + edenName);
            }
            return mu.get(edenName).getUsed();
        }

        public long getSurvivorUsedBefore() {
            var mu = info.getGcInfo().getMemoryUsageBeforeGc();
            if (!mu.containsKey(survivorName)) {
                throw new IllegalStateException("expected key: " + survivorName);
            }
            return mu.get(survivorName).getUsed();
        }

        public long getSurvivorUsedAfter() {
            var mu = info.getGcInfo().getMemoryUsageAfterGc();
            if (!mu.containsKey(survivorName)) {
                throw new IllegalStateException("expected key: " + survivorName);
            }
            return mu.get(survivorName).getUsed();
        }

        public long getTuneredUsedBefore() {
            var mu = info.getGcInfo().getMemoryUsageBeforeGc();
            if (!mu.containsKey(tenuredName)) {
                throw new IllegalStateException("expected key: " + tenuredName);
            }
            return mu.get(tenuredName).getUsed();
        }

        public long getTuneredUsedAfter() {
            var mu = info.getGcInfo().getMemoryUsageAfterGc();
            if (!mu.containsKey(tenuredName)) {
                throw new IllegalStateException("expected key: " + tenuredName);
            }
            return mu.get(tenuredName).getUsed();
        }
    }
}
