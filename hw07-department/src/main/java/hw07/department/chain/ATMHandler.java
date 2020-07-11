package hw07.department.chain;

import hw07.department.DepATM;


public class ATMHandler implements Handler {
    private Handler next;
    private final DepATM atm;

    public ATMHandler(DepATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public void handle(ChainCommand command) {
        if (command.execute(atm)) {
            return;
        }
        if (next != null) {
            next.handle(command);
        }
    }

    @Override
    public String toString() {
        return atm.toString();
    }
}
