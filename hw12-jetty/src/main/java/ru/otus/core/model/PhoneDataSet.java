package ru.otus.core.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phones")
public class PhoneDataSet {

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @Expose
    @Column(name = "number", unique = true, nullable = false)
    private String number;

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public PhoneDataSet() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneDataSet that = (PhoneDataSet) o;
        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
