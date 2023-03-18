package com.example.budgetappbackend.responseModel;

public class SuccessResponseModel {
    private String success;

    public SuccessResponseModel() {
    }

    public SuccessResponseModel(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
