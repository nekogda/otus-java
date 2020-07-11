package hw07.department.atm.cashout;

import hw07.department.atm.dispenser.Dispenser;

import java.math.BigDecimal;
import java.util.Optional;

public interface WithdrawalStrategy {
    Optional<Withdrawal> getCash(Dispenser dispenser, BigDecimal amount);
}
