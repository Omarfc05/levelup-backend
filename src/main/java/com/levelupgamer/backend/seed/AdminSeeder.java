package com.levelupgamer.backend.seed;

import com.levelupgamer.backend.models.User;
import com.levelupgamer.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        repo.findByEmail("admin@levelup.cl").orElseGet(() -> {
            User u = new User();
            u.setName("Admin LevelUp");
            u.setEmail("admin@levelup.cl");
            u.setPasswordHash(encoder.encode("admin123"));
            u.setRole(User.Role.ADMIN);
            return repo.save(u);
        });
    }
}
