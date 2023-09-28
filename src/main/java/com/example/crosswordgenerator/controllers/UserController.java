package com.example.crosswordgenerator.controllers;

import com.example.crosswordgenerator.models.Image;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.services.ImageService;
import com.example.crosswordgenerator.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Контроллер, отвечающий за взаимодействие пользователя с аккаунтом
 */
@Controller
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final ImageService imageService;

    public UserController(UserService userService, PasswordEncoder encoder, ImageService imageService) {
        this.userService = userService;
        this.encoder = encoder;
        this.imageService = imageService;
    }

    @GetMapping("/account")
    public String getAccountView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(authentication.getName());
        model.addAttribute("user", user);
        if(user != null) {
            model.addAttribute("crosswords", user.getCrosswords());
        }
        return "accountView";
    }

    @ResponseBody
    @PatchMapping(path = "/user/{id}", consumes = "application/json")
    public ResponseEntity<?> changeUser(@PathVariable Long id, @RequestBody User user){
        User oldUser = userService.getById(id);
        if(user.getUsername() != null){
            log.info("Changing username for user {}", id);
            if(userService.isUsernameAvailable(user.getUsername())){
                oldUser.setUsername(user.getUsername());
                Authentication authentication =  new UsernamePasswordAuthenticationToken(user.getUsername(), oldUser.getPassword(), oldUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Пользователь с таким именем уже существует");
                return ResponseEntity.unprocessableEntity().body(response);
            }
        }
        if(user.getEmail() != null){
            log.info("Changing email for user {}", id);
            if(userService.isEmailAvailable(user.getEmail())){
                oldUser.setEmail(user.getEmail());
            }else{
                Map<String, String> response = new HashMap<>();
                response.put("error", "Данный адрес эл. почты уже используется");
                return ResponseEntity.unprocessableEntity().body(response);
            }
        }
        if(user.getAvatar() != null){
            log.info("Changing avatar for user {}", id);
            Image img = imageService.getImageById(user.getAvatar().getId());
            if(img != null){
                oldUser.setAvatar(img);
            }
        }
        if(user.getPassword() != null){
            log.info("Changing password for user {}", id);
            Matcher m = Pattern.compile("(?:([a-z])|([A-Z])|(\\d)|([`~!@#$%^&*()\\-_=+\\[\\]{},<.>/?])){6,}").matcher(user.getPassword());
            if(m.matches() && m.group(1) != null && m.group(2) != null && m.group(3) != null && m.group(4) != null){
                oldUser.setPassword(encoder.encode(user.getPassword()));
            }else{
                Map<String, String> response = new HashMap<>();
                response.put("error", "пароль не удовлетворяет требованиям");
                return ResponseEntity.unprocessableEntity().body(response);
            }
        }
        userService.update(oldUser);
        return ResponseEntity.ok().build();
    }
}
