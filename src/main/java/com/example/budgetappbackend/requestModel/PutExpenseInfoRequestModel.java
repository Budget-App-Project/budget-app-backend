package com.example.budgetappbackend.requestModel;

import java.math.BigDecimal;
import java.sql.Date;

public class PutExpenseInfoRequestModel extends ExpenseInfoRequestModel {
    private Long expenseId;

    public PutExpenseInfoRequestModel(BigDecimal price, Date whatTime, String whatFor, Boolean necessary, Long expenseId) {
        super(price, whatTime, whatFor, necessary);
        this.expenseId = expenseId;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }
}
