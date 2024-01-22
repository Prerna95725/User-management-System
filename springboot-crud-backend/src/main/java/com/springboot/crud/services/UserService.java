package com.springboot.crud.services;

import java.util.List;

import com.springboot.crud.entities.User;
import com.springboot.crud.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	String deleteUser(Integer userId);

	

	

}
