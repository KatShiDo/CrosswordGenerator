package com.example.crosswordgenerator.services;

import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.repositories.IUserRepository;
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
    public void UserService_GetById_ReturnsUser() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }

    @Test
    public void UserService_GetById_NullId_ReturnsNull() {
        User user = userService.getById(null);
        Assertions.assertNull(user);
    }

    @Test
    public void UserService_GetByUsername_ReturnsUser() {
        User user = new User();
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        user = userService.getByUsername("test");
        Assertions.assertNotNull(user);
    }

    @Test
    public void UserService_GetByUsername_NullUsername_ReturnsNull() {
        User user = userService.getByUsername(null);
        Assertions.assertNull(user);
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
        boolean result = userService.create(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void UserService_CreateUser_NullArgument_ThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.create(null);
        }, "NullPointerException was expected");
    }

    @Test
    public void UserService_UpdateUser_ReturnsTrue() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        boolean result = userService.update(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void UserService_UpdateUser_NullArgument_ThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.update(null);
        }, "NullPointerException was expected");
    }

    @Test
    public void UserService_IsUsernameAvailable_ReturnsFalse() {
        User user = new User();
        user.setUsername("test");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        boolean result = userService.isUsernameAvailable("test");
        Assertions.assertFalse(result);
    }

    @Test
    public void UserService_IsUsernameAvailable_NullArgument_ReturnsTrue() {
        boolean result = userService.isUsernameAvailable(null);
        Assertions.assertTrue(result);
    }

    @Test
    public void UserService_IsEmailAvailable_ReturnsBoolean() {
        User user = new User();
        user.setEmail("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
        boolean result = userService.isEmailAvailable("test@email.com");
        Assertions.assertFalse(result);
    }

    @Test
    public void UserService_IsEmailAvailable_NullArgument_ReturnsTrue() {
        boolean result = userService.isEmailAvailable(null);
        Assertions.assertTrue(result);
    }
}
