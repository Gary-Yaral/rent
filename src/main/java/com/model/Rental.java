package com.model;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_time")
    private Integer rentalTime;

    @Column(name = "payment")
    private Float payment;

    @Column(name = "current_date")
    private Timestamp currentDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    private Images house;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Tenant tenant;

    public Rental() {}

    public Rental(Integer rentalTime, Float payment, Timestamp currentDate, Images house, Tenant tenant) {
        this.rentalTime = rentalTime;
        this.payment = payment;
        this.currentDate = currentDate;
        this.house = house;
        this.tenant = tenant;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(Integer rentalTime) {
        this.rentalTime = rentalTime;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public Timestamp getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Timestamp currentDate) {
        this.currentDate = currentDate;
    }

    public Images getHouse() {
        return house;
    }

    public void setHouse(Images house) {
        this.house = house;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
