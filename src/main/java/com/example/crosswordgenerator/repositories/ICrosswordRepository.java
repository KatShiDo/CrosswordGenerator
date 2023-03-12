package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.Crossword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICrosswordRepository extends JpaRepository<Crossword, Long> {
}
