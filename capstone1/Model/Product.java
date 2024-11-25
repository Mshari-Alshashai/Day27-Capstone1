package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "The ID should not be empty")
    private String id;

    @Size(min = 4, message = "The name should have more than 3 characters")
    @NotEmpty(message = "The name should not be empty")
    private String name;

    @NotNull(message = "The price should not be null")
    private double price;

    @NotEmpty(message = "The category ID should not be empty")
    private String categoryId;
}
