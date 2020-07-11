package hw07.department.observer;

import hw07.department.visitor.Ctx;

public interface Listener {
    void onEvent(Ctx ctx);
}
