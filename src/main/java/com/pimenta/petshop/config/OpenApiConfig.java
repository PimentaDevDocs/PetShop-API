package com.pimenta.petshop.config;

import com.pimenta.petshop.security.SecurityConfig;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PetShop API")
                        .version("1.0.0")
                        .description("Documentação Oficial da API do PetShop")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação Completa")
                        .url("https://github.com/seu-usuario/petshop-api"))
                .addSecurityItem(new SecurityRequirement()
                        .addList(SecurityConfig.SECURITY));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("petShop-api")
                .pathsToMatch("/api/**")
                .packagesToScan("com.pimenta.petshop.controller")
                .addOpenApiMethodFilter(method ->
                        !method.isAnnotationPresent(Deprecated.class))
                .build();
    }
}