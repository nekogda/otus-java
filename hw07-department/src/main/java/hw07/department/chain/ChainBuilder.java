package hw07.department.chain;

import java.util.List;

public class ChainBuilder {
    private Handler head;
    private Handler tail;

    public ChainBuilder add(Handler handler) {
        if (this.head == null) {
            this.head = handler;
            tail = handler;
            return this;
        }
        tail.setNext(handler);
        tail = handler;
        return this;
    }

    public ChainBuilder add(List<Handler> handlers) {
        handlers.forEach(this::add);
        return this;
    }

    public Handler build() {
        return head;
    }
}
