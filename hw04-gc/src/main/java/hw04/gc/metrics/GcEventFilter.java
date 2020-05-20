package hw04.gc.metrics;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.openmbean.CompositeData;

public class GcEventFilter implements NotificationFilter {
    @Override
    public boolean isNotificationEnabled(Notification notification) {
        CompositeData cd = (CompositeData) notification.getUserData();
        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from(cd);
        return info.getGcAction().equals("end of major GC") || info.getGcAction().equals("end of minor GC");
    }
}
