package com.sypron.courses.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SypronAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SypronAuthentication sypronAuthentication = (SypronAuthentication) authentication;
		sypronAuthentication.setAuthenticated(true);
		 
		return sypronAuthentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SypronAuthentication.class.isAssignableFrom(authentication);
	}
	

}
