package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "The ID should not be empty")
    private String id;

    @NotEmpty(message = "The name should not be empty")
    private String productID;

    @NotEmpty(message = "The ID should not be empty")
    private String merchantID;

    @Min(value = 10,message = "The stock have to be more than 10")
    @Positive(message = "The stock should not be 0 or negative")
    private int stock;
}
