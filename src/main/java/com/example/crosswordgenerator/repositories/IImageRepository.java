package com.example.crosswordgenerator.repositories;

import com.example.crosswordgenerator.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, Long> {
}
