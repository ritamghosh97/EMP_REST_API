package com.ritam.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class EmployeeRestApiSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //authenticate all the request
        http.authorizeHttpRequests(httpRequest ->
                httpRequest.anyRequest().authenticated());

        //add basic authentication
        http.httpBasic(Customizer.withDefaults());

        //disable csrf
        // csrfConfigurer -> csrfConfigurer.disable()
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
