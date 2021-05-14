package com.isik.rest.jpa.models;

import javax.persistence.*;

@Entity
public class Address extends BaseEntity{

    @Column
    private String fullAddress;

    @ManyToOne
    private City city;

    @ManyToOne
    private Employee employee;

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
