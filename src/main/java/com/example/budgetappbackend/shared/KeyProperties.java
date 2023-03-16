package com.example.budgetappbackend.shared;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyProperties {
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    public static PrivateKey getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(PrivateKey privateKey) {
        KeyProperties.privateKey = privateKey;
    }

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static void setPublicKey(PublicKey publicKey) {
        KeyProperties.publicKey = publicKey;
    }

}
