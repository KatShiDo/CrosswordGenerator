package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.repositories.CrosswordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrosswordService {

    private final CrosswordRepository crosswordRepository;

    public List<Crossword> getAll() {
        return crosswordRepository.findAll();
    }

    public void save(Crossword crossword) {
        log.info("Saving new {}", crossword);
        crosswordRepository.save(crossword);
    }

    public void delete(Long id) {
        crosswordRepository.deleteById(id);
    }

    public Crossword getById(Long id) {
        return crosswordRepository.findById(id).orElse(null);
    }
}
