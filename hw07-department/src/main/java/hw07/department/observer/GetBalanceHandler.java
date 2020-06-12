package hw07.department.observer;

import hw07.department.observer.command.Command;
import hw07.department.observer.report.BalanceReport;

import java.math.BigDecimal;
import java.util.Map;


public class GetBalanceHandler implements Listener {
    private final Command<BigDecimal> command;

    public GetBalanceHandler(Command<BigDecimal> command) {
        this.command = command;
    }

    @Override
    public void onEvent(Map<EventType, Object> ctx) {
        var report = (BalanceReport) ctx.get(EventType.report);
        report.updateBalance(command.execute());
    }

}
