package com.example.budgetappbackend.controller;

import com.example.budgetappbackend.model.Authentication;
import com.example.budgetappbackend.model.User;
import com.example.budgetappbackend.repository.AuthenticationRepository;
import com.example.budgetappbackend.repository.UserRepository;
import com.example.budgetappbackend.requestModel.SignUpRequestModel;
import com.example.budgetappbackend.responseModel.LoginResponseModel;
import com.example.budgetappbackend.shared.KeyProperties;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private static final Gson gson = new Gson();

    public SignUpController(UserRepository userRepository, AuthenticationRepository authenticationRepository) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity postUser(@RequestBody SignUpRequestModel userInfo) {
        if (userInfo.getEmail() == null || userInfo.getName() == null || userInfo.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must provide all necessary user information");
        }
        boolean userAlreadyExists = this.userRepository.existsByEmail(userInfo.getEmail());
        if (userAlreadyExists) {
            // return that the user already exists with 409 status code
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } else {
            // generate salt:
            try {
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
                User associatedUser = userRepository.save(new User(userInfo.getName(), userInfo.getEmail(), authInfo));
                Date currDate = new Date();
                String jws = Jwts.builder().setSubject(userInfo.getEmail()).claim("id", associatedUser.getId()).claim("name", associatedUser.getName()).setIssuedAt(currDate).setExpiration(new Date(currDate.getTime() + TimeUnit.HOURS.toMillis(2))).signWith(KeyProperties.getPrivateKey()).compact();
                return ResponseEntity.ok(gson.toJson(new LoginResponseModel(jws)));
            } catch (Exception e) {
                System.out.println(e);
                return ResponseEntity.ok(gson.toJson("There was a problem creating the user, please try again later"));
            }
        }
    }
}
