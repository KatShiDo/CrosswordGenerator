package com.example.crosswordgenerator;

import com.example.crosswordgenerator.configurations.SecurityConfig;
import com.example.crosswordgenerator.controllers.UserController;
import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.services.ImageService;
import com.example.crosswordgenerator.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import java.nio.charset.Charset;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {SecurityConfig.class})
@Import(UserController.class)
@WebAppConfiguration
public class UserControllerTests {

    @MockBean
    private ImageService imageService;
    @MockBean
    UserService userService;
    @MockBean
    PasswordEncoder encoder;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .defaultResponseCharacterEncoding(Charset.defaultCharset())
                .build();
    }


    @Test
    public void getAccountViewTest() throws Exception {
        User myUser = new User();
        myUser.setUsername("user");
        myUser.setPassword("password");
        myUser.setEmail("mail@mail.ru");

        Model model = mock(Model.class);

        when(this.userService.getByUsername("user")).thenReturn(myUser);

        mvc.perform(get("/account", model).with(user("user").roles("USER").password("password")))
                .andExpect(view().name("accountView"));
    }

    @Test
    public void changeUserWithTakenUsernameTest() throws Exception {
        User oldUser = new User();

        when(this.userService.getById(1L)).thenReturn(oldUser);
        when(this.userService.isUsernameAvailable("user")).thenReturn(false);

        mvc.perform(patch("/user/{id}", 1).contentType("application/json")
                        .with(csrf()).characterEncoding("UTF-8")
                        .content("{\"username\":\"user\",\"active\":false," +
                                "\"password\":\"password\",\"roles\":[],\"crosswords\":[]}"))
                .andExpect(content().string("{\"error\":\"Пользователь с таким именем уже существует\"}"));
    }

    @Test
    public void changeUserWithTakenMailTest() throws Exception {
        User oldUser = new User();

        when(this.userService.getById(1L)).thenReturn(oldUser);
        when(this.userService.isUsernameAvailable("user")).thenReturn(true);
        when(this.userService.isEmailAvailable("mail@mail.ru")).thenReturn(false);

        mvc.perform(patch("/user/{id}", 1).contentType("application/json")
                        .with(csrf()).characterEncoding("UTF-8")
                        .content("{\"username\":\"user\", \"password\":\"password\", \"email\":\"mail@mail.ru\"}"))
                .andExpect(content().string("{\"error\":\"Данный адрес эл. почты уже используется\"}"));
    }

    @Test
    public void changeUserWithInvalidPasswordTest() throws Exception {
        User oldUser = new User();

        when(this.userService.getById(1L)).thenReturn(oldUser);
        when(this.userService.isUsernameAvailable("user")).thenReturn(true);
        when(this.userService.isEmailAvailable("mail@mail.ru")).thenReturn(true);

        mvc.perform(patch("/user/{id}", 1).contentType("application/json")
                        .with(csrf()).characterEncoding("UTF-8")
                        .content("{\"username\":\"user\", \"password\":\"password\", \"email\":\"mail@mail.ru\"}"))
                .andExpect(content().string("{\"error\":\"пароль не удовлетворяет требованиям\"}"));
    }

    @Test
    public void changeUserSuccessTest() throws Exception {
        User oldUser = new User();

        when(this.userService.getById(1L)).thenReturn(oldUser);
        when(this.userService.isUsernameAvailable("user")).thenReturn(true);
        when(this.userService.isEmailAvailable("mail@mail.ru")).thenReturn(true);

        mvc.perform(patch("/user/{id}", 1).contentType("application/json")
                        .with(csrf()).characterEncoding("UTF-8")
                        .content("{\"username\":\"user\", \"password\":\"BigDick2015$$\", \"email\":\"mail@mail.ru\"}"))
                .andExpect(status().isOk());
    }
}
