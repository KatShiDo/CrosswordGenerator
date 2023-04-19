package com.example.crosswordgenerator.configurations;

import com.example.crosswordgenerator.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Класс, наследующий абстрактный класс WebSecurityConfigurerAdapter и использующийся для настройки параметров.
 * @see <a href="https://spring.io/guides/gs/securing-web/">Официальная документация по Spring</a>.
 * */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    /**
     * Метод, позволяющий настраивать параметры авторизации для Spring Security.
     * @param http объект, позволяющий настраивать параметры Spring Security для определённого http запроса.
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/create", "/crossword/**", "/images/**", "/registration", "/css/**", "/javascript/**", "/img/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

    }

    /**
     * Метод, позволяющий настраивать параметры аутентификации для Spring Security.
     * @param auth объект, предназначенный для настройки параметров AuthenticationManager
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    /**
     * Метод, определяющий способ кодирования пароля.
     * @return Реализация PasswordEncoder, которая использует хеш-функцию BCrypt
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
