package com.levelupgamer.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "LevelUp Gamer API",
        version = "1.0.0",
        description = "API REST para usuarios y productos de LevelUp Gamer (Spring Boot + MySQL + JWT)"
    )
)
public class OpenApiConfig {
}
