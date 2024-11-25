package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id,@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if (userService.updateUser(id, user))return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        return ResponseEntity.status(200).body(new ApiResponse("User not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        if (userService.deleteUser(id))return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
        return ResponseEntity.status(200).body(new ApiResponse("User not found"));
    }

    @PutMapping("/buy-product/{userId}/{productId}/{merchantId}")
    public ResponseEntity buyProduct(@PathVariable String userId,@PathVariable String productId,@PathVariable String merchantId){
        return switch (userService.buyProduct(userId,productId,merchantId)){
          case 0 -> ResponseEntity.status(200).body(new ApiResponse("Product bought successfully"));
          case 1 -> ResponseEntity.status(400).body(new ApiResponse("Product out of stock"));
          case 2 -> ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
          case 3 -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
          case 4 -> ResponseEntity.status(400).body(new ApiResponse("user not found"));
          case 5 -> ResponseEntity.status(400).body(new ApiResponse("Insufficient balance"));
          default -> ResponseEntity.status(400).body(new ApiResponse("Buying process failed"));
        };
    }

    @GetMapping("/get-wishlist/{userId}")
    public ResponseEntity getWishlist(@PathVariable String userId){
        if (userService.getWishlist(userId)==null) return ResponseEntity.status(404).body(new ApiResponse("User not found"));
        return ResponseEntity.status(200).body(userService.getWishlist(userId));
    }

    @PostMapping("/add-wishlist/{userId}/{productId}")
    public ResponseEntity addWishlist(@PathVariable String userId, @PathVariable String productId){
        return switch (userService.addWishlist(userId,productId)){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Product added successfully to the wishlist"));
            case 1 -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Adding to wishlist process failed"));
        };
    }

    @DeleteMapping("/delete-wishlist/{userId}/{productId}")
    public ResponseEntity deleteWishlist(@PathVariable String userId,@PathVariable String productId){
       return switch (userService.deleteWishlist(userId,productId)){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
            case 1 -> ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
            default -> ResponseEntity.status(400).body(new ApiResponse("The process failed"));
        };
    }

    @PutMapping("/move-to-shopping-cart/{userId}")
    public ResponseEntity moveToShoppingCart(@PathVariable String userId){
        return switch (userService.moveToShoppingCart(userId)){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Shopping cart moved successfully"));
            case 1 -> ResponseEntity.status(400).body(new ApiResponse("Wishlist is empty"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("User not found"));
            default -> ResponseEntity.status(400).body(new ApiResponse("The process failed"));
        };
    }

    @GetMapping("/get-shopping-cart")
    public ResponseEntity getShoppingCart(){
        return ResponseEntity.status(200).body(userService.getShoppingCart());
    }

    @PostMapping("/add-shopping-cart/{productId}/{merchantId}")
    public ResponseEntity addShoppingCart(@PathVariable String productId, @PathVariable String merchantId){
        return switch (userService.addShoppingCart(productId,merchantId)){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
            case 1 -> ResponseEntity.status(400).body(new ApiResponse("Product not available"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Failed to add to shopping cart"));
        };
    }

    @DeleteMapping("/delete-shopping-cart/{id}")
    public ResponseEntity deleteShoppingCart(@PathVariable String id){
        if (userService.deleteShoppingCart(id)) return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Failed to delete from shopping cart"));
    }

    @PutMapping("/buy-all-in-shopping-cart/{id}")
    public ResponseEntity buyAllInShoppingCart(@PathVariable String id){
        return switch (userService.buyAllInShoppingCart(id)){
            case 0 -> ResponseEntity.status(200).body(new ApiResponse("Products bought successfully"));
            case 1 -> ResponseEntity.status(400).body(new ApiResponse("Products out of stock"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse("Products not found"));
            case 4 -> ResponseEntity.status(400).body(new ApiResponse("user not found"));
            case 5 -> ResponseEntity.status(400).body(new ApiResponse("Insufficient balance"));
            case 6 -> ResponseEntity.status(400).body(new ApiResponse("Shopping cart is empty"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Failed to buy from shopping cart"));
        };
    }

    @GetMapping("/get-purchase-history/{userId}")
    public ResponseEntity getPurchaseHistory(@PathVariable String userId){
        if (userService.getPurchaseHistory(userId)==null) return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        return ResponseEntity.status(200).body(userService.getPurchaseHistory(userId));
    }

    @GetMapping("/recommend-products/{userId}")
    public ResponseEntity recommendProducts(@PathVariable String userId){
        if (userService.recommendProducts(userId)==null) return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        return ResponseEntity.status(200).body(userService.recommendProducts(userId));
    }

}
