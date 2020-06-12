package hw07.department.atm;

import java.math.BigDecimal;

public enum Nominal {
    _100(new BigDecimal(100)),
    _200(new BigDecimal(200)),
    _500(new BigDecimal(500)),
    _1000(new BigDecimal(1000)),
    _2000(new BigDecimal(2000)),
    _5000(new BigDecimal(5000));

    public final BigDecimal value;

    Nominal(final BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
