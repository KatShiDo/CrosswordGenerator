package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.Crossword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrosswordRepository extends JpaRepository<Crossword, Long> {
}
