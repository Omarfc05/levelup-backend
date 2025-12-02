package com.levelupgamer.backend.controllers;

import com.levelupgamer.backend.models.User;
import com.levelupgamer.backend.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    // LISTAR TODOS LOS USUARIOS
    @GetMapping
    public List<User> list() {
        return users.findAll();
    }

    // CREAR USUARIO
    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserDTO dto) {
        // validar que no exista el correo
        if (users.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPasswordHash(encoder.encode(dto.getPassword()));

        User.Role role;
        try {
            role = User.Role.valueOf(dto.getRole());
        } catch (IllegalArgumentException ex) {
            role = User.Role.USER; // fallback
        }
        u.setRole(role);

        User saved = users.save(u);
        return ResponseEntity
                .created(URI.create("/api/admin/users/" + saved.getId()))
                .body(saved);
    }

    // CAMBIAR ROL
    @PutMapping("/{id}/role")
    public ResponseEntity<User> updateRole(@PathVariable Long id,
                                           @RequestBody UpdateRoleDTO dto) {
        var opt = users.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final User.Role newRole;
        try {
            newRole = User.Role.valueOf(dto.getRole());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }

        User u = opt.get();
        u.setRole(newRole);
        User saved = users.save(u);

        return ResponseEntity.ok(saved);
    }

    // ELIMINAR USUARIO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!users.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        users.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

@Data
class UpdateRoleDTO {
    private String role; // "USER" o "ADMIN"
}

@Data
class CreateUserDTO {
    private String name;
    private String email;
    private String password;
    private String role; // "USER" o "ADMIN"
}
