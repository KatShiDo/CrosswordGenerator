package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "loginView";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registrationView";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.create(user)) {
            model.addAttribute("errorMessage", "This username or email is already taken");
            return "registrationView";
        }
        return "redirect:/login";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/admin")
//    public String admin() {
//        return "adminView";
//    }
}
