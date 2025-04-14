package org.researchandreview.projecttsbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

       /* config.setAllowedOrigins(List.of("http://js.thxx.xyz")); // 허용 Origin
        config.setAllowedOrigins(List.of("https://js.thxx.xyz")); // 허용 Origin
        config.setAllowedOrigins(List.of("http://localhost:8080")); // 허용 Origin
        config.setAllowedOrigins(List.of("chrome-extension://mkpijdockceklmbengaklappippglihh")); // 허용 Origin
        */
        config.setAllowedOrigins(List.of("*")); // 허용 Origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*", "x-uuid"));
        //config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/**")
                                .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .setSharedObject(CorsConfigurationSource.class, corsConfigurationSource());

        return http.build();
    }
}