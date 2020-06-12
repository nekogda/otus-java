package hw07.department.adapter;

import hw07.department.DepATM;
import hw07.department.atm.NCR;
import hw07.department.atm.cashout.Withdrawal;
import hw07.department.atm.dispenser.Cassette;
import hw07.department.memento.Memento;
import hw07.department.memento.NCRCassettesMemento;
import hw07.department.observer.EventType;
import hw07.department.observer.GetBalanceHandler;
import hw07.department.observer.Listener;
import hw07.department.observer.command.GetBalanceCommand;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NCRAdapter implements DepATM {
    private final NCR atm;

    public NCRAdapter(NCR atm) {
        this.atm = atm;
    }

    @Override
    public Memento save() {
        return new NCRCassettesMemento(this, atm.getDispenserStatus());
    }

    @Override
    public Withdrawal getCash(BigDecimal amount) {
        return atm.getCash(amount);
    }

    public void setState(List<Cassette> cassettes) {
        atm.reloadCassettes(cassettes);
    }

    @Override
    public BigDecimal getBalance() {
        return atm.getSummary();
    }

    @Override
    public boolean hasCash(BigDecimal amount) {
        System.out.println("Some work in NCR ATM");
        return atm.hasCash(amount);
    }

    @Override
    public Withdrawal getCash(int amount) {
        System.out.println("Some work in NCR ATM at getCash");
        return atm.getCash(amount);
    }

    @Override
    public Map<EventType, Listener> createListeners() {
        var subscriptions = new HashMap<EventType, Listener>();
        subscriptions.put(EventType.report, new GetBalanceHandler(new GetBalanceCommand(this)));
        return subscriptions;
    }
}
