package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotEmpty(message = "The ID should not be empty")
    private String id;

    @Size(min = 6, message = "The username should have more than 5 characters")
    @NotEmpty(message = "The username should not be empty")
    private String username;

    @NotEmpty(message = "The password should not be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{7,}$",message = "Password should have letters and numbers and be more than 6 characters")
    private String password;

    @NotEmpty(message = "The email should not be empty")
    @Email(message = "Wrong format for the email")
    private String email;

    @NotEmpty(message = "The role should not be empty")
    @Pattern(regexp = "Admin|Customer",message = "The role should be Admin or customer")
    private String role;

    @NotNull(message = "The balance should not be null")
    @Positive(message = "The balance should be positive")
    private double balance;

    private ArrayList<String> purchasedProducts=new ArrayList<>();

    private ArrayList<Product> wishlist=new ArrayList<>();

}
