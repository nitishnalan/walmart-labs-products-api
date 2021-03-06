package org.walmart.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(value = {"org.walmart.services","org.walmart.repository", "org.walmart.models","org.walmart.controllers","org.walmart.helpers"})
@EnableJpaRepositories("org.walmart.repository")
@EntityScan("org.walmart.models")
@EnableCaching
@EnableScheduling

public class RestApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestApiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public CacheManager cachingManager() {
        return new ConcurrentMapCacheManager("cacheProducts");
    }

}
