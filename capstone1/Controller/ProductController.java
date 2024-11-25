package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getProducts() {
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if (productService.addProduct(product)) return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
        return ResponseEntity.status(400).body("Product failed to be added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id,@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getField());
        if (productService.updateProduct(id, product)) return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        if (productService.deleteProduct(id)) return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }

}
