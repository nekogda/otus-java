package hw07.department;

import hw07.department.adapter.NCRAdapter;
import hw07.department.adapter.WincorAdapter;
import hw07.department.atm.NCR;
import hw07.department.atm.Nominal;
import hw07.department.atm.Wincor;
import hw07.department.atm.cashout.GreedyWithdrawalStrategy;
import hw07.department.atm.dispenser.Cassette;
import hw07.department.atm.dispenser.DefaultDispenser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Department must")
class DepartmentTest {
    private List<DepATM> atms;
    private Department department;

    @BeforeEach
    void setup() {
        atms = new ArrayList<>();
        var ncr = new NCR(new DefaultDispenser(), new GreedyWithdrawalStrategy());
        var wincor = new Wincor(new DefaultDispenser(), new GreedyWithdrawalStrategy());
        ncr.reloadCassettes(new Cassette(Nominal._100, 2));
        wincor.reloadCassettes(new Cassette(Nominal._200, 2));
        atms.add(new NCRAdapter(ncr));
        atms.add(new WincorAdapter(wincor));
        department = new Department(atms);
    }

    @Test
    @DisplayName("be empty")
    void emptyDepartment() {
        var e = assertThrows(IllegalArgumentException.class, () -> new Department(new ArrayList<DepATM>()));
        assertEquals(e.getMessage(), "atms must not be empty");
    }

    @Test
    @DisplayName("get balance from all ATMs")
    void gatherATMsBalance() {
        assertEquals(BigDecimal.valueOf(600), department.gatherATMsBalance());
    }

    @Test
    @DisplayName("restore state of all ATMs")
    void restoreStateATMs() {
        department.getCash(atms.get(0), BigDecimal.valueOf(100));
        department.getCash(atms.get(1), BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(300), department.gatherATMsBalance());
        department.restoreStates();
        assertEquals(BigDecimal.valueOf(600), department.gatherATMsBalance());
    }

    @Test
    @DisplayName("find ATM which can get cash")
    void getCash() {
        assertEquals(atms.get(1), department.getFirstATMWithCash(BigDecimal.valueOf(400)));
    }

    @Test
    @DisplayName("not find ATM which can get cash")
    void getCashNegative() {
        assertNull(department.getFirstATMWithCash(BigDecimal.valueOf(700)));
    }
}

