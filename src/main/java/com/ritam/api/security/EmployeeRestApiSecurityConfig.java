package com.ritam.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class EmployeeRestApiSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        UserDetails user1 = User.withUsername("ritamghosh97")
                .password(passwordEncoder().encode("Ritam@1997"))
                .roles("EMPLOYEE").build();

        UserDetails user2 = User.withUsername("arpankarmakar96")
                .password(passwordEncoder().encode("Arpan@1996"))
                .roles("EMPLOYEE").build();

        UserDetails user3 = User.withUsername("prithisdey79")
                .password("Prithis@1979")
                .passwordEncoder(password -> passwordEncoder().encode(password))
                .roles("EMPLOYEE", "MANAGER").build();

        UserDetails user4 = User.withUsername("sumanpanja1992")
                .password("Suman@1992")
                .passwordEncoder(password -> passwordEncoder().encode(password))
                .roles("EMPLOYEE", "ADMIN").build();

        UserDetails user5 = User.withUsername("sreejaguha93")
                .password("Sreeja@1993")
                .passwordEncoder(password -> passwordEncoder().encode(password))
                .roles("EMPLOYEE", "HR").build();

        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        userDetailsManager.createUser(user3);
        userDetailsManager.createUser(user4);
        userDetailsManager.createUser(user5);

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //authenticate all the request
        http.authorizeHttpRequests(httpRequest ->
                httpRequest.anyRequest().authenticated());

        //specify session creation policy
        http.sessionManagement(httpSessionConfigurer ->
                httpSessionConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //add basic authentication
        http.httpBasic(Customizer.withDefaults());

        //disable csrf
        //csrfConfigurer -> csrfConfigurer.disable()
        http.csrf(AbstractHttpConfigurer::disable);

        //allow any requests that are coming from the same origin to frame this application
        http.headers(httpHeaderConfigurer -> httpHeaderConfigurer
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}
