package com.example.budgetappbackend.controller;

import com.example.budgetappbackend.model.Authentication;
import com.example.budgetappbackend.model.User;
import com.example.budgetappbackend.repository.AuthenticationRepository;
import com.example.budgetappbackend.repository.UserRepository;
import com.example.budgetappbackend.requestModel.LoginRequestModel;
import com.example.budgetappbackend.responseModel.LoginResponseModel;
import com.example.budgetappbackend.shared.KeyProperties;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;


@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final UserRepository userRepository;
    private static final Gson gson = new Gson();

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
        // define and set private and public keys
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        KeyProperties.setPrivateKey(keyPair.getPrivate());
        KeyProperties.setPublicKey(keyPair.getPublic());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity validateUser(@RequestBody LoginRequestModel loginInfo) throws NoSuchAlgorithmException {
        User associatedUser = userRepository.findByEmail(loginInfo.getEmail().toLowerCase());
        if (associatedUser == null) {
            return ResponseEntity.ok(gson.toJson("Incorrect email or password"));
        }
        // verify the user using the same process as was used to create the hash in the /api/user post route
        // get salt and hashedPassword
        Authentication associatedAuth = associatedUser.getAuthentication();
        byte[] salt = associatedAuth.getSalt();
        byte[] storedHashedPassword = associatedAuth.getHashedPassword();
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] hashedPassword = md.digest(loginInfo.getPassword().getBytes(StandardCharsets.UTF_8));
        if (Arrays.equals(storedHashedPassword, hashedPassword)) {
            // generate jwt token to send back to client
            // then create routes for the adding, removing, and viewing expenses
            Date currDate = new Date();
            String jws = Jwts.builder().setSubject(loginInfo.getEmail()).setId(String.valueOf(associatedUser.getId())).claim("name", associatedUser.getName()).setIssuedAt(currDate).setExpiration(new Date(currDate.getTime() + TimeUnit.HOURS.toMillis(2))).signWith(KeyProperties.getPrivateKey()).compact();
            return ResponseEntity.ok(gson.toJson(new LoginResponseModel(jws)));
        } else {
            return ResponseEntity.ok(gson.toJson("Incorrect email or password"));
        }

    }
}
