package com.example.sharediary.global.config.swaggerConfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class SwaggerConfig {

    @Value("${myapp.api-url}")
    private String prodUrl;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("ShareDiary API Document")
                .version("v0.0.1")
                .description("ShareDiary 문서");

        Server prodServer = new Server();
        prodServer.setDescription("Production Server");
        prodServer.setUrl(prodUrl);

        Server localServer = new Server();
        localServer.setDescription("Development Server");
        localServer.setUrl("http://localhost:8080");

        return new OpenAPI()
                .info(info)
                .servers(Arrays.asList(prodServer, localServer));
    }
}
