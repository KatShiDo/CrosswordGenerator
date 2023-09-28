package com.example.crosswordgenerator;

import com.example.crosswordgenerator.controllers.ReginController;
import com.example.crosswordgenerator.enums.Role;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReginControllerTest {

    @Test
    @DisplayName("Test receiving of registration page")
    public void testGetReginView(){
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);
        ReginController reginController = new ReginController(encoder, userService);
        String page = reginController.getReginView(model);
        assertEquals(page, "reginView");
        verify(model, never()).addAttribute(any(), any());
    }

    @Test
    @DisplayName("Registration success flow")
    public void testRegistrateUserFlow(){
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        UserService userService = mock(UserService.class);
        ReginController reginController = new ReginController(encoder, userService);
        Model model = mock(Model.class);
        String password = "A!user1";
        String email = "example@gmail.com";
        String username = "user1";
        doReturn(password).when(encoder).encode(password);
        doReturn(true).when(userService).isEmailAvailable(email);
        doReturn(true).when(userService).isUsernameAvailable(username);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        user.setActive(true);
        String page = reginController.registrateUser(username, email, password, model);
        verify(userService).create(user);
        verify(model, never()).addAttribute(any(), any());
        assertEquals(page, "redirect:/login");
    }

    @Test
    @DisplayName("Test registration with username that doesn't available")
    public void testRegistrateUsernameNotAvailable(){
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        UserService userService = mock(UserService.class);
        ReginController reginController = new ReginController(encoder, userService);
        Model model = mock(Model.class);
        String username = "user";
        doReturn(false).when(userService).isUsernameAvailable(username);
        String page = reginController.registrateUser(username, "email", "password", model);
        assertEquals(page, "reginView");
        verify(model).addAttribute("usernameNotAvailable", true);
    }
}
