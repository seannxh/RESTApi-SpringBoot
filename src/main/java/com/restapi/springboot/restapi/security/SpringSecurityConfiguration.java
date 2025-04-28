package com.restapi.springboot.restapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //all requests are authed
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
        //if a request is not authed a web page is shown
        http.httpBasic(withDefaults());

        //disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
