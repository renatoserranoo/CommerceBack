package com.renatoserranoo.payment.entity;

import com.renatoserranoo.payment.dto.ProductRequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
@Table(name = "products")
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String image;

    public Product(Long id, String title, Double price, String description, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Product() {
    }

    public Product(ProductRequest data) {
        this.title = data.title();
        this.price = data.price();
        this.description = data.description();
        this.image = data.image();
    }
}
