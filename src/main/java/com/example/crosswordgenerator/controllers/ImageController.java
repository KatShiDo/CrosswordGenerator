package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.models.Image;
import com.example.crosswordgenerator.repositories.IImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

/**
 * Контроллер, отвечающий за доступ к изображениям
 * */
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final IImageRepository imageRepository;

    /**
     * Контроллер, возвращающий изображение по id.
     * @param id id изображения в БД.
     * @return Объект ResponseEntity, описывающий HTTP ответ.
     * */
    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        assert image != null;
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}
