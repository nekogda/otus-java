package hw06.atm.dispenser;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultDispenser implements Dispenser {
    private List<Cassette> cassettes = new ArrayList<>();

    @Override
    public List<Cassette> takeCassettes() {
        var result = getCassettes();
        cassettes.clear();
        return result;
    }

    private List<Cassette> copyCassettes(List<Cassette> cassettes) {
        return cassettes.stream()
                .map(Cassette::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cassette> getCassettes() {
        return copyCassettes(cassettes);
    }

    @Override
    public void insertCassettes(List<Cassette> cassettes) {
        this.cassettes = copyCassettes(cassettes);
    }

    @Override
    public BigDecimal getBalance() {
        return cassettes.stream()
                .map(Cassette::getBalance)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    @Override
    public boolean hasCash(BigDecimal amount) {
        if (getBalance().compareTo(amount) < 0) {
            return false;
        }
        return true;
    }
}
