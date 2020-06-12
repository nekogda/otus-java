package hw07.department.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class Caretaker {
    private final Deque<Memento> history = new ArrayDeque<>();

    public void push(Memento memento) {
        history.addFirst(memento);
    }

    public void undo() {
        history.forEach(Memento::restore);
    }
}
