package hw07.department.factory;

import hw07.department.observer.EventType;
import hw07.department.observer.Listener;

import java.util.Map;

public interface EventListenersCreator {
    Map<EventType, Listener> createListeners();
}
