package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "The ID should not be empty")
    private String id;

    @Size(min = 4, message = "The name should have more than 3 characters")
    @NotEmpty(message = "The name should not be empty")
    private String name;
}
