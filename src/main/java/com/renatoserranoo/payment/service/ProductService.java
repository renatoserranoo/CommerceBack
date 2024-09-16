package com.renatoserranoo.payment.service;

import com.renatoserranoo.payment.dto.ProductRequest;
import com.renatoserranoo.payment.dto.ProductResponse;
import com.renatoserranoo.payment.entity.Product;
import com.renatoserranoo.payment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(ProductRequest data){
        Product productData = new Product(data);
        return productRepository.save(productData);
    }

    public Optional<ProductResponse> findById(Long id){
        return productRepository.findById(id).map(ProductResponse::new);
    }

    public List<ProductResponse> findAll(){
        return productRepository.findAll().stream().map(ProductResponse::new).toList();
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByTitleContainingIgnoreCase(query);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(ProductRequest data){
        Product productData = new Product(data);
        return productRepository.save(productData);
    }
}
