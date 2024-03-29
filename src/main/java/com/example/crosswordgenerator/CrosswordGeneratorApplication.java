package com.example.crosswordgenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.InetAddress;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
public class CrosswordGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrosswordGeneratorApplication.class, args);
    }
}
