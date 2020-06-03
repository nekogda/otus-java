package hw06.atm;

import hw06.atm.cashout.Withdrawal;
import hw06.atm.cashout.WithdrawalStrategy;
import hw06.atm.dispenser.Cassette;
import hw06.atm.dispenser.Dispenser;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ATM {

    private final Dispenser dispenser;
    private WithdrawalStrategy withdrawalStrategy;

    public ATM(Dispenser dispenser, WithdrawalStrategy withdrawalStrategy) {
        if (dispenser == null || withdrawalStrategy == null) {
            throw new IllegalArgumentException("argument must be not null");
        }
        this.dispenser = dispenser;
        this.withdrawalStrategy = withdrawalStrategy;
    }

    public void setWithdrawalStrategy(WithdrawalStrategy strategy) {
        withdrawalStrategy = strategy;
    }

    public Withdrawal getCash(BigDecimal amount) {
        if (!dispenser.hasCash(amount)) {
            throw new IllegalStateException("недостаточно средств");
        }

        Optional<Withdrawal> result = withdrawalStrategy.getCash(dispenser, amount);

        if (result.isEmpty()) {
            throw new IllegalStateException("недостаточно средств");
        }

        return result.get();
    }

    public Withdrawal getCash(int amount) {
        return getCash(BigDecimal.valueOf(amount));
    }


    public BigDecimal getBalance() {
        return dispenser.getBalance();
    }

    public List<Cassette> reloadCassettes(List<Cassette> cassettes) {
        var result = dispenser.takeCassettes();
        dispenser.insertCassettes(cassettes);
        return result;
    }

    public List<Cassette> reloadCassettes(Cassette... cassettes) {
        return reloadCassettes(Arrays.asList(cassettes));
    }
}
