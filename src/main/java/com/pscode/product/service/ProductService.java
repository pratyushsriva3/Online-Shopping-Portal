package com.pscode.product.service;


import com.pscode.product.dto.Product;
import com.pscode.product.exception.CurrencyNotValidException;
import com.pscode.product.exception.OfferNotValidException;
import com.pscode.product.repository.ProductRepository;
import com.pscode.product.service.config.ProductConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;

    private ProductConfiguration productConfiguration;

    public String addProduct(Product product) {
        log.info("adding product");
        if(product.getPrice() == 0 && product.getDiscount() >0) {
            throw new OfferNotValidException("No discount is allowed at 0 product price");
        }

        if(!productConfiguration.getCurrencies().contains(product.getCurrency().toUpperCase())){
           throw new CurrencyNotValidException("Invalid Currency. Valid Currencies-" + productConfiguration.getCurrencies());
        }

        productRepository.save(product);
        return "success";
    }

    public List<Product> listAllProducts() {

        return productRepository.findAll();
    }

    public List<Product> productCategoryList(String category) {

        return productRepository.findByCategory(category);

    }

    public Product productById(String id) {
        return productRepository.findById(id).get();

    }

    public String updateProduct(Product product) {
        productRepository.save(product);
        return "product updated";
    }

    public String deleteProductById(String id) {
        productRepository.deleteById(id);
        return "product deleted";
    }
}
