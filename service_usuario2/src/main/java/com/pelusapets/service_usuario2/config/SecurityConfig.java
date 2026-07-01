package com.pelusapets.service_usuario2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    return http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    // Se permite acceso libre al Login/Registro y a la documentación Swagger
                    .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    // Cualquier otra petición (como /api/usuarios) exigirá el Token JWT
                    .anyRequest().authenticated()
            ).build();
  }
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
