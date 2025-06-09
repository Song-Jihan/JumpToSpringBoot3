package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username,String password,String email) {
		SiteUser user=new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser=this.userRepository.findByUsername(username);
		if(siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
	
	public SiteUser getUserByEmail(String email) {
		Optional<SiteUser> siteUser=this.userRepository.findByEmail(email);
		if(siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("Email not found");
		}
	}
	
	public SiteUser updatePassword(SiteUser user,String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		this.userRepository.save(user);
		return user;
	}
	
	public boolean MatchPassword(String rawPassword,String encodedPassword) {
		//if(rawPassword==null || encodedPassword==null) return false;
		return this.passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
