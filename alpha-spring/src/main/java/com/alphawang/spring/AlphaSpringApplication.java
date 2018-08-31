package com.alphawang.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan( {"com.alphawang.spring.scope", "com.alphawang.spring.scope.bean"})
public class AlphaSpringApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        log.warn("===> bean count: {}", context.getBeanDefinitionCount());
        for (String name : context.getBeanDefinitionNames()) {
            log.warn("===> bean names: {}", name);
        }
        
        
        SpringApplication.run(AlphaSpringApplication.class, args);
    }
}
