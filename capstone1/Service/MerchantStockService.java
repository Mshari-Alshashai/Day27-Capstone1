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
    public List<Product> sorting(String categoryId, String sortBy, Double minPrice, Double maxPrice, boolean inStockOnly, int page, int size) {
        List<Product> filteredProducts = productService.products.stream()
                .filter(product -> product.getCategoryId().equals(categoryId))
                .filter(product -> (minPrice == null || product.getPrice() >= minPrice))
                .filter(product -> (maxPrice == null || product.getPrice() <= maxPrice))
                .collect(Collectors.toList());

        filteredProducts.removeIf(product -> {
            int totalStock = 0;
            for (MerchantStock stock : merchantStocks) {
                if (stock.getProductID().equals(product.getId())) {
                    totalStock += stock.getStock();
                }
            }
            return inStockOnly && totalStock == 0;
        });

        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "price":
                    filteredProducts.sort(Comparator.comparingDouble(Product::getPrice));
                    break;
                case "name":
                    filteredProducts.sort(Comparator.comparing(Product::getName));
                    break;
            }
        }

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, filteredProducts.size());

        if (startIndex >= filteredProducts.size()) {
            return Collections.emptyList();
        }

        return filteredProducts.subList(startIndex, endIndex);
    }

}
