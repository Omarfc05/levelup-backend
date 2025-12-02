package com.levelupgamer.backend.controllers;

import com.levelupgamer.backend.jwt.JwtUtil;
import com.levelupgamer.backend.models.User;
import com.levelupgamer.backend.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
        if (users.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email ya registrado");
        }

        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPasswordHash(encoder.encode(dto.getPassword()));
        u.setRole(User.Role.USER); // siempre usuario normal al registrarse

        users.save(u);

        // devolvemos el usuario (podrías devolver también el token si quieres login automático)
        return ResponseEntity.status(201).body(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        Optional<User> opt = users.findByEmail(dto.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        User u = opt.get();
        if (!encoder.matches(dto.getPassword(), u.getPasswordHash())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        String token = jwt.generateToken(u.getEmail(), u.getRole().name());

        AuthResponse res = new AuthResponse();
        res.setToken(token);
        res.setUser(u);

        return ResponseEntity.ok(res);
    }
}

@Data
class RegisterDTO {
    private String name;
    private String email;
    private String password;
}

@Data
class LoginDTO {
    private String email;
    private String password;
}

@Data
class AuthResponse {
    private String token;
    private User user;
}
