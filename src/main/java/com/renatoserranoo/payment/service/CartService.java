package com.renatoserranoo.payment.service;

import com.renatoserranoo.payment.dto.CartItemRequest;
import com.renatoserranoo.payment.dto.CartResponse;
import com.renatoserranoo.payment.entity.Cart;
import com.renatoserranoo.payment.entity.CartItem;
import com.renatoserranoo.payment.entity.Product;
import com.renatoserranoo.payment.entity.User;
import com.renatoserranoo.payment.repository.CartRepository;
import com.renatoserranoo.payment.repository.ProductRepository;
import com.renatoserranoo.payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartResponse> getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        return cart.getItems().stream()
                .map(item -> new CartResponse(item.getId(), item.getProduct(), item.getQuantity()))
                .collect(Collectors.toList());
    }

    public List<CartResponse> addToCart(Long userId, CartItemRequest cartItemRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);  // Salva o novo carrinho
        }

        Product product = productRepository.findById(cartItemRequest.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem newItem = cartItemRequest.toCartItem(product, cart);

        cart.addItem(newItem);

        Cart updatedCart = cartRepository.save(cart);

        return updatedCart.getItems().stream()
                .map(item -> new CartResponse(item.getId(), item.getProduct(), item.getQuantity()))
                .collect(Collectors.toList());
    }

    public List<CartResponse> removeFromCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        cart.getItems().removeIf(item -> item.getId().equals(itemId));

        Cart updatedCart = cartRepository.save(cart);

        return updatedCart.getItems().stream()
                .map(item -> new CartResponse(item.getId(), item.getProduct(), item.getQuantity()))
                .collect(Collectors.toList());
    }
}
