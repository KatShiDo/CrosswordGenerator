package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.enums.Role;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ReginController {
    private PasswordEncoder encoder;
    private UserService userService;

    public ReginController(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @GetMapping("/regin")
    public String getReginView(Model model){
        return "reginView";
    }

    @PostMapping("/regin")
    public String registrateUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model){
        if(!userService.isUsernameAvailable(username)){
            model.addAttribute("usernameNotAvailable", true);
            return "reginView";
        }
        if(!userService.isEmailAvailable(email)){
            model.addAttribute("emailNotAvailable");
            return "reginView";
        }
        Matcher m = Pattern.compile("(?:([a-z])|([A-Z])|(\\d)|([`~!@#$%^&*()\\-_=+\\[\\]{},<.>/?])){6,}").matcher(password);
        if(!m.matches() || m.group(1) == null || m.group(2) == null
        || m.group(3) == null || m.group(4) == null){
            model.addAttribute("wrongPassword", true);
            return "reginView";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setActive(true);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        userService.create(user);
        return "redirect:/login";
    }
}
