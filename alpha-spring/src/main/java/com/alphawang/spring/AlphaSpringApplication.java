package com.alphawang.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.alphawang.spring.scope")
public class AlphaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlphaSpringApplication.class, args);
    }
}
