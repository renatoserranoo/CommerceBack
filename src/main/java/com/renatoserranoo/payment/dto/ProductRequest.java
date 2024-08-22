package com.renatoserranoo.payment.dto;

import com.renatoserranoo.payment.entity.Product;

public record ProductRequest(String title, Double price, String description, String image) {
    public ProductRequest(Product product){
        this(product.getTitle(), product.getPrice(), product.getDescription(), product.getImage());
    }
}
