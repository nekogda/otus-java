package hw07.department.atm.cashout;

import hw07.department.atm.Nominal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Withdrawal {
    private final HashMap<Nominal, Integer> batch = new HashMap<>();

    public void put(Nominal nominal, Integer numOfNotes) {
        batch.put(nominal, numOfNotes);
    }

    public int getNotesCount() {
        return batch.values().stream().reduce(0, Integer::sum);
    }

    public BigDecimal getSum() {
        return batch
                .entrySet()
                .stream()
                .map(e -> e.getKey().value.multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return batch.entrySet()
                .stream()
                .map(e -> String.format("%.0f - %d", e.getKey().value, e.getValue()))
                .collect(Collectors.joining(
                        ", ",
                        "Выдано " + getNotesCount() + " банкнот: ",
                        " на общую сумму: " + getSum()));
    }
}
