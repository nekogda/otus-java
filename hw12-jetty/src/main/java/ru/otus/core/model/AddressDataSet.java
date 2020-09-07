package ru.otus.core.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class AddressDataSet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "street", unique = true, nullable = false)
    private String street;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDataSet that = (AddressDataSet) o;
        return street.equals(that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street);
    }
}
