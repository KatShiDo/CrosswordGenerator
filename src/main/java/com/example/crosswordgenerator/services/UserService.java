package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.enums.Role;
import com.example.crosswordgenerator.models.Image;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean create(User user) {
        if (userRepository.findByNickname(user.getNickname()) != null) {
            return false;
        }
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        log.info("Saving new User. Nickname: {}", user.getNickname());
        return true;
    }

    public void update(User user, MultipartFile file) throws IOException {
        Image image;
        if (file.getSize() != 0) {
            image = toImageEntity(file);
            user.setAvatar(image);
        }
        log.info("Updating User. Name: {}", user.getNickname());
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
}
