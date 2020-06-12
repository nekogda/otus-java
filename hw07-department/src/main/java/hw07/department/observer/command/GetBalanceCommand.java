package hw07.department.observer.command;

import hw07.department.adapter.ATMAdapter;

import java.math.BigDecimal;

public class GetBalanceCommand implements Command<BigDecimal> {
    private final ATMAdapter atm;

    public GetBalanceCommand(ATMAdapter atm) {
        this.atm = atm;
    }

    @Override
    public BigDecimal execute() {
        return atm.getBalance();
    }
}
