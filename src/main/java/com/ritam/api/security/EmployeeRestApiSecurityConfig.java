package com.ritam.api.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class EmployeeRestApiSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRestApiSecurityConfig.class);

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //authenticate all the request
        http.authorizeHttpRequests(httpRequest ->
                httpRequest
                        .requestMatchers("/authenticate/get-token").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/teams/**").hasRole("HR")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/teams").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/teams/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/teams").hasRole("MANAGER")
                        .anyRequest().authenticated());

        //specify session creation policy
        http.sessionManagement(httpSessionConfigurer ->
                httpSessionConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //disable basic authentication
        http.httpBasic(Customizer.withDefaults());

        //disable csrf
        //csrfConfigurer -> csrfConfigurer.disable()
        http.csrf(AbstractHttpConfigurer::disable);

        //allow any requests that are coming from the same origin to frame this application
        http.headers(httpHeaderConfigurer -> httpHeaderConfigurer
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        //JWT-Step-1: configure OAuth2 Resource Server
        http.oauth2ResourceServer(oauth2ResourceServerConfigurer ->
                oauth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer
                        .jwtAuthenticationConverter(getJwtAuthenticationConverter())
                )
        );

        return http.build();
    }

    private Converter<Jwt,? extends AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(getJwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    private Converter<Jwt, Collection<GrantedAuthority>> getJwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");
        authoritiesConverter.setAuthoritiesClaimName("authorities");
        return authoritiesConverter;
    }

    //JWT-Step-2: create a KeyPair
    @Bean
    public KeyPair keyPair(){

        KeyPairGenerator keyPairGenerator = null;

        try{
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        }catch (NoSuchAlgorithmException e){
            logger.error("No algorithm found with name: 'RSA'");
            e.printStackTrace();
        }

        Optional<KeyPair> optionalKeyPair = Optional.ofNullable(keyPairGenerator).map(kPGenerator -> {
            kPGenerator.initialize(2048); //use 2048-bit RSA Encryption
            return kPGenerator.generateKeyPair();
        });

        return optionalKeyPair.orElse(null);
    }

    //JWT-Step-3: create a RsaKey object using KeyPair
    @Bean
    public RSAKey rsaKey(KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    //JWT-Step-4: Create the JWKSource from JWKSet, which will be created from RSAKey object
    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        //1st, create a JWKSet to be able to create JWKSource from it.
        JWKSet jwkSet = new JWKSet(rsaKey);

        //implement the get method of JWKSource, which is a functional interface
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    //JWT-Step-5: Create the JWTDecoder
    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    //JWT-Step-6: Create the JWTEncoder
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
}
