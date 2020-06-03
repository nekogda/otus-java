package hw06.atm.dispenser;

import java.math.BigDecimal;
import java.util.List;

public interface Dispenser {
    void insertCassettes(List<Cassette> cassettes);

    List<Cassette> takeCassettes();

    List<Cassette> getCassettes();

    boolean hasCash(BigDecimal amount);

    BigDecimal getBalance();
}
