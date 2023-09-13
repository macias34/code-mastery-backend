package com.macias34.codemastery.security.jwt;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.macias34.codemastery.security.constants.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

		String token = Jwts.builder().setSubject(username).setIssuedAt(currentDate).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET).compact();

		return token;
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
					.setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);

			return true;
		}

		catch (Exception exception) {
			System.out.println(exception.getMessage());
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect.");
		}
	}
}