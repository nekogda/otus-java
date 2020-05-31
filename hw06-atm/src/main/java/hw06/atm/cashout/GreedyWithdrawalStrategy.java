package hw06.atm.cashout;

import hw06.atm.Nominal;
import hw06.atm.dispenser.Cassette;
import hw06.atm.dispenser.Dispenser;

import java.math.BigDecimal;
import java.util.*;

public class GreedyWithdrawalStrategy implements WithdrawalStrategy {

    @Override
    public Optional<Withdrawal> getCash(Dispenser dispenser, BigDecimal amount) {
        List<Cassette> cassettes = dispenser.getCassettes();
        cassettes.sort(Comparator.comparing(Cassette::getNominal).reversed());

        var remaining = amount;
        Map<Nominal, Integer> batch = new HashMap<>();

        for (var cassette : cassettes) {
            if (remaining.equals(BigDecimal.ZERO)) break;
            if (cassette.isEmpty()) continue;

            var result = remaining.divideAndRemainder(cassette.getNominal().value);
            var desiredNumOfNotes = result[0].intValue();
            remaining = result[1];

            var notesToTake = Math.min(desiredNumOfNotes, cassette.getNoteCount());
            remaining = remaining.add(new BigDecimal(desiredNumOfNotes - notesToTake)
                    .multiply(cassette.getNominal().value));

            batch.put(cassette.getNominal(), notesToTake);
        }

        if (!remaining.equals(BigDecimal.ZERO)) {
            return Optional.empty();
        }
        Withdrawal withdrawal = new Withdrawal();
        for (var cassette : cassettes) {
            var numOfNotes = batch.getOrDefault(cassette.getNominal(), 0);
            if (numOfNotes == 0) continue;
            cassette.takeNotes(numOfNotes);
            withdrawal.put(cassette.getNominal(), numOfNotes);
        }
        return Optional.of(withdrawal);
    }
}
