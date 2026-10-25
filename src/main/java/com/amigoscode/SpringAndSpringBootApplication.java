package com.amigoscode;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@SpringBootApplication
public class SpringAndSpringBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(
                SpringAndSpringBootApplication.class,
                args
        );
//        String[] beanDefinitionNames = context.getBeanDefinitionNames();
//        for(String beanDef : beanDefinitionNames) {
//            System.out.println(beanDef);
//        }
//        System.out.println(beanDefinitionNames.length);
    }

//    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//    @SessionScope
//    @ApplicationScope
    @Bean("red-bean-object")
    public String redBean() {
        return "Manchester United";
    }

    @Bean
    public String blueBean() {
        return "Manchester City";
    }

    @Bean
    CommandLineRunner cmdLineRunner(String blueBean, UserService userService) {
        return args -> {
            System.out.println("Hello from command line runner");
            System.out.println(blueBean);
            System.out.println(userService.getUser());
        };
    }

    public record User(int id, String name) {}

    @Service
    public class UserService {

        @PostConstruct
        public void init(){
            System.out.println("fill redis cache...");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("clear redis cache...");
        }

        public List<User> getUser(){
            return List.of(
                    new User(1, "John"),
                    new User(2, "Blade")
            );
        }
    }
}
