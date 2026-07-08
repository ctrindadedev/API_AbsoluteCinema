package com.ufrn.imd.cinema.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cinemaOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Absolute Cinema API")
                        .description("API para gerenciamento de cinema: filmes, sessões, ingressos, clientes e demais entidades.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Absolute Cinema")
                                .url("https://github.com/ctrindadedev/API_AbsoluteCinema"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
