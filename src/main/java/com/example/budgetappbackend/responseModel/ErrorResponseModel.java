package com.example.budgetappbackend.responseModel;

public class ErrorResponseModel {
    private String error;

    public ErrorResponseModel() {
    }

    public ErrorResponseModel(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
