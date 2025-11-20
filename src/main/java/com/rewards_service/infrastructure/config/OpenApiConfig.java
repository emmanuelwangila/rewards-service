package com.rewards_service.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(servers())
                .components(components())
                .security(securityRequirements())
                .externalDocs(externalDocumentation())
                .tags(tags());
    }

    private Info apiInfo() {
        return new Info()
                .title("Mini Rewards Service API")
                .description("A comprehensive rewards system API built with Clean Architecture principles. " +
                           "Features Redis caching, RabbitMQ messaging, rate limiting, and enterprise-grade security. " +
                           "Supports user point management, tier-based rewards, and real-time event processing.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Rewards Service Development Team")
                        .email("dev@rewards-service.com")
                        .url("https://github.com/emmanuelwangila/rewards-service"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));
                
    }

    private List<Server> servers() {
        return List.of(
                new Server()
                        .url("http://localhost:8080" + contextPath)
                        .description("Local Development Server"),
                new Server()
             
        );
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes("ApiKeyAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-API-Key")
                                .description("API Key for authentication. Contact administrator to obtain your API key."))
                .addSecuritySchemes("BearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT token for authenticated users"));
    }

    private List<SecurityRequirement> securityRequirements() {
        return List.of(
                new SecurityRequirement().addList("ApiKeyAuth")
        );
    }

    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("Complete API Documentation and Guides");
                
    }

    private List<Tag> tags() {
        return List.of(
                new Tag().name("Rewards Management").description("Operations for awarding and redeeming points"),
                new Tag().name("User Management").description("User account and profile operations"),
                new Tag().name("Analytics").description("Reporting and analytics endpoints"),
                new Tag().name("Health").description("System health and monitoring endpoints")
        );
    }
}