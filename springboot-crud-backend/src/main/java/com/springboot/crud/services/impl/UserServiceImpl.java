package com.springboot.crud.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.crud.entities.User;
import com.springboot.crud.exceptions.ResourceNotFoundException;
import com.springboot.crud.payloads.UserDto;
import com.springboot.crud.repositories.UserRepo;
import com.springboot.crud.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	private UserRepo userRepo;
	
	

	public UserServiceImpl(UserRepo userRepo) {
		// TODO Auto-generated constructor stub
		this.userRepo=userRepo;
	}

	@Override
	@Transactional
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	@Transactional
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userRepo.findUserById(userId);
				if (user == null)

					throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Record with provided id is not found");
				if(user.getEmail()==null)
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST," Pls Enter valid data");

		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		 User updatedUser = this.userRepo.save(user);
		 UserDto userDto1 = this.userToDto(updatedUser);
		 return userDto1;
	}

	@Override
	@Transactional
	public UserDto getUserById(Integer userId) {
	
		User user=this.userRepo.findUserById(userId);
		if (user == null)

			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Record with provided id is not found");
		return this.userToDto(user);
	}

	@Override
	@Transactional
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
	
		List<UserDto> userDtos = users.stream().map(user ->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	@Transactional
	public String deleteUser(Integer userId) {
		User user = this.userRepo.findUserById(userId);
				if (user == null)

					throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Record with provided id is not found");
		this.userRepo.delete(user);
		return "success";
	}

	public User dtoToUser(UserDto userDto)
	{
		User user=new User();
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAddress(userDto.getAddress());
		return user;
		
	}
	
	public UserDto userToDto(User user)
	{
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAddress(user.getAddress());
		return userDto;
	}

	
}
