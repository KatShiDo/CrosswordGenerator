package com.example.crosswordgenerator.configurations;

import com.example.crosswordgenerator.services.ImageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    @Bean
    public CommandLineRunner addImages(ImageService imageService){
        return (args)->{

        };
    }
}
