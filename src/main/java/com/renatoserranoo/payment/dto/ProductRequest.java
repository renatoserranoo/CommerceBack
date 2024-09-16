package com.renatoserranoo.payment.dto;

import com.renatoserranoo.payment.entity.Product;

public record ProductRequest(Long id, String title, Double price, String description, String image, String category) {
    public ProductRequest(Product product){
        this(product.getId(), product.getTitle(), product.getPrice(), product.getDescription(),
                product.getImage(), product.getCategory());
    }
}
