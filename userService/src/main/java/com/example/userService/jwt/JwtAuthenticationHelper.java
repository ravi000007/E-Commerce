package com.example.userService.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.userService.entity.User;
import com.example.userService.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthenticationHelper {

	private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 24;
	@Autowired
	private UserRepository userRepository ;
	private String secret = 
			"ismynameishunterhuehfgklfifb77632r33rxnfskfhdfhdslhfkfdhfkhfdkfgsdfhsdhfdsgk7A9Bc0D1Ef2Gh3I4Jk5L6Mn7Op8Qr9S";
	
	public String getUsernameFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getSubject();
	}
	
	public Claims getClaimsFromToken(String token)
	{
		Claims claims = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build()
				.parseClaimsJws(token).getBody();
		
		return claims;
	}

	public boolean isTokenExpired(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration().before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		 String userId = Long.toString(((User) userDetails).getId());
		
		Map<String, Object> claims = new HashMap<>();
		 claims.put("roles", userDetails.getAuthorities().stream()
	                .map(GrantedAuthority::getAuthority)
	                .collect(Collectors.toList()));
		 
		 claims.put("userId", userId);
		
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000 ))
				.signWith(new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName()),SignatureAlgorithm.HS512)
				.compact();
				
					
				
	}

}
