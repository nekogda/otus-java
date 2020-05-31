package hw06.atm.dispenser;

import hw06.atm.Nominal;

import java.math.BigDecimal;

public class Cassette {
    private int noteCount;
    private final Nominal nominal;

    public Cassette(Nominal nominal, int noteCount) {
        if (noteCount < 0) {
            throw new IllegalArgumentException("noteCount must be >= 0");
        }
        this.noteCount = noteCount;
        this.nominal = nominal;
    }

    public int takeNotes(int count) {
        if (count > noteCount) {
            throw new IllegalArgumentException("not enough notes, requested: " + count + " have: " + noteCount);
        }
        noteCount -= count;
        return count;
    }

    public boolean isEmpty() {
        if (noteCount == 0) return true;
        return false;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public int getNoteCount() {
        return noteCount;
    }

    public BigDecimal getBalance() {
        return nominal.value.multiply(new BigDecimal(noteCount));
    }
}
