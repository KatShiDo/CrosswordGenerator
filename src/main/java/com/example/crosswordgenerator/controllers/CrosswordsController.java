package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.services.CrosswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CrosswordsController {

    private final CrosswordService crosswordService;

    @GetMapping("/")
    public String crosswords(Model model) {

        model.addAttribute("crosswords", crosswordService.getAll());
        return "crosswordsView";
    }

    @PostMapping("/crossword/create")
    public String createCrossword(Crossword crossword) {
        crosswordService.save(crossword);
        return "redirect:/";
    }

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
