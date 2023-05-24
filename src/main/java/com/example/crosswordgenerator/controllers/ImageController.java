package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.models.Image;
import com.example.crosswordgenerator.repositories.IImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер, отвечающий за доступ к изображениям
 * */
@RestController
@Slf4j
public class ImageController {

    private final IImageRepository imageRepository;

    public ImageController(IImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Контроллер, возвращающий изображение по id.
     * @param id id изображения в БД.
     * @return Объект ResponseEntity, описывающий HTTP ответ.
     * */
    @GetMapping("/images/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

    @PutMapping("/images")
    public ResponseEntity<?> addImage(@RequestParam MultipartFile imageFile){
        Image image = new Image();
        try{
            image.setBytes(imageFile.getBytes());
            image.setOriginalFileName(imageFile.getOriginalFilename());
            String mime = java.nio.file.Files.probeContentType((new File(image.getOriginalFileName())).toPath());
            image.setContentType(mime);
        }catch(IOException e){
            log.error(e.getMessage());
        }
        imageRepository.save(image);
        Map<String, Long> response = new HashMap<>();
        response.put("id", image.getId());
        return ResponseEntity.ok().body(response);
    }
}
