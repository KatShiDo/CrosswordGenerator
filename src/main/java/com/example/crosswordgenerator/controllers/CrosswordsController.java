package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.services.CrosswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * Контроллер, отвечающий за взаимодействие пользователя с кроссвордами
 * */
@Controller
@RequiredArgsConstructor
public class CrosswordsController {

    private final CrosswordService crosswordService;

    /**
     * Контроллер для доступа к главной странице.
     * @param model объект для предоставления данных в механизм шаблонов.
     * @return Имя представления
     * */
    @GetMapping("/")
    public String crosswords(Model model) {

        model.addAttribute("crosswords", crosswordService.getAll());
        return "crosswordsView";
    }

    /**
     * Контроллер для доступа к сервису создания кроссворда.
     * @param principal текущий зарегистрированный пользователь.
     * @param crossword объект, представляющий кроссворд
     * @return Имя представления
     * */
    @PostMapping("/crossword/create")
    public String createCrossword(Crossword crossword, Principal principal) {
        crosswordService.save(principal, crossword);
        return "redirect:/";
    }

    /**
     * Контроллер для удаления кроссворда.
     * @param id id кроссворда в БД.
     * @return Имя представления
     * */
    @PostMapping("/crossword/delete/{id}")
    public String deleteCrossword(@PathVariable Long id) {
        crosswordService.delete(id);
        return "redirect:/";
    }

//    @GetMapping("/crossword/{id}")
//    public String solveCrossword(@PathVariable Long id, Model model) {
//
//    }
}
