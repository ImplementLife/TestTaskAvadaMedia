package com.testTask.kinoCMS.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        Info info = new Info();
        info.title("Test task for AVADA MEDIA.");
        info.version("1.0.0");
        info.description("This application created for AVADA MEDIA. It is test task.");
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(info);
        return openAPI;
    }
}
