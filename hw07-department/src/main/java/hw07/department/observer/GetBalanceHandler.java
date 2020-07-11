package hw07.department.observer;

import hw07.department.observer.command.Command;
import hw07.department.visitor.Ctx;

import java.math.BigDecimal;


public class GetBalanceHandler implements Listener {
    private final Command<BigDecimal> command;

    public GetBalanceHandler(Command<BigDecimal> command) {
        this.command = command;
    }

    public Command<BigDecimal> getCommand() {
        return command;
    }

    @Override
    public void onEvent(Ctx ctx) {
        ctx.run(this);
    }
}
