package com.example.budgetappbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
@Entity
@Table(name = "authentication")
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] salt;

    private byte[] hashedPassword;

    @OneToOne(mappedBy = "authentication")
    private User user;

    public Authentication() {
    }
    public Authentication(byte[] salt, byte[] hashedPassword) {
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
