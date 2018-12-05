package com.snailmann.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class RouterWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouterWebfluxApplication.class, args);
    }
}
