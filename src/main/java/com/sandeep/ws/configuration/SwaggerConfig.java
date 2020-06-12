package com.sandeep.ws.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.sandeep.ws.controller")).paths(regex("/m1.*")).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("This microservice is known as micro1-ws", "It Expose two http methods, one get and one post. get method return “Up” if service is up. The post method return the concatenated responses of the Get call of Service 2 (micro2-ws) and the Post call of Service 3 (micro3-ws) using the same payload.", null, null,
				new Contact(null,null,"sandeep.ejlnoqe@company.com"), null, null,
				Collections.emptyList());
	}
}
