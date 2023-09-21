package com.ritam.api.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authenticate")
public class JwtAuthenticationResource {

    private final JwtEncoder jwtEncoder;

    @Autowired
    public JwtAuthenticationResource(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/get-token")
    public JwtResponse generateJwtToken(Authentication authentication){
        return new JwtResponse(createJwtToken(authentication));
    }

    private String createJwtToken(Authentication authentication){
        JwtClaimsSet jwtClaimsSet
                = JwtClaimsSet.builder()
                    .issuer("http://localhost:8080/")
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(60L * 30))
                    .subject(authentication.getName())
                    .claim("authorities", getScope(authentication))
                    .build();

        JwtEncoderParameters jwtEncodeParameters = JwtEncoderParameters.from(jwtClaimsSet);

        return jwtEncoder.encode(jwtEncodeParameters).getTokenValue();
    }

    private String getScope(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
