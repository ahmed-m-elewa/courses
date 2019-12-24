package com.sypron.courses;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {

		ParameterBuilder parameterBuilder = new ParameterBuilder();
		Parameter authHeader = parameterBuilder.name("Authorization")
				.modelRef(new ModelRef("string"))
				.parameterType("header").required(false)
				.build();

		java.util.List<Parameter> parametersList = new ArrayList<>();
		parametersList.add(authHeader);

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().pathMapping("").globalOperationParameters(parametersList);
	}

}