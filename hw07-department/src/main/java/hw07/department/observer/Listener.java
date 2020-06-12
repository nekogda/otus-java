package hw07.department.observer;

import java.util.Map;

public interface Listener {
    void onEvent(Map<EventType, Object> ctx);
}
