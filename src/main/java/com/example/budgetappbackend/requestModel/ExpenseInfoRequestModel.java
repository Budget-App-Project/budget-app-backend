package com.example.budgetappbackend.requestModel;

import java.math.BigDecimal;
import java.sql.Date;

public class ExpenseInfoRequestModel {
    private BigDecimal price;
    private Date whatTime;
    private String whatFor;
    private Boolean necessary;

    public ExpenseInfoRequestModel() {
    }

    public ExpenseInfoRequestModel(BigDecimal price, Date whatTime, String whatFor, Boolean necessary) {
        this.price = price;
        this.whatTime = whatTime;
        this.whatFor = whatFor;
        this.necessary = necessary;
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
}
