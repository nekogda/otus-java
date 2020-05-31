package hw06.atm;

import hw06.atm.cashout.WithdrawalStrategy;
import hw06.atm.dispenser.Dispenser;

public class ATMBuilder {
    private Dispenser dispenser;
    private WithdrawalStrategy withdrawalStrategy;

    public ATMBuilder setDispenser(Dispenser dispenser) {
        this.dispenser = dispenser;
        return this;
    }

    public ATMBuilder setWithdrawalStrategy(WithdrawalStrategy withdrawalStrategy) {
        this.withdrawalStrategy = withdrawalStrategy;
        return this;
    }

    public ATM build() {
        for (var field : this.getClass().getDeclaredFields()) {
            try {
                if (field.get(this) == null) {
                    throw new IllegalStateException("missing value for the '" + field.getName() + "' field");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new ATM(dispenser, withdrawalStrategy);
    }
}
