package org.walmart.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * This class is used to configure Swagger Documentation for the REST APIs which are present inside the base package
 * org.walmart.controllers
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.walmart.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

    public ApiInfo metaInfo(){

        ApiInfo apiInfo = new ApiInfo("Walmart Labs REST Api",
                "Swagger Documentation for walmart-labs-rest-api project",
                "1.0",
                "Terms of Service",
                 new Contact("Nitish Nalan", "https://github.com/nitishnalan",
                         "nxl170130@gmail.com"),
                "Apache License v2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());

        return apiInfo;
    }
}
