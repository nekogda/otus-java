package hw07.department.atm;

import hw07.department.atm.cashout.WithdrawalStrategy;
import hw07.department.atm.dispenser.Dispenser;

import java.math.BigDecimal;

public class NCR extends BaseATM {

    public NCR(Dispenser dispenser, WithdrawalStrategy withdrawalStrategy) {
        super(dispenser, withdrawalStrategy);
        this.name = "NCR";
    }

    public BigDecimal getSummary() {
        return getBalance();
    }
}
