package hw07.department.atm;

import hw07.department.atm.cashout.WithdrawalStrategy;
import hw07.department.atm.dispenser.Dispenser;

import java.math.BigDecimal;

public class Wincor extends BaseATM {

    public Wincor(Dispenser dispenser, WithdrawalStrategy withdrawalStrategy) {
        super(dispenser, withdrawalStrategy);
        this.name = "Wincor";
    }

    public BigDecimal collectReport() {
        return getBalance();
    }
}
