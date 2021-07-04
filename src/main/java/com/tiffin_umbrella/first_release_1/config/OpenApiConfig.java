package com.tiffin_umbrella.first_release_1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .description("Local")
                        .url("http://localhost:" + serverPort + contextPath))
                .addServersItem(new Server()
                        .description("Production")
                        .url("https://tiffin-umbrella.herokuapp.com" + contextPath));
    }
}
