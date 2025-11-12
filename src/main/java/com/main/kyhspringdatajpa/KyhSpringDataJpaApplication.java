package com.main.kyhspringdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class KyhSpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KyhSpringDataJpaApplication.class, args);
    }

}
