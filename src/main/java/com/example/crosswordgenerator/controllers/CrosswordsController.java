package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.cwbuilder.CrosswordBuilder;
import com.example.crosswordgenerator.cwbuilder.CrosswordResult;
import com.example.crosswordgenerator.cwbuilder.InputWords;
import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.services.CrosswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

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
        List<Crossword> crosswords = crosswordService.getAll();
        System.out.println(crosswords);
        InputWords iw = new InputWords();
        model.addAttribute("wordsobj", iw);
        model.addAttribute("width", 20);
        model.addAttribute("height", 20);
        return "generateView";
    }

    @GetMapping("/create")
    public String createCrosswordOutOfWords(InputWords iw, Model model){
        CrosswordBuilder builder = new CrosswordBuilder();
        List<String> words = new LinkedList<>();
        for(String word:iw.getWords().split("[^a-zA-Zа-яА-Я0-9]"))
            words.add(word);
        CrosswordResult cw = builder.buildCrosswordOutOfWords(words);
        if(cw.getStatus() == 0){
            model.addAttribute("status", 0);
            String[][] cwInArray = cw.getCrossword();
            int height = cwInArray.length, width = cwInArray[0].length;
            System.out.println("Generated crossword: ");
            for(int i=0;i<width;i++) {
                for (int k = 0; k < height; k++)
                    System.out.print(cwInArray[k][i]);
                System.out.print("\n");
            }

            List<String> cwInList = new LinkedList<>();
            for(int i=0;i < width;i++)
                for (int k = 0; k < height; k++)
                    cwInList.add(cwInArray[k][i]);
            model.addAttribute("crossword", cwInList);
            model.addAttribute("wordsCount", words.size());
            model.addAttribute("width", cwInArray.length);
            model.addAttribute("height", cwInArray[0].length);
        }else System.out.println("Status is not 0!");
        model.addAttribute("wordsobj", iw);
        return "generateView";
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

    @GetMapping("/crossword/{id}")
    public String solveCrossword(@PathVariable Long id, Model model) {
        return "crosswordSolutionView";
    }
}
