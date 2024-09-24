package com.example.api.config;

import com.example.api.domain.User;
import com.example.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void startDB() {
        User us = new User(null, "Andre", "andre@gmail.com", "321");
        User us2 = new User(null, "Valdir", "valdir@gmail.com", "123");

        repository.saveAll(List.of(us, us2));
    }
}