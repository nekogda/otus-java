package hw06.atm;

import hw06.atm.cashout.GreedyWithdrawalStrategy;
import hw06.atm.dispenser.Cassette;
import hw06.atm.dispenser.DefaultDispenser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ATM должен")
class ATMTest {

    private ATM atm;
    private List<Cassette> cassettes;

    @BeforeEach
    void init() {
        atm = new ATM(new DefaultDispenser(), new GreedyWithdrawalStrategy());
        cassettes = new ArrayList<>();
    }

    @Test
    @DisplayName("создаваться с помощью ATMBuild")
    void atmBuilderTest() {
        var atm = new ATMBuilder()
                .setDispenser(new DefaultDispenser())
                .setWithdrawalStrategy(new GreedyWithdrawalStrategy())
                .build();

        assertNotNull(atm);
    }

    @Test
    @DisplayName("выдавать одну купюру указанного номинала")
    void getSingleNote() {
        atm.reloadCassettes(new Cassette(Nominal._100, 1));
        var withdrawal = atm.getCash(100);
        assertEquals(1, withdrawal.getNotesCount());
        assertEquals(BigDecimal.valueOf(100), withdrawal.getSum());
    }

    @Test
    @DisplayName("выбрасывать исключение при отсутствии запрошенной суммы")
    void noCashTest1() {
        atm.reloadCassettes(new Cassette(Nominal._100, 1));
        var e = assertThrows(IllegalStateException.class,
                () -> atm.getCash(200));
        assertEquals(e.getMessage(), "недостаточно средств");
    }

    @Test
    @DisplayName("выбрасывать исключение при отсутствии купюр нужного номинала")
    void noCashTest2() {
        atm.reloadCassettes(new Cassette(Nominal._200, 1));
        var e = assertThrows(IllegalStateException.class,
                () -> atm.getCash(100));
        assertEquals(e.getMessage(), "недостаточно средств");
    }

    @Test
    @DisplayName("выбрасывать исключение при отсутствии кассет")
    void noCashTest3() {
        var e = assertThrows(IllegalStateException.class,
                () -> atm.getCash(100));
        assertEquals(e.getMessage(), "недостаточно средств");
    }

    @Test
    @DisplayName("выдать запрошенную сумму")
    void basicATMJustEnough() {
        atm.reloadCassettes(
                new Cassette(Nominal._100, 2),
                new Cassette(Nominal._200, 2),
                new Cassette(Nominal._500, 2));

        var withdrawal = atm.getCash(BigDecimal.valueOf(800));
        var balance = atm.getBalance();
        var cassettes = atm.reloadCassettes();

        assertEquals(BigDecimal.valueOf(800), withdrawal.getSum());
        assertEquals(3, withdrawal.getNotesCount());
        assertEquals(3, cassettes.stream().map(Cassette::getNoteCount).reduce(0,Integer::sum));
        assertEquals(BigDecimal.valueOf(800), balance);
    }

    @Test
    @DisplayName("вернуть баланс")
    void initATMSameFaceValues() {
        atm.reloadCassettes(
                new Cassette(Nominal._100, 1),
                new Cassette(Nominal._100, 2),
                new Cassette(Nominal._100, 2)
        );
        assertEquals(BigDecimal.valueOf(500), atm.getBalance());
    }
}
