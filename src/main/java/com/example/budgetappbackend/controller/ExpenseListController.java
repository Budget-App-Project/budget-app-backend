package com.example.budgetappbackend.controller;

import com.example.budgetappbackend.repository.ExpensesRepository;
import com.example.budgetappbackend.shared.KeyProperties;
import com.google.gson.Gson;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseListController {
    // will need to create the expense list database model and repository interface
    // will need to create that variable and then load in the value of the variable with the constructor
    // until then, just check to make sure that the jwt is sending with requests
    private final ExpensesRepository expensesRepository;

    private static final Gson gson = new Gson();

    public ExpenseListController(ExpensesRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity listOfExpenses(@RequestHeader("Authorization") String authorization) {
        // now I just need to authenticate the jwt that was sent as authorization and return the expense list with the given sorting parameters that I will add later
        try {
            Jws jws = Jwts.parserBuilder().setSigningKey(KeyProperties.getPublicKey()).build().parseClaimsJws(authorization);
            Object body = jws.getBody();
            System.out.println("Valid jwt");
            return ResponseEntity.ok(gson.toJson(authorization));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity addExpense(@RequestHeader("Authorization") String authorization) {
        try {
            Jws jws = Jwts.parserBuilder().setSigningKey(KeyProperties.getPublicKey()).build().parseClaimsJws(authorization);
            Object body = jws.getBody();
            System.out.println("Valid jwt");
            return ResponseEntity.ok(gson.toJson(authorization));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }
    }
}
