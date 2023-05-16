package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.enums.Role;
import com.example.crosswordgenerator.models.Image;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * Класс, предоставляющий операции для работы с кроссвордами
 * */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Получить всех пользователей.
     * @return Список объектов класса User
     * */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Создать запись о пользователе.
     * @param user объект класса User, представляющий пользователя
     * @return Возвращает true, если пользователь был успешно создан, и false, если пользователь с таким именем или почтой уже есть в системе.
     * */
    public boolean create(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null || userRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_ADMIN);
        log.info("Saving new User. Username: {}", user.getUsername());
        userRepository.save(user);
        return true;
    }

    /**
     * Обновить информацию о пользователе.
     * @param user пользователь, информация о котором будет обновлена
     * @param file объект, представляющий файл с изображением, загруженный на сервер
     **/
    public void update(User user, MultipartFile file) throws IOException {
        Image image;
        if (file.getSize() != 0) {
            image = toImageEntity(file);
            user.setAvatar(image);
        }
        log.info("Updating User. Username: {}", user.getUsername());
        userRepository.save(user);
    }

    /**
     * Удалить пользователя по его ID.
     * @param id идентификатор пользователя, которого необходимо удалить.
     * */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Найти пользователя по его id.
     * @param id идентификатор пользователя.
     * @return Объект, представляющий найденного пользователя, или null
     * */
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("User banned with id {}; username: {}", id, user.getUsername());
            }
            else {
                user.setActive(true);
                log.info("User unbanned with id {}; username: {}", id, user.getUsername());
            }
        }
        userRepository.save(user);
    }

    /**
     * Преобразовать загруженный на сервер файл к сущности Image, которая используется для хранения изображений в базе данных users.
     * @param file загруженный на сервер файл.
     * @return Объект класса Image, представляющий изображение
     * */
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
