package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.Crossword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс репозитория crosswords.
 * */
public interface ICrosswordRepository extends JpaRepository<Crossword, Long> {
}
