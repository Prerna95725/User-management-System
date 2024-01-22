package com.springboot.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.crud.entities.User;
import com.springboot.crud.payloads.UserDto;


public interface UserRepo extends JpaRepository<User, Integer>{

	Object save(UserDto userDto);
	
	User findUserById(Integer userId);

	User findUserByEmail(String email);
 
}
