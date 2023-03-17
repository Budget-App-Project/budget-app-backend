package com.example.budgetappbackend.requestModel;

import java.sql.Date;

public class ExpenseInfoRequestModel {
    private String price;
    private Date whatTime; // this might need to be a string and then be converted to date later...
    private String whatFor;
    private Boolean necessary;

    public ExpenseInfoRequestModel() {
    }

    public ExpenseInfoRequestModel(String price, Date whatTime, String whatFor, Boolean necessary) {
        this.price = price;
        this.whatTime = whatTime;
        this.whatFor = whatFor;
        this.necessary = necessary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
