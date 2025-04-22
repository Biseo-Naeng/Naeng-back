package com.naeng_biseo.naeng_biseo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server.local}")
    private String localServer;

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(apiSecurityRequirement())
                .components(apiComponents());

        for (Server server : apiServers()) {
            openAPI.addServersItem(server);
        }

        return openAPI;
    }

    private Info apiInfo() {
        return new Info()
                .title("naeng-biseo-api")
                .version("1.0")
                .description("API Documentation with JWT Authentication");
    }

    private SecurityRequirement apiSecurityRequirement() {
        return new SecurityRequirement().addList("Bearer Authentication");
    }

    private Components apiComponents() {
        return new Components()
                .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
    }

    private List<Server> apiServers() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url(localServer).description("Local Server"));
        return servers;
    }
}
