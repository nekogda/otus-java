package hw07.department.chain;

import hw07.department.DepATM;

import java.math.BigDecimal;

public class HasCashCommand implements ChainCommand {
    private final BigDecimal amount;
    private DepATM atm;

    public HasCashCommand(BigDecimal amount) {
        this.amount = amount;
    }

    public DepATM getATM() {
        return atm;
    }

    @Override
    public boolean execute(DepATM atm) {
        if (atm.hasCash(amount)) {
            this.atm = atm;
            return true;
        }
        return false;
    }
}
