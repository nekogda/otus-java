package hw07.department.atm;

import hw07.department.atm.cashout.Withdrawal;
import hw07.department.atm.dispenser.Cassette;

import java.math.BigDecimal;
import java.util.List;

public interface ATM {
    Withdrawal getCash(BigDecimal amount);

    Withdrawal getCash(long amount);

    BigDecimal getBalance();

    List<Cassette> reloadCassettes(List<Cassette> cassettes);

    List<Cassette> reloadCassettes(Cassette... cassettes);

    boolean hasCash(BigDecimal amount);
}
