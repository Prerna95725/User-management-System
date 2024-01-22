package com.springboot.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.payloads.UserDto;

@FeignClient(value = "feignClient", url="http://localhost:9090/api/v1")
public interface UserServiceClient {

	@GetMapping("/users")
	public List<UserDto> getAllUser();
	
	@GetMapping("/users/{userId}")
	public UserDto getUserById(@PathVariable("userId") Integer uid);
	
	@PostMapping("/users")
	public UserDto createUser( @RequestBody UserDto userDto);
	
	@PutMapping("/users/{userId}")
	public UserDto updateUser( @RequestBody UserDto userDto, @PathVariable("userId") Integer uid);
	

	@DeleteMapping("/users/{userId}")
	public Object deleteUser(@PathVariable("userId") Integer uid);
	
}
