package com.ufrn.imd.cinema.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cinemaOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Absolute Cinema API")
                        .description("API para gerenciamento de cinema: filmes, sessoes, ingressos, clientes e demais entidades.")
                        .version("v1"));
    }
}
