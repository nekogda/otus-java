package hw07.department.atm.dispenser;

import java.math.BigDecimal;
import java.util.List;

public interface Dispenser {
    void insertCassettes(List<Cassette> cassettes);

    List<Cassette> takeCassettes();

    List<Cassette> getCassettesCopy();

    boolean hasCash(BigDecimal amount);

    BigDecimal getBalance();
}
