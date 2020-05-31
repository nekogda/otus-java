package hw06.atm.cashout;

import hw06.atm.dispenser.Dispenser;

import java.math.BigDecimal;
import java.util.Optional;

public interface WithdrawalStrategy {
    Optional<Withdrawal> getCash(Dispenser dispenser, BigDecimal amount);
}
