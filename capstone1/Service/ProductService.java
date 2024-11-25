package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryService categoryService;

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product) {
        for (Category category: categoryService.categories) {
            if (product.getCategoryId().equals(category.getId())) {
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id, Product product) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                products.set(products.indexOf(p), product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                products.remove(p);
                return true;
            }
        }
        return false;
    }


}
