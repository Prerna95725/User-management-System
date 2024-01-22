package com.springboot.crud.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.crud.payloads.UserDto;
import com.springboot.crud.services.UserService;

@CrossOrigin//(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	

	
	@PostMapping("/users")
	//@ResponseStatus(HttpStatus.CREATED) 
	public UserDto createUser(@Valid @RequestBody UserDto userDto)
	{
		return this.userService.createUser(userDto);
	   // return createUserDto, HttpStatus.ACCEPTED;
	}
	

	
	@PutMapping("/users/{userId}")
	public UserDto updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid)
	{
		return this.userService.updateUser(userDto,uid);
		//return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/users")
	public List<UserDto> getAllUsers()
	{
		return this.userService.getAllUsers();
	}

	@GetMapping("/users/{userId}")
	public UserDto getUserById(@PathVariable Integer userId){
		return this.userService.getUserById(userId);
	}
	
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable("userId") Integer uid){
	return this.userService.deleteUser(uid);
	//return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",200),HttpStatus.OK);
	}
	

	
}
