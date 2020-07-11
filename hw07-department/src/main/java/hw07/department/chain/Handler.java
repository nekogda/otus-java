package hw07.department.chain;

public interface Handler {
    void setNext(Handler next);

    Handler getNext();

    boolean hasNext();

    void handle(ChainCommand command);
}
