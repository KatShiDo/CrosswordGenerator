package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс репозитория images.
 * */
public interface IImageRepository extends JpaRepository<Image, Long> {
}
