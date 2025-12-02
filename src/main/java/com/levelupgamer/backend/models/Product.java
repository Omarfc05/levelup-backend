package com.levelupgamer.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(length = 50)
    private String category;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 255)
    private String imgSource;
}
