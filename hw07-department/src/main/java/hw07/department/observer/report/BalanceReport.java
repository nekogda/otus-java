package hw07.department.observer.report;

import java.math.BigDecimal;

public class BalanceReport {
    private BigDecimal balance = new BigDecimal(0);

    public BigDecimal getBalance() {
        return balance;
    }

    public void updateBalance(BigDecimal remainder) {
        balance = balance.add(remainder);
    }

    @Override
    public String toString() {
        return balance.toString();
    }

}
