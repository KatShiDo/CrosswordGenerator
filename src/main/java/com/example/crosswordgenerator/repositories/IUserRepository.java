package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интрфейс репозитория users.
 * */
public interface IUserRepository extends JpaRepository <User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
