package com.aircraft_ground_handling.service_billings.repo.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "billings")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "ENUM('PARKING')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private BillingServiceType billingServiceType;

    @Column(columnDefinition = "ENUM('ACTIVE', 'CLOSED')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private BillingStatus billingStatus;

    @NotNull
    private int count;

    @NotNull
    private double price;

    @NotNull
    private double totalPrice;

    @NotNull
    private int consumer;

    public Billing() {
    }

    public Billing(BillingServiceType billingServiceType, BillingStatus billingStatus, int count,
                   double price, double totalPrice, int consumer) {
        this.billingServiceType = billingServiceType;
        this.billingStatus = billingStatus;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.consumer = consumer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BillingServiceType getBillingServiceType() {
        return billingServiceType;
    }

    public void setBillingServiceType(BillingServiceType billingServiceType) {
        this.billingServiceType = billingServiceType;
    }

    public BillingStatus getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(BillingStatus billingStatus) {
        this.billingStatus = billingStatus;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getConsumer() {
        return consumer;
    }

    public void setConsumer(int consumer) {
        this.consumer = consumer;
    }
}
