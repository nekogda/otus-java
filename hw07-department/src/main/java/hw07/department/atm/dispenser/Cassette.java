package hw07.department.atm.dispenser;

import hw07.department.atm.Nominal;

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

    public Cassette(Cassette cassette) {
        this.noteCount = cassette.noteCount;
        this.nominal = cassette.getNominal();
    }

    public int takeNotes(int count) {
        if (count > noteCount) {
            throw new IllegalArgumentException("not enough notes, requested: " + count + " have: " + noteCount);
        }
        noteCount -= count;
        return count;
    }

    public boolean isEmpty() {
        return noteCount == 0;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public int getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(int noteCount) {
        this.noteCount += noteCount;
    }

    public BigDecimal getBalance() {
        return nominal.value.multiply(new BigDecimal(noteCount));
    }
}
