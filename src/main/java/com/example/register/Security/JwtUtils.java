package com.example.register.Security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.register.Service.Impl.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${kumaonnest.app.jwtSecret}")
	private String jwtSecret;

	@Value("${kumaonnest.app.jwtExpirationMsAdmin}")
	private Long jwtExpirationMsAdmin;

	@Value("${kumaonnest.app.jwtExpirationMsModerator}")
	private Long jwtExpirationMsModerator;

	@Value("${kumaonnest.app.jwtExpirationMsStaff}")
	private Long jwtExpirationMsStaff;

	@Value("${kumaonnest.app.jwtExpirationMsUser}")
	private Long jwtExpirationMsUser;

	public String generateJwtTokenAdmin(Authentication authentication) {
		System.out.println("Generate Token");
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println("GERERATING TOKEN BY Admin: "+ jwtExpirationMsAdmin);
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMsAdmin))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	public String generateJwtTokenModerator(Authentication authentication) {
		System.out.println("Generate Token");
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println("GERERATING TOKEN BY Moderator: "+ jwtExpirationMsModerator );
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMsModerator))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	public String generateJwtTokenStaff(Authentication authentication) {
		System.out.println("Generate Token");
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println("GERERATING TOKEN BY staff: "+ jwtExpirationMsStaff );
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMsStaff))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	public String generateJwtToken(Authentication authentication) {
		System.out.println("Generate Token");
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println("GERERATING TOKEN BY USER: "+ jwtExpirationMsUser );
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMsUser))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		System.out.println("Validate Token");
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

}
