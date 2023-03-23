package com.example.budgetappbackend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "expenses", indexes = @Index(columnList = "user_id", unique = false))
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private String whatFor;

    @Temporal(TemporalType.DATE)
    private Date whatTime;

    private Boolean necessary;

    @Column(name = "user_id")
    private Long userId;

    public Expenses() {
    }

    public Expenses(BigDecimal price, String whatFor, Long userId, Date whatTime, Boolean necessary) {
        this.price = price;
        this.whatTime = whatTime;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getWhatTime() {
        return whatTime;
    }

    public void setWhatTime(Date whatTime) {
        this.whatTime = whatTime;
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

