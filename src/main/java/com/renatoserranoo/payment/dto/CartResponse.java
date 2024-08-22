package com.renatoserranoo.payment.dto;

import com.renatoserranoo.payment.entity.Product;

public record CartResponse(Long id, Product product, Integer quantity) {
    public CartResponse(Long id, Product product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }
}