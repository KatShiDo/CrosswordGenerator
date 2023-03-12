package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository <User, Long> {
    User findByNickname(String nickname);
    User findByEmail(String email);
}
