package com.renatoserranoo.payment.dto;

import com.renatoserranoo.payment.entity.Product;

public record ProductResponse(Long id, String title, Double price, String description, String image) {
    public ProductResponse(Product product){
        this(product.getId(), product.getTitle(), product.getPrice(), product.getDescription(), product.getImage());
    }
}
