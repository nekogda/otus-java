package hw07.department.observer.command;

@FunctionalInterface
public interface Command<T> {
    T execute();
}
