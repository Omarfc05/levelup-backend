package com.levelupgamer.backend.repositories;

import com.levelupgamer.backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
