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

    private Boolean necessary = false;

    private String whatFor;

    private Date when;

    @ManyToOne
    private Long user_id;

    public Expenses() {
    }

    public Expenses(BigDecimal price, Boolean necessary, String whatFor, Date when, Long user_id) {
        this.price = price;
        this.necessary = necessary;
        this.whatFor = whatFor;
        this.when = when;
        this.user_id = user_id;
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

    public Boolean getNecessary() {
        return necessary;
    }

    public void setNecessary(Boolean necessary) {
        this.necessary = necessary;
    }

    public String getWhatFor() {
        return whatFor;
    }

    public void setWhatFor(String whatFor) {
        this.whatFor = whatFor;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
