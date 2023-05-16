package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контроллер, отвечающий за взаимодействие пользователя с аккаунтом
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Контроллер, возвращающий представление формы для входа в аккаунт.
     * @return Имя представления.
     * */
    @GetMapping("/login")
    public String login() {
        return "loginView";
    }

    /**
     * Контролер, вохвращающий представления для регистрации.
     * @return Имя представления.
     * */
    @GetMapping("/registration")
    public String registration() {
        return "registrationView";
    }

    /**
     * Контроллер для прохождения регистрации.
     * @param user объект, представляющий пользователя.
     * @param model объект для предоставления данных в механизм шаблонов.
     * @return Имя представления для формы входа в аккаунт, или имя представления для регистрации, если регистрация не удалась
     * */
    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.create(user)) {
            model.addAttribute("errorMessage", "This username or email is already taken");
            return "registrationView";
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("crosswords", user.getCrosswords());
        return "userInfoView";
    }
}
