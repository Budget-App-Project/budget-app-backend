package com.example.budgetappbackend.requestModel;

import java.util.Date;

public class ExpenseInfoRequestModel {
    private String price;
    private Date when; // this might need to be a string and then be converted to date later...
    private String whatFor;
    private Boolean necessary;

    public ExpenseInfoRequestModel() {
    }

    public ExpenseInfoRequestModel(String price, Date when, String whatFor, Boolean necessary) {
        this.price = price;
        this.when = when;
        this.whatFor = whatFor;
        this.necessary = necessary;
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
}
