package com.example.userService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.userService.jwt.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF for this example, but it's better to enable it in production
            .authorizeHttpRequests()
            .requestMatchers("/api/auth/signup", "/api/auth/signin","/signin").permitAll()  // Allow access to the signup endpoint
            .anyRequest().authenticated()  // Require authentication for all other requests
            .and()
            .exceptionHandling()
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Access is denied for this user\"}");
            })
            .authenticationEntryPoint((request, response, authException) -> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"User is Unauthorized\"}");
            })
            .and()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                    
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
            return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
