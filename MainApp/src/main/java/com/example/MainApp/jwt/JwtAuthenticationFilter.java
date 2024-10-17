package com.example.MainApp.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
	
//	@Autowired
//	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestHeader = request.getHeader("Authorization");
		
		String userId = null;
		String token = null;
		
		if(requestHeader != null && requestHeader.startsWith("Bearer")) {
			token = requestHeader.substring(7);
			
			userId = jwtHelper.getUserIdFromToken(token);
			
			if(userId != null) {
				
				List<String> roles = jwtHelper.getRolesFromToken(token);
	            
	            List<GrantedAuthority> authorities = roles.stream()
	                    .map(SimpleGrantedAuthority::new)
	                    .collect(Collectors.toList());
				UserDetails userDetails = new User(userId, "", authorities);
				
				if(!jwtHelper.isTokenExpired(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
							new UsernamePasswordAuthenticationToken(token,null , userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
