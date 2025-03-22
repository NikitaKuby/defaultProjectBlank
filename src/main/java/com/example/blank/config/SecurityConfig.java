/*
package com.example.blank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ZitadelIntrospectionFilter zitadelIntrospectionFilter) throws Exception {
        http
                .csrf().disable() // Отключаем CSRF для API
                .authorizeRequests(auth -> auth
                        .anyRequest().authenticated() // Все запросы требуют аутентификации
                )
                .addFilterBefore(zitadelIntrospectionFilter, UsernamePasswordAuthenticationFilter.class); // Добавляем кастомный фильтр

        return http.build();
    }
}

*/
