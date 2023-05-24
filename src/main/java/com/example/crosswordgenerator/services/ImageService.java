package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.Image;
import com.example.crosswordgenerator.repositories.IImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private IImageRepository imageRepository;


    public ImageService(IImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void addImage(Image image){
        imageRepository.save(image);
    }

    public boolean deleteImage(Long id){
        if(imageRepository.findById(id).isPresent()){
            imageRepository.deleteById(id);
            return true;
        }else return false;
    }

    public Image getImageById(Long id){
        return imageRepository.findById(id).orElse(null);
    }
}
