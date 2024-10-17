package com.example.userService.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtAuthenticationHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		   // Get the Authorization header
        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        
        // Check if the token is present and starts with "Bearer"
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            username = jwtHelper.getUsernameFromToken(token);
            
            // Proceed if username is found and authentication isn't already set
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Check if the token is not expired
                if (!jwtHelper.isTokenExpired(token)) {
                    // Authenticate the user
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        
        filterChain.doFilter(request, response);                       
	}

}
