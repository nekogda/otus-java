package hw06.atm.dispenser;


import java.math.BigDecimal;
import java.util.List;

public class DefaultDispenser implements Dispenser {
    private List<Cassette> cassettes;

    @Override
    public List<Cassette> takeCassettes() {
        var result = cassettes;
        cassettes = null;
        return result;
    }

    @Override
    public List<Cassette> getCassettes() {
        // assert
        return cassettes;
    }


    @Override
    public void insertCassettes(List<Cassette> cassettes) {
        this.cassettes = cassettes;
    }

    @Override
    public BigDecimal getBalance() {
        if (cassettes == null) {
            return BigDecimal.ZERO;
        }
        return cassettes.stream().map(Cassette::getBalance).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    @Override
    public boolean hasCash(BigDecimal amount) {
        if (getBalance().compareTo(amount) < 0) {
            return false;
        }
        return true;
    }
}
