package ru.otus.core.model;

import ru.otus.jdbc.annotations.Id;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    @Id
    private long no;
    private String type = "";
    private BigDecimal rest = new BigDecimal(0);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return type.equals(account.type) &&
                rest.equals(account.rest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, rest);
    }

    public Account() {
    }

    public Account(long no, String type, BigDecimal rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getRest() {
        return rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
