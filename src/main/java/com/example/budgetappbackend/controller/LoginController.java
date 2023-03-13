package com.example.budgetappbackend.controller;

import com.example.budgetappbackend.model.Authentication;
import com.example.budgetappbackend.model.User;
import com.example.budgetappbackend.repository.AuthenticationRepository;
import com.example.budgetappbackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;

    public LoginController(UserRepository userRepository, AuthenticationRepository authenticationRepository) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
    }

    @GetMapping
    public ResponseEntity validateUser(@RequestParam String email, @RequestParam String password) throws NoSuchAlgorithmException {
        User associatedUser = userRepository.findByEmail(email);
        if (associatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
        }
        // verify the user using the same process as was used to create the hash in the /api/user post route
        // get salt and hashedPassword
        Authentication associatedAuth = associatedUser.getAuthentication();
        byte[] salt = associatedAuth.getSalt();
        byte[] storedHashedPassword = associatedAuth.getHashedPassword();
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        if (Arrays.equals(storedHashedPassword, hashedPassword)) {
            // generate jwt token to send back to client
            // then create routes for the adding, removing, and viewing expenses
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
        }

    }
}
