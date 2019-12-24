package com.sypron.courses.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SypronAuthentication implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5724184715517427993L;

	private String userId;
	private boolean authenticated;
	private final Collection<GrantedAuthority> authorities;

	public SypronAuthentication(String userId, String roles) {
		super();
		this.userId = userId;
		authorities = new ArrayList<GrantedAuthority>();
		List<String> rolesList;
		rolesList = Arrays.asList(roles.split(","));
		for (String role : rolesList) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
	}

	@Override
	public String getName() {
		return userId + "";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return userId + "";
	}

	@Override
	public Object getDetails() {
		return userId + "";
	}

	@Override
	public Object getPrincipal() {
		return userId + "";
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

}
