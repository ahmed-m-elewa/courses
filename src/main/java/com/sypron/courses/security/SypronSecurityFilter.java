package com.sypron.courses.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@Component
public class SypronSecurityFilter extends GenericFilterBean {

	private static final Logger logger = LoggerFactory.getLogger(SypronSecurityFilter.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${token.encodedkey}")
	String encodedKey;


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String requestUri = req.getServletPath();

		if (requestUri.equals("/health") || requestUri.equals("/beans") || requestUri.contains("api-docs")
				|| requestUri.contains("swagger") || requestUri.contains("springfox-swagger-ui")
				|| requestUri.contains("auth") || requestUri.contains("version")) {

			logger.info("Request Skipped Authentication : {}", requestUri);
			chain.doFilter(request, response);
		} else {
			logger.info("request authorization header : [{}]" , req.getHeader("Authorization"));
			try {

				if (StringUtils.isBlank(req.getHeader("Authorization"))) {
					logger.error("Header Authorization Not Found !!");
					throw new InsufficientAuthenticationException("ER_07_001--Authorization headers not found ");
				}

				logger.info("Authorization header :  [{}]", req.getHeader("Authorization"));
				String header = req.getHeader("Authorization");
				String authorizationSchema = "Bearer";

				header = header.substring(authorizationSchema.length()).trim();

				byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

				SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
				Jws<Claims> jws = null;
				try {
					jws = Jwts.parser().setSigningKey(originalKey).parseClaimsJws(header);
				} catch (ExpiredJwtException e) {
					logger.error(" Authentication token is expired!!");
					throw new InsufficientAuthenticationException("ER_07_001--Authentication token is expired ");
				} catch (Exception e) {
					logger.error("failed to decode token  ", e);
					throw new InsufficientAuthenticationException("ER_07_001--failed to decode token ");
				}

				String userId = jws.getBody().getSubject();
				String userRoles = jws.getBody().get("role", String.class);
				logger.info("authenticating user with id [{}] and roles [{}]" , userId , userRoles);
				SypronAuthentication sypronAuthentication = new SypronAuthentication(userId, userRoles);
				Authentication auth = authenticationManager.authenticate(sypronAuthentication);
				SecurityContextHolder.getContext().setAuthentication(auth);
				chain.doFilter(request, response);

			} catch (AuthenticationException e) {
				logger.error("Authorization headers not Valid !!");
				throw new InsufficientAuthenticationException("ER_07_001--Authorization headers not Valid ");
			} catch (Exception e) {
				throw new BadCredentialsException("ER_07_001--Authentication Error");
			} finally {
				SecurityContextHolder.clearContext();
			}
		}

	}

}
