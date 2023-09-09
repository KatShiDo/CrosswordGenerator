package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.cwbuilder.CrosswordBuilder;
import com.example.crosswordgenerator.cwbuilder.CrosswordResult;
import com.example.crosswordgenerator.cwbuilder.InputWords;
import com.example.crosswordgenerator.cwbuilder.WordReadyToSave;
import com.example.crosswordgenerator.forms.QuestionsForm;
import com.example.crosswordgenerator.forms.SolutionForm;
import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.models.WordEntity;
import com.example.crosswordgenerator.services.CrosswordService;
import com.example.crosswordgenerator.services.UserService;
import com.example.crosswordgenerator.services.WordEntityService;
import com.example.crosswordgenerator.tojson.SolutionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Контроллер, отвечающий за взаимодействие пользователя с кроссвордами
 * */
@Controller
@Slf4j
public class CrosswordsController {

    private CrosswordService crosswordService;
    private CrosswordResult result;
    private UserService userService;
    private WordEntityService wordEntityService;
    public CrosswordsController(CrosswordService crosswordService, CrosswordResult result, UserService userService, WordEntityService wordEntityService) {
        this.crosswordService = crosswordService;
        this.result = result;
        this.userService = userService;
        this.wordEntityService = wordEntityService;
    }

    private void addResultToModel(Model model){
        model.addAttribute("status", result.getStatus());
        model.addAttribute("crossword", result.getCrosswordAsList());
        model.addAttribute("usedWords", result.getUsedWords());
        model.addAttribute("omittedWords", result.getOmittedWords());
        model.addAttribute("width", result.getWidth());
        model.addAttribute("height", result.getHeight());
    }

    /**
     * Контроллер для доступа к главной странице.
     * @param model объект для предоставления данных в механизм шаблонов.
     * @return Имя представления
     * */
    @GetMapping("/")
    public String crosswords(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(authentication.getName());
        if(user != null)
            model.addAttribute(user);
        model.addAttribute("wordsobj", new InputWords());
        model.addAttribute("width", 20);
        model.addAttribute("height", 20);
        return "generateView";
    }

    @GetMapping("/create")
    public String createCrossword(InputWords iw, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(authentication.getName());
        CrosswordBuilder builder = new CrosswordBuilder();
        List<String> words = new LinkedList<>();
        for(String word:iw.getWords().toLowerCase().split("[^a-zA-Zа-яА-Я0-9]"))
            if(!word.isEmpty())
                words.add(word);
        if(words.isEmpty()){
            model.addAttribute("wordsobj", new InputWords());
            model.addAttribute("width", 20);
            model.addAttribute("height", 20);
            return "generateView";
        }
        CrosswordResult cw = builder.buildCrosswordOutOfWords(words);
        if(cw.getStatus() == 0 || cw.getStatus() == 1){
            result.setCrossword(cw.getCrossword());
            result.setWidth(cw.getWidth());
            result.setHeight(cw.getHeight());
            result.setOmittedWords(cw.getOmittedWords());
        }else{
            log.warn("Cannot create crossword from words {}", words);
            model.addAttribute("notCreated");
        }
        model.addAttribute("wordsobj", new InputWords());
        model.addAttribute("questionsForm", new QuestionsForm());
        model.addAttribute("user", user);
        addResultToModel(model);
        return "generateView";
    }

