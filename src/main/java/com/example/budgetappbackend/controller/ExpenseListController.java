package com.example.budgetappbackend.controller;

import com.example.budgetappbackend.model.Expenses;
import com.example.budgetappbackend.repository.ExpensesRepository;
import com.example.budgetappbackend.requestModel.ExpenseInfoRequestModel;
import com.example.budgetappbackend.responseModel.ErrorResponseModel;
import com.example.budgetappbackend.responseModel.SuccessResponseModel;
import com.example.budgetappbackend.shared.JwsModel;
import com.example.budgetappbackend.shared.KeyProperties;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseListController {
    // will need to create the expense list database model and repository interface
    // will need to create that variable and then load in the value of the variable with the constructor
    // until then, just check to make sure that the jwt is sending with requests
    private static final Gson gson = new Gson();
    private final ExpensesRepository expensesRepository;

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
            JwsModel jwsValues = gson.fromJson(gson.toJson(body), JwsModel.class);
            return ResponseEntity.ok(gson.toJson(authorization));
        } catch (Exception e) {
            return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Invalid JWT")));
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity addExpense(@RequestHeader("Authorization") String authorization, @RequestBody ExpenseInfoRequestModel expenseInfo) {
        if (expenseInfo.getWhatFor() != null && expenseInfo.getPrice() != null && expenseInfo.getWhatTime() != null && expenseInfo.getNecessary() != null) {
            try {
                Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(KeyProperties.getPublicKey()).build().parseClaimsJws(authorization);
                Long userId = parseLong(jws.getBody().getId());
                System.out.println(userId);
                // add the corresponding expense to the expenses' database.
                expensesRepository.save(new Expenses(expenseInfo.getPrice(), expenseInfo.getWhatFor(), userId, expenseInfo.getWhatTime(), expenseInfo.getNecessary()));
                return ResponseEntity.ok(new SuccessResponseModel("Successfully created expense"));
            } catch (Exception e) {
                return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Invalid JWT")));
            }
        } else {
            return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Please provide all fields")));
        }
    }
}
