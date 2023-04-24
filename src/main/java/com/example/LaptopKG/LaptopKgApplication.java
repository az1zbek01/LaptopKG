package com.example.LaptopKG;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass=true)
@OpenAPIDefinition(info = @Info(title = "Laptop KG", version = "1.0", description = "basic functional"))
@SecurityScheme(
		name = "JWT",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class LaptopKgApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopKgApplication.class, args);
	}
}
