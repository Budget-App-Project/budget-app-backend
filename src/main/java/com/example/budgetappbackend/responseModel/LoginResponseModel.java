package com.example.budgetappbackend.responseModel;

public class LoginResponseModel {
    String idToken;
    Integer expiresIn = 7200;

    public LoginResponseModel(String idToken) {
        this.idToken = idToken;
    }
}
