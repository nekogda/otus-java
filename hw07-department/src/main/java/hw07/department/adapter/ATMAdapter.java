package hw07.department.adapter;

import hw07.department.atm.cashout.Withdrawal;

import java.math.BigDecimal;

public interface ATMAdapter {
    BigDecimal getBalance();

    boolean hasCash(BigDecimal amount);

    Withdrawal getCash(int amount);

    Withdrawal getCash(BigDecimal amount);
}
