package com.github.jaguzmanb1.quasar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Main class for the Quasar project.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class }) public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}