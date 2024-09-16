package com.renatoserranoo.payment.controller;

import com.renatoserranoo.payment.dto.ProductRequest;
import com.renatoserranoo.payment.dto.ProductResponse;
import com.renatoserranoo.payment.entity.Product;
import com.renatoserranoo.payment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductRequest product){
        Product productCreated = productService.saveProduct(product);
        return ResponseEntity.status(201).body(productCreated);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductResponse>> findById(@PathVariable Long id){
        Optional<ProductResponse> product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String query) {
        return productService.searchProducts(query);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*")
    @PutMapping
    public ResponseEntity<Product> update(@RequestBody ProductRequest product){
        Product productUpdated = productService.updateProduct(product);
        return ResponseEntity.ok(productUpdated);
    }
}
