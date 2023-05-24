package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Интрфейс репозитория users.
 * */
public interface IUserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
