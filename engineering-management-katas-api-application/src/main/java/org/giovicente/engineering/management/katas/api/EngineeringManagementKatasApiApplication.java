package org.giovicente.engineering.management.katas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.giovicente.engineering.management.katas")
@EntityScan(basePackages = "org.giovicente.engineering.management.katas.api.adapter.persistence.entity")
public class EngineeringManagementKatasApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineeringManagementKatasApiApplication.class, args);
    }
}
