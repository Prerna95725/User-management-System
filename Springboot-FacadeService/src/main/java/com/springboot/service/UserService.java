package com.springboot.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService{

	
	@Value("${user.username}")
	private  String username;
	
	private static String NAME_STATIC;
	
	@Value("${user.username}")
	public void setNameStatic(String username) {
		UserService.NAME_STATIC=username;
	}
	
	
	@Value("${user.password}")
	private  String password;
	
	private static String PASSWORD_STATIC;
	
	@Value("${user.password}")
	public void setPasswordStatic(String password) {
		UserService.PASSWORD_STATIC=password;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if(NAME_STATIC.equals(userName)) {
			return new org.springframework.security.core.userdetails.User(userName,PASSWORD_STATIC,new ArrayList<>());
		}else {
		throw new UsernameNotFoundException("User not found with username: " + userName);
		
		
		//return new org.springframework.security.core.userdetails.User("admin","password",new ArrayList<>());
	}
	}
}
