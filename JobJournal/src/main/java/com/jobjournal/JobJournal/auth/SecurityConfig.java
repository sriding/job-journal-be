package com.jobjournal.JobJournal.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Value("${AUTH0_AUDIENCE}")
    private String audience;

    @Value("${AUTH0_ISSUERURI}")
    private String issuer;

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // .mvcMatchers("/**").authenticated()
                .mvcMatchers("/api/post/get/posts/from/specific/user/for/unauthorized/clients").permitAll()
                .mvcMatchers("/api/post/**").authenticated()
                .mvcMatchers("/api/users/**").authenticated()
                .mvcMatchers("/api/userprofiles/**").authenticated()
                .mvcMatchers("/api/setting/**").authenticated()
                .mvcMatchers("/api/job/**").authenticated()
                .mvcMatchers("/api/company/**").authenticated()
                .and().cors()
                .and().oauth2ResourceServer().jwt();
        return http.build();
    }
}
