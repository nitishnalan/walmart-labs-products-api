package org.walmart.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(value = {"org.walmart.services","org.walmart.repository", "org.walmart.models"})
@EnableJpaRepositories("org.walmart.repository")
@EntityScan("org.walmart.models")
public class RestApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestApiApplication.class, args);
    }

}
