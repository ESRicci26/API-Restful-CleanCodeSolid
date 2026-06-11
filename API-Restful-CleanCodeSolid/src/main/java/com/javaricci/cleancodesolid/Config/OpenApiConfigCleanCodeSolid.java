package com.javaricci.cleancodesolid.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfigCleanCodeSolid {

	@Bean
	public OpenAPI customOpenApi() {

		return new OpenAPI().info(new Info().title("API Fornecedores Clean Code SOLID")
				.description("Cadastro de fornecedores com Spring JDBC").version("2.0"));
	}
}