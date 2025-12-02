package com.levelupgamer.backend.controllers;

import com.levelupgamer.backend.models.Product;
import com.levelupgamer.backend.repositories.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductRepository repo;

    // LISTAR (puedes seguir usando el público, pero este sirve igual)
    @GetMapping
    public List<Product> list() {
        return repo.findAll();
    }

    // CREAR
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO dto) {
        Product p = new Product();
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setCategory(dto.getCategory());
        p.setPrice(dto.getPrice());
        p.setImgSource(dto.getImageSrc()); // ojo: DTO usa imageSrc, entidad usa imgSource

        Product saved = repo.save(p);
        return ResponseEntity
                .created(URI.create("/api/admin/products/" + saved.getId()))
                .body(saved);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return repo.findById(id)
                .map(p -> {
                    p.setTitle(dto.getTitle());
                    p.setDescription(dto.getDescription());
                    p.setCategory(dto.getCategory());
                    p.setPrice(dto.getPrice());
                    p.setImgSource(dto.getImageSrc());
                    Product updated = repo.save(p);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

@Data
class ProductDTO {
    private String title;
    private String description;
    private String category;
    private Integer price;
    private String imageSrc;
}
