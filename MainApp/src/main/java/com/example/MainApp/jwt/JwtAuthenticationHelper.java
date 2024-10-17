package com.example.MainApp.jwt;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationHelper {

//	private static final long JWT_TOKEN_VALIDITY = 60*60;
	private String secret =
			"ismynameishunterhuehfgklfifb77632r33rxnfskfhdfhdslhfkfdhfkhfdkfgsdfhsdhfdsgk7A9Bc0D1Ef2Gh3I4Jk5L6Mn7Op8Qr9S";
	
    public String getUserIdFromToken(String token) {
        return getClaimsFromToken(token).get("userId", String.class);
    }
  	
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
	public List<String> getRolesFromToken(String token) {
	    Claims claims = getClaimsFromToken(token);
	    return (List<String>) claims.get("roles");
	}

	public boolean isTokenExpired(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration().before(new Date());
	}
	
}
