package com.ramennsama.springboot.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Mini E-Commerce System API",
                version = "1.0.0",
                description = "API Documentation for Mini E-Commerce System. " +
                        "Provides endpoints for managing users, products, orders and payments.",
                contact = @Contact(
                        name = "RamenNSama",
                        email = "contact@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development Server",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production Server",
                        url = "https://api.example.com"
                )
        }
)
public class OpenApiConfig {
}
