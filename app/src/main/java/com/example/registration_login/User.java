package com.example.registration_login;


public class User {
    public String email;  // Matches the "email" field in Firebase
    public String password; // Matches the "password" field in Firebase

    // Default constructor (required for Firebase)
    public User() {
    }

    // Parameterized constructor (optional, for convenience)
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Optional: Getters and setters for encapsulation
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}