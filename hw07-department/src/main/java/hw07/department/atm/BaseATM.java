package hw07.department.atm;

import hw07.department.atm.cashout.Withdrawal;
import hw07.department.atm.cashout.WithdrawalStrategy;
import hw07.department.atm.dispenser.Cassette;
import hw07.department.atm.dispenser.Dispenser;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class BaseATM implements ATM {
    protected String name;
    protected Dispenser dispenser;
    protected WithdrawalStrategy withdrawalStrategy;

    public BaseATM(Dispenser dispenser, WithdrawalStrategy withdrawalStrategy) {
        if (dispenser == null || withdrawalStrategy == null) {
            throw new IllegalArgumentException("argument must be not null");
        }
        this.dispenser = dispenser;
        this.withdrawalStrategy = withdrawalStrategy;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    @Override
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

    public String getName() {
        return name;
    }

    @Override
    public boolean hasCash(BigDecimal amount) {
        return dispenser.hasCash(amount);
    }

    @Override
    public Withdrawal getCash(long amount) {
        return getCash(BigDecimal.valueOf(amount));
    }

    @Override
    public BigDecimal getBalance() {
        return dispenser.getBalance();
    }

    @Override
    public List<Cassette> reloadCassettes(List<Cassette> cassettes) {
        var result = dispenser.takeCassettes();
        dispenser.insertCassettes(cassettes);
        return result;
    }

    @Override
    public List<Cassette> reloadCassettes(Cassette... cassettes) {
        return reloadCassettes(Arrays.asList(cassettes));
    }

    public List<Cassette> getDispenserStatus() {
        return dispenser.getCassettesCopy();
    }
}
