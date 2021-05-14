package com.isik.rest.jpa.test.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Employee extends BaseEntity {

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private int height;

    @Column
    private Date birthDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Title title;

    @ManyToOne
    private Office office;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private Set<Address> addresses;


    public Employee() {
    }

    public Employee(String name, String surname, int height, Date birthDate, Title title) {
        this.name = name;
        this.surname = surname;
        this.height = height;
        this.birthDate = birthDate;
        this.title = title;
    }

    public Employee(String name, String surname, int height, Date birthDate, Title title, Set<Address> addresses, Office office) {
        this.name = name;
        this.surname = surname;
        this.height = height;
        this.birthDate = birthDate;
        this.title = title;
        this.addresses = addresses;
        this.office = office;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
