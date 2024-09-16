package com.renatoserranoo.payment.controller;

import com.renatoserranoo.payment.dto.CartItemRequest;
import com.renatoserranoo.payment.dto.CartResponse;
import com.renatoserranoo.payment.entity.User;
import com.renatoserranoo.payment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CartResponse>> addToCart(@RequestBody CartItemRequest cartItemRequest, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<CartResponse> updatedCart = cartService.addToCart(userId, cartItemRequest);
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/sync")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CartResponse>> syncCart(@RequestBody List<CartItemRequest> cartItemRequest, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<CartResponse> updatedCart = cartService.syncCart(userId, cartItemRequest);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CartResponse>> getCart(Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<CartResponse> cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CartResponse>> removeFromCart(@RequestParam Long itemId, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<CartResponse> cart = cartService.removeFromCart(userId, itemId);
        return ResponseEntity.ok(cart);
    }
}
