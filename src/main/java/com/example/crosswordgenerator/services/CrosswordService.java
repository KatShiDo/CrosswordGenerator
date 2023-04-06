package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.repositories.ICrosswordRepository;
import com.example.crosswordgenerator.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Класс, представляющий операции для работы с кроссвордами.
 * */
@Service
@Slf4j
@RequiredArgsConstructor
public class CrosswordService {

    private final ICrosswordRepository crosswordRepository;
    private final IUserRepository userRepository;

    /**
     * Получить все кроссворды.
     * @return список всех кроссвордов.
     * */
    public List<Crossword> getAll() {
        return crosswordRepository.findAll();
    }

    /**
     * Сохранить кроссворд.
     * @param principal текущий зарегистрированный пользователь.
     * @param crossword кроссворд
     * */
    public void save(Principal principal, Crossword crossword) {
        crossword.setUser(getUserByPrincipal(principal));
        log.info("Saving new Crossword. Title: {}", crossword.getTitle());
        crosswordRepository.save(crossword);
    }

    /**
     * Извлекает из базы данных текущего зарегистрированного пользователя.
     * @param principal текущий зарегистрированный пользователь.
     * @return Объект, представляющий пользователя
     * */
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepository.findByUsername(principal.getName());
    }
    /**
     * Удаолить кроссворд.
     * @param id id rроссворда, который должен быть удален.
     * */
    public void delete(Long id) {
        crosswordRepository.deleteById(id);
    }
    /**
     * Возвращает кроссворд из базы данных по id.
     * @param id id кроссворда.
     * @return Объект класса Сrossword.
     * */
    public Crossword getById(Long id) {
        return crosswordRepository.findById(id).orElse(null);
    }
}
