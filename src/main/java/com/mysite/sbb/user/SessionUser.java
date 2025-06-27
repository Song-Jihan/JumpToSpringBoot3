package com.mysite.sbb.user;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	private String username;
	private String email;
	private String password;
	
	public SessionUser(SiteUser siteuser) {
		this.username=siteuser.getUsername();
		this.email=siteuser.getEmail();
		this.password=siteuser.getPassword();
	}
}
