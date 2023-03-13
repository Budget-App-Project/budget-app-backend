package com.example.budgetappbackend.controller;

import com.example.budgetappbackend.model.Authentication;
import com.example.budgetappbackend.model.User;
import com.example.budgetappbackend.repository.AuthenticationRepository;
import com.example.budgetappbackend.repository.UserRepository;
import com.example.budgetappbackend.requestModel.UserRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;

    public UserController(UserRepository userRepository, AuthenticationRepository authenticationRepository) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
    }

    @GetMapping
    public ResponseEntity getAllUsers() {
       return ResponseEntity.ok(this.userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity postUser(@RequestBody UserRequestModel userInfo) throws NoSuchAlgorithmException {
        if (userInfo.getEmail() == null || userInfo.getName() == null || userInfo.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must provide all necessary user information");
        }
        boolean userAlreadyExists = this.userRepository.existsByEmail(userInfo.getEmail());
        if (userAlreadyExists) {
            // return that the user already exists with 409 status code
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } else {
            // generate salt:
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            // generate hash function
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            // hash password
            byte[] hashedPassword = md.digest(userInfo.getPassword().getBytes(StandardCharsets.UTF_8));
            // create auth info in db.
            Authentication authInfo = authenticationRepository.save(new Authentication(salt, hashedPassword));
            // Use the returned authInfo object to then create corresponding entry in user repository
            User createdUser = userRepository.save(new User(userInfo.getName(), userInfo.getEmail(), authInfo));
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
    }
}
