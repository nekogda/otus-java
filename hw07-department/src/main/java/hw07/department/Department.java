
package hw07.department;

import hw07.department.adapter.ATMAdapter;
import hw07.department.atm.cashout.Withdrawal;
import hw07.department.chain.ATMHandler;
import hw07.department.chain.ChainBuilder;
import hw07.department.chain.Handler;
import hw07.department.chain.HasCashCommand;
import hw07.department.memento.Caretaker;
import hw07.department.observer.NotificationManager;
import hw07.department.observer.report.BalanceReport;
import hw07.department.observer.EventType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Department {
    private final NotificationManager notificationManager;
    private final Handler chain;
    private final Caretaker caretaker = new Caretaker();

    public Department(List<DepATM> atms) {
        if (atms == null || atms.size() == 0) {
            throw new IllegalArgumentException("atms must not be empty");
        }
        chain = new ChainBuilder()
                .add(atms.stream()
                        .map(ATMHandler::new)
                        .collect(Collectors.toList()))
                .build();
        notificationManager = new NotificationManager();
        notificationManager.subscribe(atms);
    }

    public ATMAdapter getFirstATMWithCash(BigDecimal amount) {
        var command = new HasCashCommand(amount);
        chain.handle(command);
        return command.getATM();
    }

    public BigDecimal gatherATMsBalance() {
        Map<EventType, Object> ctx = new HashMap<>();
        ctx.put(EventType.report, new BalanceReport());
        notificationManager.notifySubscribers(EventType.report, ctx);
        var report = (BalanceReport) ctx.get(EventType.report);
        return report.getBalance();
    }

    public Withdrawal getCash(DepATM atm, BigDecimal amount) {
        if (!atm.hasCash(amount)) {
            throw new IllegalStateException("недостаточно средств");
        }
        caretaker.push(atm.save());
        return atm.getCash(amount);
    }

    public void restoreStates() {
        caretaker.undo();
    }
}
