package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.repositories.ICrosswordRepository;
import com.example.crosswordgenerator.repositories.IUserRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

/**
 * Класс, представляющий операции для работы с кроссвордами.
 * */
@Service
@Slf4j
public class CrosswordService {

    private final ICrosswordRepository crosswordRepository;
    private final IUserRepository userRepository;
    private final EntityManager entityManager;

    public CrosswordService(ICrosswordRepository crosswordRepository, IUserRepository userRepository, EntityManager entitymanager) {
        this.crosswordRepository = crosswordRepository;
        this.userRepository = userRepository;
        this.entityManager = entitymanager;
    }

    /**
     * Получить все кроссворды.
     * @return список всех кроссвордов.
     * */
    public Iterable<Crossword> getAll() {
        return crosswordRepository.findAll();
    }

    /**
     * Сохранить кроссворд.
     * @param crossword кроссворд
     * */
    public Crossword save(Crossword crossword) {
        log.info("Saving new Crossword. Title: {}", crossword.getTitle());
        crossword = crosswordRepository.save(crossword);
        return crossword;
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
        return userRepository.findByUsername(principal.getName()).orElse(null);
    }
    /**
     * Удаолить кроссворд.
     * @param id id кроссворда, который должен быть удален.
     * */
    public boolean delete(Long id) {
        if(crosswordRepository.findById(id).isPresent()){
            log.info("Crossword with id {} deleted.", id);
            crosswordRepository.deleteById(id);
            return true;
        }else{
            log.info("Crossword with id {} not deleted because it doesn't exist", id);
            return false;
        }
    }
    /**
     * Возвращает кроссворд из базы данных по id.
     * @param id id кроссворда.
     * @return Объект класса Сrossword.
     * */
    public Crossword getById(Long id) {
        return crosswordRepository.findById(id).orElse(null);
    }

    public boolean update(Crossword crossword){
        if(crosswordRepository.findById(crossword.getId()).isPresent()){
            crosswordRepository.save(crossword);
            return true;
        }else return false;
    }

    @Transactional
    public boolean increaseSolvedCount(Long id){
        Crossword cw = crosswordRepository.findById(id).orElse(null);
        if(cw != null){
            cw.setSolved(cw.getSolved() + 1);
            crosswordRepository.save(cw);
            return true;
        }else return false;
    }
}