    /**
     * Контроллер для создания кроссворда.
     * @return Имя представления
     * */
    @PostMapping("/save")
    public String saveCrossword(QuestionsForm questionsForm, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(authentication.getName());
        if(questionsForm.getQuestions().size() != result.getCrossword().size()){
            model.addAttribute("saveError", "Заполните все поля с вопросами");
        }
        else if(questionsForm.getTitle() == null || questionsForm.getTitle().isEmpty()){
            model.addAttribute("saveError", "Укажите название кроссоврда.");
        }else{
            Crossword cw = new Crossword();
            cw.setTitle(questionsForm.getTitle());
            cw.setUser(user);
            List<Crossword> crosswords = user.getCrosswords();
            crosswords.add(cw);
            user.setCrosswords(crosswords);
            cw.setQuestions(questionsForm.getQuestions());
            cw.setWidth(result.getWidth());
            cw.setHeight(result.getHeight());
            List<WordEntity> wordsEntities = new ArrayList<>();
            for(WordReadyToSave cwWord: result.getCrossword()){
                WordEntity word = new WordEntity();
                log.info("Saving word: {} pos {} {}", cwWord.getWord(), cwWord.getPosX(), cwWord.getPosY());
                word.setWord(cwWord.getWord());
                word.setPosX(cwWord.getPosX());
                word.setPosY(cwWord.getPosY());
                word.setHorizontal(cwWord.isHorizontal());
                wordEntityService.saveWord(word);
                wordsEntities.add(word);
            }
            cw.setWords(wordsEntities);
            crosswordService.save(cw);
            userService.update(user);
            model.addAttribute("link", String.format("http://localhost:8080/solve/%s", cw.getId()));
        }
        model.addAttribute("wordsobj", new InputWords());
        addResultToModel(model);
        model.addAttribute("questionsForm", questionsForm);
        model.addAttribute("user", user);
        return "generateView";
    }

    /**
     * Контроллер для удаления кроссворда.
     * @param id id кроссворда в БД.
     * @return Имя представления
     * */
    @DeleteMapping("/crossword/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCrossword(@PathVariable Long id) {
        boolean result = crosswordService.delete(id);
        if(result) return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/solve/{id}")
    public String solveCrossword(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(authentication.getName());
        Crossword cw = crosswordService.getById(id);
        if(cw!=null){
            CrosswordResult cr = new CrosswordResult();
            cr.setHeight(cw.getHeight());
            cr.setWidth(cw.getWidth());
            cr.setCrossword(cw.getWords().stream().map(word -> {
                return new WordReadyToSave(word.getWord(), word.isHorizontal(), word.getPosX(), word.getPosY());
            }).toList());
            model.addAttribute("crossword", cr.getCrosswordAsList());
            model.addAttribute("questions", cw.getQuestions());
            model.addAttribute("height", cw.getHeight());
            model.addAttribute("width", cw.getWidth());
        }else{
            model.addAttribute("notFound", true);
        };
        model.addAttribute("user", user);
        return "solveView";
    }

    @PostMapping("/solve/{id}")
    @ResponseBody
    public ResponseEntity<Object> checkSolution(@PathVariable Long id, @RequestBody SolutionForm solutionForm){
        Crossword cw = crosswordService.getById(id);
        List<String> words = new ArrayList<>();
        for(WordEntity we: cw.getWords())
            words.add(we.getWord());
        List<List<Integer>> mistakes = new ArrayList<>();
        int wordIndex = 0, solvedWords = 0, allChars = 0, solvedChars = 0;
        for(String solutionWord: solutionForm.getWords()){
            List<Integer> ms = new ArrayList<>();
            String cwWord = cw.getWords().get(wordIndex++).getWord();
            boolean wordSolved = true;
            for(int i=0;i<cwWord.length();i++){
                if(i < solutionWord.length()){
                    if(solutionWord.charAt(i) != cwWord.charAt(i)){
                        ms.add(i);
                        wordSolved = false;
                    }else{
                        solvedChars++;
                    }
                }else{
                    ms.add(i);
                    wordSolved=false;
                }
            }
            if(wordSolved)
                solvedWords++;
            mistakes.add(ms);
        }
        for(String cwWord: cw.getWords().stream().map(WordEntity::getWord).toList())
            allChars += cwWord.length();
        SolutionResult sr = new SolutionResult();
        sr.setWordsSolved(solvedWords);
        sr.setAccuracy((int)((double)solvedChars / allChars * 100));
        sr.setMistakes(mistakes);
        crosswordService.increaseSolvedCount(cw.getId());
        return ResponseEntity.ok().body(sr);
    }
}
