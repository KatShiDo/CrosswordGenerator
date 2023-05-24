package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.WordEntity;
import com.example.crosswordgenerator.repositories.IWordEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class WordEntityService {
    private IWordEntityRepository wordEntityRepository;

    public WordEntityService(IWordEntityRepository wordEntityRepository) {
        this.wordEntityRepository = wordEntityRepository;
    }

    public void saveWord(WordEntity word){
        wordEntityRepository.save(word);
    }
}
