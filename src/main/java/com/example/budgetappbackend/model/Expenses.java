package com.example.budgetappbackend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "expenses", indexes = @Index(columnList = "user_id", unique = false))
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String price;

    private Date when;

    private String whatFor;

    private Boolean necessary = false;

    @Column(name = "user_id")
    private Long userId;

    public Expenses() {
    }

    public Expenses(String price, Date when, String whatFor, Boolean necessary, Long userId) {
        this.price = price;
        this.when = when;
        this.whatFor = whatFor;
        this.necessary = necessary;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public String getWhatFor() {
        return whatFor;
    }

    public void setWhatFor(String whatFor) {
        this.whatFor = whatFor;
    }

    public Boolean getNecessary() {
        return necessary;
    }

    public void setNecessary(Boolean necessary) {
        this.necessary = necessary;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

