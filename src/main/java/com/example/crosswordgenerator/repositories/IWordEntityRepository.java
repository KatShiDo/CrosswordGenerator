package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.WordEntity;
import org.springframework.data.repository.CrudRepository;

public interface IWordEntityRepository extends CrudRepository<WordEntity, Long> {

}
