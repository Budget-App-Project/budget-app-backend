package com.example.budgetappbackend.responseModel;

import com.example.budgetappbackend.model.Expenses;

public class UpdateResponseModel {
    private boolean successful;
    private Expenses updatedExpense;

    public UpdateResponseModel() {
        this.successful = false;
    }
    public UpdateResponseModel(Expenses updatedExpense) {
        this.successful = true;
        this.updatedExpense = updatedExpense;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Expenses getUpdatedExpense() {
        return updatedExpense;
    }

    public void setUpdatedExpense(Expenses updatedExpense) {
        this.updatedExpense = updatedExpense;
    }
}
