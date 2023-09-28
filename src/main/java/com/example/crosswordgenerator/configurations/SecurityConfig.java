package com.example.crosswordgenerator.configurations;

import com.example.crosswordgenerator.models.User;
import com.example.crosswordgenerator.services.ImageService;
import com.example.crosswordgenerator.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Класс, наследующий абстрактный класс WebSecurityConfigurerAdapter и использующийся для настройки параметров.
 * @see <a href="https://spring.io/guides/gs/securing-web/">Официальная документация по Spring</a>.
 * */
@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {
    private UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return (username)->{
            User user = userService.getByUsername(username);
            if(user != null) {
                log.info("User with name {} found, password {} ", username, user.getPassword());
                return user;
            }
            else {
                log.info("User with name {} not found", username);
                throw new UsernameNotFoundException("User with username " + username + " doesn't exists.");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests()
                .requestMatchers("/account", "/save").hasRole("USER")
                .requestMatchers("/*", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .build();
    }
}
