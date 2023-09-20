package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.Crossword;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.repositories.IUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_GetById_ReturnUser() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }

    @Test
    public void UserService_GetByUsername_ReturnUser() {
        User user = new User();
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        user = userService.getByUsername("test");
        Assertions.assertNotNull(user);
    }

    @Test
    public void UserService_GetAllUsers_ReturnsIterable() {
        List<User> users = Mockito.mock(List.class);
        when(userRepository.findAll()).thenReturn(users);
        Iterable<User> usersReturn = userService.getAll();
        Assertions.assertNotNull(usersReturn);
    }

    @Test
    public void UserService_CreateUser_ReturnsBoolean() {
        User user = new User();
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Boolean result = userService.create(user);
        Assertions.assertEquals(true, result);
    }

    @Test
    public void UserService_UpdateUser_ReturnsBoolean() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Boolean result = userService.update(user);
        Assertions.assertEquals(true, result);
    }

    @Test
    public void UserService_IsUsernameAvailable_ReturnsBoolean() {
        User user = new User();
        user.setUsername("test");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        Boolean result = userService.isUsernameAvailable("test");
        Assertions.assertEquals(false, result);
    }

    @Test
    public void UserService_IsEmailAvailable_ReturnsBoolean() {
        User user = new User();
        user.setEmail("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
        Boolean result = userService.isEmailAvailable("test@email.com");
        Assertions.assertEquals(false, result);
    }
}
