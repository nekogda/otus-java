package ru.otus.core.model;


import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Expose
    @Column(name = "name", nullable = false)
    private String name;

    @Expose
    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true
    )
    @JoinColumn(name = "address_id", nullable = false)
    private AddressDataSet address;

    @Expose
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<PhoneDataSet> phones = new HashSet<>();

    public User() {
    }

    public User(long id, String name, AddressDataSet address, List<PhoneDataSet> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        setPhones(phones);
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones.stream().peek(this::addPhone).collect(Collectors.toSet());
    }

    public void addPhone(PhoneDataSet phone) {
        phone.setUser(this);
        phones.add(phone);
    }

    public void removePhone(PhoneDataSet phone) {
        phones.remove(phone);
        phone.setUser(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
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
        User user = (User) o;
        return name.equals(user.name) &&
                address.equals(user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
