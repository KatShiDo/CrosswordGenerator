package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.Crossword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Интерфейс репозитория crosswords.
 * */
public interface ICrosswordRepository extends CrudRepository<Crossword, Long> {
}
