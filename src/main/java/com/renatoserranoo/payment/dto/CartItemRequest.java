package com.renatoserranoo.payment.dto;

import com.renatoserranoo.payment.entity.Cart;
import com.renatoserranoo.payment.entity.CartItem;
import com.renatoserranoo.payment.entity.Product;

public record CartItemRequest(Long productId, Integer quantity, Long cartId) {

    public CartItem toCartItem(Product product, Cart cart) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(this.quantity);
        cartItem.setCart(cart);
        return cartItem;
    }
}
