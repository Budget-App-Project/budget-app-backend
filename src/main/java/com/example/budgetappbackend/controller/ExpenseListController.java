package com.example.budgetappbackend.controller;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseListController {
    // will need to create the expense list database model and repository interface
    // will need to create that variable and then load in the value of the variable with the constructor
    // until then, just check to make sure that the jwt is sending with requests
    Gson gson = new Gson();

    public ExpenseListController() {
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity listOfExpenses(@RequestHeader("Authorization") String authorization) {
        // now I just need to authenticate the jwt that was sent as authorization and return the expense list with the given sorting parameters that I will add later
        return ResponseEntity.ok(gson.toJson(authorization));
    }
}
