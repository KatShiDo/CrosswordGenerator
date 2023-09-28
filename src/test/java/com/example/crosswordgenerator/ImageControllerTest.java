package com.example.crosswordgenerator;

import com.example.crosswordgenerator.controllers.ImageController;
import com.example.crosswordgenerator.models.Image;
import com.example.crosswordgenerator.repositories.IImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ImageControllerTest {
    @Test
    @DisplayName("Test retrieving image by its ID")
    public void testGetImageById() throws Exception{
        IImageRepository repo = mock(IImageRepository.class);
        ImageController imageController = new ImageController(repo);
        Image image = new Image();
        image.setBytes("image".getBytes());
        image.setContentType("image/jpeg");
        image.setOriginalFileName("original_name");
        doReturn(Optional.of(image)).when(repo).findById(10L);
        ResponseEntity<?> result = imageController.getImageById(10L);
        assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(new String(((InputStreamResource) result.getBody()).getInputStream().readAllBytes()), "image");
        assertEquals(result.getHeaders().getContentType(), MediaType.IMAGE_JPEG);
        assertEquals(result.getHeaders().get("fileName").get(0), "original_name");
    }

    @Test
    @DisplayName("Trying to load image with id that doesn't exists")
    public void testGetImageByNotExistedId(){
        IImageRepository repo = mock(IImageRepository.class);
        ImageController imageController = new ImageController(repo);
        doReturn(Optional.empty()).when(repo).findById(1L);
        ResponseEntity<?> result = imageController.getImageById(1L);
        assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(404));
        assertEquals(result.getBody(), null);
    }

    @Test
    @DisplayName("Trying to save image with success result")
    public void testAddImage()throws IOException {
        IImageRepository repo = mock(IImageRepository.class);
        ImageController imageController = new ImageController(repo);
        MultipartFile file = mock(MultipartFile.class);
        doReturn("image".getBytes()).when(file).getBytes();
        doReturn("file.jpeg").when(file).getOriginalFilename();

        doAnswer((invocation) ->{
            Image image = invocation.getArgument(0, Image.class);
            image.setId(1L);
            return null;
        }).when(repo).save(any(Image.class));
        ResponseEntity<?> response = imageController.addImage(file);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(((Map<String, Long>)response.getBody()).get("id"), 1L);
        Image persisted = new Image();
        persisted.setOriginalFileName("file.jpeg");
        persisted.setContentType("image/jpeg");
        persisted.setBytes("image".getBytes());
        persisted.setId(1L);
        verify(repo).save(persisted);
    }

    @Test
    @DisplayName("trying to save image with unsuccessful result")
    public void testAddImageUnsuccessfulFlow() throws IOException{
        IImageRepository repo = mock(IImageRepository.class);
        ImageController imageController = new ImageController(repo);
        MultipartFile file = mock(MultipartFile.class);
        doThrow(new IOException()).when(file).getBytes();
        ResponseEntity<?> response = imageController.addImage(file);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(400));
    }
}
