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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public ResponseEntity listOfExpenses(@RequestHeader("Authorization") String authorization, @RequestParam Long startDate, @RequestParam Long endDate) {
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
                expensesRepository.save(new Expenses(expenseInfo.getPrice(), expenseInfo.getWhatFor().toLowerCase(), userId, expenseInfo.getWhatTime(), expenseInfo.getNecessary()));
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

    @CrossOrigin
    @GetMapping(value = "/exportCSV", produces = "text/csv")
    public ResponseEntity<Object> exportCSV(@RequestHeader("Authorization") String authorization, @RequestParam Long startDate, @RequestParam Long endDate, @RequestParam Long minValue, @RequestParam Long maxValue, @RequestParam String sortBy, @RequestParam boolean includeUnnecessary) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(KeyProperties.getPublicKey()).build().parseClaimsJws(authorization);
            Long userId = parseLong(jws.getBody().getId());
            // csv header
            String[] csvHeader = {
                    "Expense", "Cost", "Necessary", "Date"
            };

            ByteArrayInputStream byteArrayOutputStream;

            // closing resources by using a try with resources
            // https://www.baeldung.com/java-try-with-resources
            try (
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    // defining the CSV printer
                    CSVPrinter csvPrinter = new CSVPrinter(
                            new PrintWriter(out),
                            // withHeader is optional
                            CSVFormat.EXCEL.withHeader(csvHeader)
                    );
            ) {

                // declaring date format:
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                BigDecimal totalSpending = BigDecimal.valueOf(0);
                BigDecimal totalNecessarySpending = BigDecimal.valueOf(0);

                // data retrieving logic goes here
                List<Expenses> expenses = expensesRepository.findExpensesForCSV( Sort.by( sortBy.equals("Newest") || sortBy.equals("Price: High to Low") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy.equals("Newest") || sortBy.equals("Oldest") ? "whatTime" : "price"), userId, new Date(startDate), new Date(endDate), minValue, maxValue == -1 ? Long.MAX_VALUE : maxValue, !includeUnnecessary);
                for (Expenses expense : expenses) {
                    totalSpending = totalSpending.add(expense.getPrice());
                    if (expense.getNecessary()) {
                        totalNecessarySpending = totalNecessarySpending.add(expense.getPrice());
                    }
                    csvPrinter.printRecord(expense.getWhatFor().substring(0, 1).toUpperCase() + expense.getWhatFor().substring(1), expense.getPrice(), expense.getNecessary() ? 'Y' : 'N', dateFormat.format(expense.getWhatTime()));
                }

                csvPrinter.printRecord(Arrays.asList("Total Spending", "Total Necessary Spending"));
                csvPrinter.printRecord(totalSpending, totalNecessarySpending);

                // writing the underlying stream
                csvPrinter.flush();

                byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

            InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

            String csvFileName = "Budget and Expenses.csv";

            // setting HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
            // defining the custom Content-Type
            headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

            return new ResponseEntity<>(
                    fileInputStream,
                    headers,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.ok(gson.toJson(new ErrorResponseModel("Invalid JWT")));
        }
    }
}
