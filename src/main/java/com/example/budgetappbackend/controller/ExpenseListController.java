package com.example.budgetappbackend.controller;

import com.example.budgetappbackend.model.Expenses;
import com.example.budgetappbackend.repository.ExpensesRepository;
import com.example.budgetappbackend.requestModel.ExpenseInfoRequestModel;
import com.example.budgetappbackend.requestModel.PutExpenseInfoRequestModel;
import com.example.budgetappbackend.responseModel.ErrorResponseModel;
import com.example.budgetappbackend.responseModel.SuccessResponseModel;
import com.example.budgetappbackend.shared.KeyProperties;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity listOfExpenses(@RequestHeader("Authorization") String authorization, @RequestParam int page, @RequestParam Long startDate, @RequestParam Long endDate) {
        if (startDate != null && endDate != null) {
            try {
                System.out.println("validating jwt");
                // now I just need to authenticate the jwt that was sent as authorization and return the expense list with the given sorting parameters that I will add later
                Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(KeyProperties.getPublicKey()).build().parseClaimsJws(authorization);
                Long userId = parseLong(jws.getBody().getId());
                // now I need to query the database for the necessary information
                List<Expenses> relevantExpenses = expensesRepository.findRelevantExpenses(userId, new Date(startDate), new Date(endDate));
                System.out.println("Valid jwt");
                return ResponseEntity.ok(gson.toJson(relevantExpenses));
            } catch (Exception e) {
                return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Invalid JWT")));
            }
        } else {
            return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Please provide necessary information")));
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity addExpense(@RequestHeader("Authorization") String authorization, @RequestBody ExpenseInfoRequestModel expenseInfo) {
        if (expenseInfo.getWhatFor() != null && expenseInfo.getPrice() != null && expenseInfo.getWhatTime() != null && expenseInfo.getNecessary() != null) {
            try {
                Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(KeyProperties.getPublicKey()).build().parseClaimsJws(authorization);
                Long userId = parseLong(jws.getBody().getId());
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

    @CrossOrigin
    @PutMapping("/update")
    public ResponseEntity updateExpense(@RequestHeader("Authorization") String authorization, @RequestBody PutExpenseInfoRequestModel expenseInfo) {
        if (expenseInfo.getWhatFor() != null && expenseInfo.getPrice() != null && expenseInfo.getWhatTime() != null && expenseInfo.getNecessary() != null && expenseInfo.getExpenseId() != null) {
            try {
                Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(KeyProperties.getPublicKey()).build().parseClaimsJws(authorization);
                Long userId = parseLong(jws.getBody().getId());
                // add the corresponding expense to the expenses' database.
                Optional<Expenses> assocExpenseOptional = expensesRepository.findById(expenseInfo.getExpenseId());
                // test if the optional returned anything, if not return a no associate expense message
                if (assocExpenseOptional.isPresent()) {
                    Expenses assocExpense = assocExpenseOptional.get();
                    if (assocExpense.getUserId() == userId) {
                        assocExpense.setAllButId(expenseInfo.getPrice(), expenseInfo.getWhatFor(), expenseInfo.getWhatTime(), expenseInfo.getNecessary());
                        expensesRepository.save(assocExpense);
                        return ResponseEntity.ok(new SuccessResponseModel("Successfully updated expense"));
                    } else {
                        return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Unauthorized User")));
                    }
                } else {
                    return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Invalid Expense ID")));
                }
            } catch (Exception e) {
                return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Invalid JWT")));
            }
        } else {
            return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Please provide all fields")));
        }
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public ResponseEntity deleteExpense(@RequestHeader("Authorization") String authorization, @RequestParam Long expenseId) {
        if (expenseId != null) {
            try {
                Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(KeyProperties.getPublicKey()).build().parseClaimsJws(authorization);
                Long userId = parseLong(jws.getBody().getId());
                // add the corresponding expense to the expenses' database.
                Optional<Expenses> assocExpenseOptional = expensesRepository.findById(expenseId);
                // test if the optional returned anything, if not return a no associate expense message
                if (assocExpenseOptional.isPresent()) {
                    Expenses assocExpense = assocExpenseOptional.get();
                    if (assocExpense.getUserId() == userId) {
                        expensesRepository.deleteById(expenseId);
                        return ResponseEntity.ok(new SuccessResponseModel("Successfully deleted expense"));
                    } else {
                        return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Unauthorized User")));
                    }
                } else {
                    return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Invalid Expense ID")));
                }
            } catch (Exception e) {
                return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Invalid JWT")));
            }
        } else {
            return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Please provide all fields")));
        }
    }
}
