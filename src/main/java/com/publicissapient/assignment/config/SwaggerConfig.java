package com.publicissapient.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author Nilesh
 *
 */
@EnableWebMvc
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI createSwaggerApi() {
		return new OpenAPI()
				          .info(new Info()
				          .title("PublicisSapientAssignment")
				          .version("1.0.0")
				          .description("Publicis Sapient Assignment"));
	}
}
