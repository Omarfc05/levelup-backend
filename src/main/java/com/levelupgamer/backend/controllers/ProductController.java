package com.levelupgamer.backend.controllers;

import com.levelupgamer.backend.models.Product;
import com.levelupgamer.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repo;

    @GetMapping
    public List<Product> list(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> one(@PathVariable Long id){
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
