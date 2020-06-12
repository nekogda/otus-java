package hw07.department.observer;

import hw07.department.factory.EventListenersCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationManager {
    private final HashMap<EventType, ArrayList<Listener>> subscribers = new HashMap<>();

    public void subscribe(EventType eventType, Listener handler) {
        ArrayList<Listener> listeners = subscribers.getOrDefault(eventType, new ArrayList<>());
        listeners.add(handler);
        subscribers.put(eventType, listeners);
    }

    public void subscribe(List<? extends EventListenersCreator> eventListeners) {
        eventListeners.forEach(e -> e.createListeners().forEach(this::subscribe));
    }

    public void unsubscribe(List<? extends EventListenersCreator> eventListeners) {
        eventListeners.forEach(e -> e.createListeners().forEach(this::unsubscribe));
    }

    public void unsubscribe(EventType eventType, Listener handler) {
        var handlers = subscribers.get(eventType);
        if (handlers == null) {
            throw new IllegalArgumentException("no such eventType " + eventType);
        }
        if (!handlers.remove(handler)) {
            throw new IllegalArgumentException("no such handler");
        }
    }

    public void notifySubscribers(EventType eventType, Map<EventType, Object> ctx) {
        for (var handler : subscribers.get(eventType)) {
            handler.onEvent(ctx);
        }
    }
}
