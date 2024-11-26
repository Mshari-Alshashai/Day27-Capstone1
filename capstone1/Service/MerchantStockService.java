package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantService merchantService;
    private final ProductService productService;


    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public int addMerchantStock(MerchantStock merchantStock) {
        for (Product product:productService.getProducts()) {
            if (product.getId().equals(merchantStock.getProductID())) {
                for (Merchant merchant : merchantService.merchants) {
                    if (merchant.getId().equals(merchantStock.getMerchantID())) {
                        merchantStocks.add(merchantStock);
                        return 0;
                    }
                }
                return 1;
            }

        }
        return 2;
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (MerchantStock merchantStock1 : merchantStocks) {
            if (merchantStock1.getId().equals(id)) {
                merchantStocks.set(merchantStocks.indexOf(merchantStock), merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id) {
        for (MerchantStock merchantStock1 : merchantStocks) {
            if (merchantStock1.getId().equals(id)) {
                merchantStocks.remove(merchantStock1);
                return true;
            }
        }
        return false;
    }

    public int addStock(int quantity) {
        for (MerchantStock merchantStock : merchantStocks) {
            for (Merchant merchant : merchantService.merchants) {
                if (merchant.getId().equals(merchantStock.getMerchantID())) {
                    for (Product product : productService.products) {
                        if (product.getId().equals(merchantStock.getProductID())) {
                            merchantStock.setStock(merchantStock.getStock() + quantity);
                            return 0;
                        }
                    }
                    return 1;
                }
                return 2;
            }
        }
        return 3;
    }


    //Extra 3
    public List<Product> filterProducts(String categoryId, Double minPrice, Double maxPrice, boolean inStockOnly) {
        List<Product> filteredProducts = productService.products.stream()
                .filter(product -> product.getCategoryId().equals(categoryId))
                .filter(product -> (minPrice == null || product.getPrice() >= minPrice))
                .filter(product -> (maxPrice == null || product.getPrice() <= maxPrice))
                .collect(Collectors.toList());

        if (inStockOnly) {
            filteredProducts.removeIf(product -> {
                int totalStock = merchantStocks.stream()
                        .filter(stock -> stock.getProductID().equals(product.getId()))
                        .mapToInt(MerchantStock::getStock)
                        .sum();
                return totalStock == 0;
            });
        }

        return filteredProducts;
    }

    public List<Product> sortProducts(String sortBy, int page, int size) {
        List<Product> sortedProducts = new ArrayList<>(productService.products);

        if ("price".equalsIgnoreCase(sortBy)) {
            sortedProducts.sort(Comparator.comparingDouble(Product::getPrice));
        } else if ("name".equalsIgnoreCase(sortBy)) {
            sortedProducts.sort(Comparator.comparing(Product::getName));
        }

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, sortedProducts.size());

        if (startIndex >= sortedProducts.size()) {
            return Collections.emptyList();
        }

        return sortedProducts.subList(startIndex, endIndex);
    }

}
